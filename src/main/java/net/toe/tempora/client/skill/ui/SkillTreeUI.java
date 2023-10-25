package net.toe.tempora.client.skill.ui;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.toe.tempora.Tempora;
import net.toe.tempora.client.skill.Skill;
import net.toe.tempora.client.skill.SkillEffect;
import net.toe.tempora.client.skill.SkillEnum;
import net.toe.tempora.networking.TemporaPackets;
import net.toe.tempora.util.Persist;
import java.util.*;
import java.util.stream.Collectors;

public class SkillTreeUI extends Screen {

    private final Map<SkillEnum, BoundingBox> skillBoundingBoxes = new HashMap<>();
    private final PlayerEntity player;
    private final Map<Skill, Identifier> skillStatuses = new HashMap<>();
    private final static Identifier PURCHASABLE = new Identifier(Tempora.MOD_ID, "textures/gui/rounded_frame_purchasable.png");
    private final static Identifier PURCHASED = new Identifier(Tempora.MOD_ID, "textures/gui/rounded_frame_purchased.png");
    private final static Identifier MISSING_PREDECESSOR = new Identifier(Tempora.MOD_ID, "textures/gui/rounded_frame_missing_predecessor.png");
    private final static Identifier BROKE = new Identifier(Tempora.MOD_ID, "textures/gui/rounded_frame_broke.png");


    public SkillTreeUI(Text title, PlayerEntity player, MinecraftServer server) {
        super(title);
        this.player = player;
        loadUnlockedSkills(server);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        drawBackground(matrices);
        renderSkills(matrices);
    }

    private void loadUnlockedSkills(MinecraftServer server) {
        skillStatuses.clear();
        for (SkillEnum skillEnum : SkillEnum.values()) {
            switch (skillEnum.getSkill().canUnlock(player, server)) {
                case PURCHASABLE -> skillStatuses.put(skillEnum.getSkill(), PURCHASABLE);
                case PURCHASED -> skillStatuses.put(skillEnum.getSkill(), PURCHASED);
                case MISSING_PREDECESSOR -> skillStatuses.put(skillEnum.getSkill(), MISSING_PREDECESSOR);
                case BROKE -> skillStatuses.put(skillEnum.getSkill(), BROKE);
            }
        }
    }

    private void drawBackground(MatrixStack matrixStack) {
        int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

        // Set the color for the translucent background (white with some transparency)
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.7F);

        // Disable texture
        RenderSystem.disableTexture();

        // Begin quad rendering
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // Draw the rectangle covering the screen
        DrawableHelper.fill(matrixStack, 0, 0, screenWidth, screenHeight, 0xBBBBBBBB); // BBBBBBBB -> semi-transparent gray

        // Reset the color and texture state
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    private void renderSkills(MatrixStack matrices) {
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        int skillSize = 16;
        int framePadding = 5;  // Amount by which the frame is larger than the skill icon
        int frameSize = skillSize + 2 * framePadding; // Total size of the frame

        // Group skills by their effects
        Map<SkillEffect, List<SkillEnum>> groupedSkills = Arrays.stream(SkillEnum.values())
                .collect(Collectors.groupingBy(skillEnum -> skillEnum.getSkill().getEffect()));

        int totalColumns = groupedSkills.size();
        int totalWidth = totalColumns * (skillSize + 2 * framePadding) + (totalColumns - 1) * 10; // 10 is spacing between columns
        int startX = screenWidth / 2 - totalWidth / 2;
        int startY = screenHeight / 2 - (SkillEnum.values().length * (skillSize + 2 * framePadding)) / 2;

        int currentX = startX;

        for (Map.Entry<SkillEffect, List<SkillEnum>> entry : groupedSkills.entrySet()) {
            List<SkillEnum> skillsInColumn = entry.getValue();
            for (int i = 0; i < skillsInColumn.size(); i++) {
                Skill currentSkill = skillsInColumn.get(i).getSkill();
                int skillY = startY + i * (skillSize + 2 * framePadding + 10);  // 10 is the padding between skills

                BoundingBox boundingBox = new BoundingBox(currentX, skillY, currentX + skillSize + 2 * framePadding, skillY + skillSize + 2 * framePadding);
                skillBoundingBoxes.put(skillsInColumn.get(i), boundingBox);

                // Drawing the drop shadow
                drawTexture(matrices, currentX, skillY, 0, 0, frameSize, frameSize, frameSize, frameSize, skillStatuses.get(currentSkill));

                Identifier skillIcon = currentSkill.getIcon();
                drawTexture(matrices, currentX + framePadding, skillY + framePadding, 0, 0, skillSize, skillSize, skillSize, skillSize, skillIcon);
            }
            currentX += skillSize + 2 * framePadding + 10;  // Move to the next column
        }
    }

    private void drawTexture(MatrixStack matrices, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, Identifier icon) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, icon);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        float f = 1.0F / (float)textureWidth;
        float g = 1.0F / (float)textureHeight;
        DrawableHelper.drawTexture(matrices, x, y, u * f, v * g, width, height, textureWidth, textureHeight);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Map.Entry<SkillEnum, BoundingBox> entry : skillBoundingBoxes.entrySet()) {
            SkillEnum skillEnum = entry.getKey();
            BoundingBox box = entry.getValue();
            if (box.contains((int) mouseX, (int) mouseY)) {
                Skill clickedSkill = skillEnum.getSkill();

                PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
                data.writeInt(clickedSkill.getID());
                ClientPlayNetworking.send(TemporaPackets.SKILL_UNLOCK_PACKET, data);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public static void passState(Persist state) {

    }

    public void refresh(MinecraftServer server) {
        if (server != null) {
            loadUnlockedSkills(server);
        }
    }
}

package net.toe.tempora.keybind;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.toe.tempora.Tempora;
import org.lwjgl.glfw.GLFW;

public class Keybindings {

    public static KeyBinding TOGGLE_SKILL_TREE_UI;

    public static void registerKeybindings() {
        Tempora.LOGGER.info("Registering keybindings for " + Tempora.MOD_ID);
        TOGGLE_SKILL_TREE_UI = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.tempora.toggle_skill_tree_ui",
                GLFW.GLFW_KEY_UNKNOWN,
                "category.tempora"
        ));
    }
}

package net.toe.tempora;

import net.fabricmc.api.ModInitializer;
import net.toe.tempora.block.TemporaBlocks;
import net.toe.tempora.item.TemporaItems;
import net.toe.tempora.tool.TemporaTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tempora implements ModInitializer {

	public static final String MOD_ID = "tempora";
	private static final String MOD_NAME = "Tempora";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	@Override
	public void onInitialize() {
		TemporaItems.registerItems();
		TemporaBlocks.registerBlocks();
		TemporaTools.registerTemporaTools();
	}
}

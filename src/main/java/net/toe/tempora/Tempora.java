package net.toe.tempora;

import net.fabricmc.api.ModInitializer;
import net.toe.tempora.item.TemporaItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tempora implements ModInitializer {

	public static final String MOD_ID = "tempora";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		TemporaItems.registerItems();
	}
}

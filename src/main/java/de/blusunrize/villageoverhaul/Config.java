package de.blusunrize.villageoverhaul;

import de.blusunrize.villageoverhaul.features.FletchingFeature;
import de.blusunrize.villageoverhaul.features.RemoveRaidTotemsFeature;
import de.blusunrize.villageoverhaul.features.ResettingCartographerMapsFeature;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = VillageOverhaul.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	private static final ModConfigSpec.BooleanValue FLETCHING_ENABLED = BUILDER
			.push("Fletching Table")
			.comment("Adds a GUI to the fletching table, allowing it to craft arrows from items, with a 50% output increase. Also allows for crafting spectral arrows by adding glowstone, and tipped arrows with splash potions.")
			.define("enabled", true);
	private static final ModConfigSpec.IntValue FLETCHING_COUNT = BUILDER
			.comment("Number of arrows produced in the fletching table. The vanilla recipe produces 4.")
			.defineInRange("arrow_count", 6, 1, 64);

	private static final ModConfigSpec.BooleanValue TOTEMS_ENABLED = BUILDER
			.pop().push("Remove Totems from Raids")
			.comment("Prevent totems of undying from being dropped by evokers, unless they are in a woodland mansion. This makes it impossible to farm them in raids.")
			.define("enabled", true);

	private static final ModConfigSpec.BooleanValue CARTOGRAPHER_ENABLED = BUILDER
			.pop().push("Reset Cartographer Maps")
			.comment("Reset the maps of the cartographer when purchased, so each of them points to a different structure.")
			.define("enabled", true);

	public static final ModConfigSpec SPEC = BUILDER.build();

	@SubscribeEvent
	static void onLoad(final ModConfigEvent event)
	{
		FletchingFeature.ENABLED = FLETCHING_ENABLED.get();
		FletchingFeature.ARROW_OUTPUT_COUNT = FLETCHING_COUNT;
		RemoveRaidTotemsFeature.ENABLED = TOTEMS_ENABLED.get();
		ResettingCartographerMapsFeature.ENABLED = CARTOGRAPHER_ENABLED.get();
	}
}

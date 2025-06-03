package de.blusunrize.villalarevolution;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import de.blusunrize.villalarevolution.features.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import java.util.List;
import java.util.function.Supplier;

@Mod(VillaLaRevolution.MODID)
public class VillaLaRevolution
{
	public static final String MODID = "villalarevolution";
	public static final Logger LOGGER = LogUtils.getLogger();

	private static final List<Supplier<IFeature>> FEATURES = ImmutableList.of(
			FletchingFeature::new,
			RemoveRaidTotemsFeature::new,
			ResettingCartographerMapsFeature::new,
			ReducedGolemSpawnFeature::new,
			TradesRequireGossipFeature::new
	);

	public VillaLaRevolution(IEventBus modEventBus, ModContainer modContainer)
	{
		// Register config
		modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

		// Set up deferred registries
		Registrar.MENU_REGISTER.register(modEventBus);

		// Iterate all features
		FEATURES.forEach(constructor -> {
			IFeature instance = constructor.get();
			// Run initialization
			instance.init(modEventBus);
			// Register for event handling
			if(instance.hasEventHandling())
				NeoForge.EVENT_BUS.register(instance);
		});
	}
}

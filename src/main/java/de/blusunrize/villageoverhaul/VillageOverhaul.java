package de.blusunrize.villageoverhaul;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import de.blusunrize.villageoverhaul.features.FletchingFeature;
import de.blusunrize.villageoverhaul.features.IFeature;
import de.blusunrize.villageoverhaul.features.RemoveRaidTotemsFeature;
import de.blusunrize.villageoverhaul.features.ResettingCartographerMapsFeature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import java.util.List;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(VillageOverhaul.MODID)
public class VillageOverhaul
{
	public static final String MODID = "villageoverhaul";
	public static final Logger LOGGER = LogUtils.getLogger();

	private static final List<Supplier<IFeature>> FEATURES = ImmutableList.of(
			RemoveRaidTotemsFeature::new,
			ResettingCartographerMapsFeature::new,
			FletchingFeature::new
	);

	public VillageOverhaul(IEventBus modEventBus, ModContainer modContainer)
	{
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

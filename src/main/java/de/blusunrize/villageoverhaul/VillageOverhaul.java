package de.blusunrize.villageoverhaul;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import de.blusunrize.villageoverhaul.features.FletchingScreen;
import de.blusunrize.villageoverhaul.features.IFeature;
import de.blusunrize.villageoverhaul.features.RemoveRaidTotemsFeature;
import de.blusunrize.villageoverhaul.features.ResettingCartographerMapsFeature;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
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
			ResettingCartographerMapsFeature::new
	);

	public VillageOverhaul(IEventBus modEventBus, ModContainer modContainer)
	{
		// Register mod bus listeners
		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::registerScreens);

		// Set up deferred registries
		Registrar.MENU_REGISTER.register(modEventBus);

		FEATURES.forEach(constructor -> {
			IFeature instance = constructor.get();
			// Run initialization
			instance.init(modEventBus);
			// Register for event handling
			NeoForge.EVENT_BUS.register(instance);
		});
	}

	private void commonSetup(final FMLCommonSetupEvent event)
	{
	}

	@OnlyIn(Dist.CLIENT)
	private void registerScreens(RegisterMenuScreensEvent event)
	{
		event.register(Registrar.MENU_FLETCHING.get(), FletchingScreen::new);
	}
}

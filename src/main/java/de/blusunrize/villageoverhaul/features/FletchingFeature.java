package de.blusunrize.villageoverhaul.features;

import de.blusunrize.villageoverhaul.Registrar;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public class FletchingFeature implements IFeature
{
	// Menus
	public static final DeferredHolder<MenuType<?>, MenuType<FlechtingMenu>> MENU_FLETCHING = Registrar.MENU_REGISTER.register(
			"fletching_table", () -> new MenuType<>(FlechtingMenu::new, FeatureFlags.DEFAULT_FLAGS)
	);
	// Tags
	public static final TagKey<Item> TAG_FLETCHING = Registrar.modTag("fletching");
	public static final TagKey<Item> TAG_ARROWHEAD = Registrar.modTag("arrowhead");

	@Override
	public void init(IEventBus modEventBus)
	{
		modEventBus.addListener(this::registerScreens);
	}

	@Override
	public boolean hasEventHandling()
	{
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	private void registerScreens(RegisterMenuScreensEvent event)
	{
		event.register(FletchingFeature.MENU_FLETCHING.get(), FletchingScreen::new);
	}
}

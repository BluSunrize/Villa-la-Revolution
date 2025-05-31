package de.blusunrize.villageoverhaul;

import de.blusunrize.villageoverhaul.features.FlechtingMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Registrar
{
	// Deferred registries
	public static final DeferredRegister<MenuType<?>> MENU_REGISTER = DeferredRegister.create(BuiltInRegistries.MENU, VillageOverhaul.MODID);

	// Menus
	public static final DeferredHolder<MenuType<?>, MenuType<FlechtingMenu>> MENU_FLETCHING = MENU_REGISTER.register(
			"fletching_table", () -> new MenuType<>(FlechtingMenu::new, FeatureFlags.DEFAULT_FLAGS)
	);
}

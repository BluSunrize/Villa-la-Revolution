package de.blusunrize.villageoverhaul;

import de.blusunrize.villageoverhaul.features.FlechtingMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
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

	// Tags
	public static final TagKey<Item> TAG_FLETCHING = modTag("fletching");
	public static final TagKey<Item> TAG_ARROWHEAD = modTag("arrowhead");

	public static TagKey<Item> modTag(String name)
	{
		return ItemTags.create(ResourceLocation.fromNamespaceAndPath(VillageOverhaul.MODID, name));
	}
}

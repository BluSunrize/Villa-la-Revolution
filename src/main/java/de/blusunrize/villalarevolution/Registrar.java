package de.blusunrize.villalarevolution;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Registrar
{
	// Deferred registries
	public static final DeferredRegister<MenuType<?>> MENU_REGISTER = DeferredRegister.create(BuiltInRegistries.MENU, VillaLaRevolution.MODID);

	// Utilities
	public static TagKey<Item> modTag(String name)
	{
		return ItemTags.create(ResourceLocation.fromNamespaceAndPath(VillaLaRevolution.MODID, name));
	}
}

package de.blusunrize.villageoverhaul.features;

import com.google.common.collect.ImmutableMap;
import de.blusunrize.villageoverhaul.Registrar;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Map;

public class FletchingFeature implements IFeature
{
	// Menus
	public static final DeferredHolder<MenuType<?>, MenuType<FlechtingMenu>> MENU_FLETCHING = Registrar.MENU_REGISTER.register(
			"fletching_table", () -> new MenuType<>(FlechtingMenu::new, FeatureFlags.DEFAULT_FLAGS)
	);
	// Tags
	public static final TagKey<Item> TAG_FLETCHING = Registrar.modTag("fletching");
	public static final TagKey<Item> TAG_ARROWHEAD = Registrar.modTag("arrowhead");

	public static final int ARROW_OUTPUT_COUNT = 6;

	// Additional ingredients for special arrows
	public static final Map<Ingredient, ArrowConversion> OUTPUT_CONVERSIONS = ImmutableMap.of(
			Ingredient.of(Tags.Items.DUSTS_GLOWSTONE), additionalIngredient -> new ItemStack(Items.SPECTRAL_ARROW, ARROW_OUTPUT_COUNT),
			Ingredient.of(Items.SPLASH_POTION), additionalIngredient -> {
				PotionContents contents = additionalIngredient.get(DataComponents.POTION_CONTENTS);
				if(contents!=null&&contents.potion().isPresent())
				{
					ItemStack tippedArrow = PotionContents.createItemStack(Items.TIPPED_ARROW, contents.potion().get());
					tippedArrow.setCount(ARROW_OUTPUT_COUNT);
					return tippedArrow;
				}
				return ItemStack.EMPTY;
			}
	);

	@Override
	public void init(IEventBus modEventBus)
	{
		if(FMLEnvironment.dist.isClient())
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

	public interface ArrowConversion
	{
		ItemStack convert(ItemStack additionalIngredient);
	}
}

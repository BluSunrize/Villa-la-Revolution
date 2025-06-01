package de.blusunrize.villageoverhaul.features;

import de.blusunrize.villageoverhaul.VillageOverhaul;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.MapDecorations;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.TradeWithVillagerEvent;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Make any cartographer trades for structure maps reset themselves to a different structure after purchasing
 */
public class ResettingCartographerMapsFeature implements IFeature
{
	// Config Values
	public static boolean ENABLED = true;

	@Override
	public boolean hasEventHandling()
	{
		return true;
	}

	@SubscribeEvent
	public void onVillagerTrade(TradeWithVillagerEvent event)
	{
		if(!ResettingCartographerMapsFeature.ENABLED)
			return;
		// Only affect cartographers
		if(!(event.getAbstractVillager() instanceof Villager villager)||villager.getVillagerData().getProfession()!=VillagerProfession.CARTOGRAPHER)
			return;
		// Only affect trades for filled maps
		if(!event.getMerchantOffer().getResult().is(Items.FILLED_MAP))
			return;

		ItemStack map = event.getMerchantOffer().getResult();
		MapDecorations mapData = map.get(DataComponents.MAP_DECORATIONS);
		if(mapData==null)
			return;
		Holder<MapDecorationType> explorationType = null;
		for(MapDecorations.Entry decoration : mapData.decorations().values())
			if(decoration.type().value().explorationMapElement())
				explorationType = decoration.type();

		// No marker matching an exploration element was found
		if(explorationType==null||explorationType.getKey()==null)
			return;

		final ResourceKey<MapDecorationType> typeKey = explorationType.getKey();
		VillagerTrades.TRADES.get(VillagerProfession.CARTOGRAPHER).values().stream()
				.flatMap((Function<ItemListing[], Stream<ItemListing>>)Arrays::stream)
				.filter(listing -> listing instanceof VillagerTrades.TreasureMapForEmeralds mapListing&&mapListing.destinationType.is(typeKey))
				.findFirst().ifPresent(listing -> {
					MerchantOffer newOffer = listing.getOffer(villager, villager.getRandom());
					int offerIndex = villager.getOffers().indexOf(event.getMerchantOffer());
					villager.getOffers().set(offerIndex, newOffer);
					VillageOverhaul.LOGGER.debug("Replaced cartographer trade {} with new trade.", offerIndex);
				});
	}
}

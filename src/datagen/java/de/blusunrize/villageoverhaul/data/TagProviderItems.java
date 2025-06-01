package de.blusunrize.villageoverhaul.data;

import de.blusunrize.villageoverhaul.VillageOverhaul;
import de.blusunrize.villageoverhaul.features.FletchingFeature;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class TagProviderItems extends ItemTagsProvider
{
	public TagProviderItems(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper)
	{
		super(output, lookupProvider, CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()), VillageOverhaul.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider provider)
	{
		tag(FletchingFeature.TAG_FLETCHING).addTag(Tags.Items.FEATHERS);
		tag(FletchingFeature.TAG_ARROWHEAD).add(Items.FLINT).addTag(Tags.Items.NUGGETS_IRON);
	}
}

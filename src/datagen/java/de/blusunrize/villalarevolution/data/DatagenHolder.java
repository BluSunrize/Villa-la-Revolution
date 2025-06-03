package de.blusunrize.villalarevolution.data;

import com.google.common.collect.ImmutableList;
import de.blusunrize.villalarevolution.VillaLaRevolution;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.neoforged.neoforge.common.world.StructureModifiers.AddSpawnsStructureModifier;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = VillaLaRevolution.MODID)
public class DatagenHolder
{
	public static final ResourceKey<StructureModifier> WOODLAND_MANSION = ResourceKey.create(
			Keys.STRUCTURE_MODIFIERS,
			ResourceLocation.fromNamespaceAndPath(VillaLaRevolution.MODID, "woodland_mansion_illagers")
	);

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		CompletableFuture<Provider> lookupProvider = event.getLookupProvider();

		if(event.includeServer())
		{
			event.createDatapackRegistryObjects(new RegistrySetBuilder().add(
					Keys.STRUCTURE_MODIFIERS, bootstrap -> {
						// Static registries only need to be looked up if you need to grab the tag data.
						HolderGetter<Structure> structures = bootstrap.lookup(Registries.STRUCTURE);
						bootstrap.register(WOODLAND_MANSION, new AddSpawnsStructureModifier(
								HolderSet.direct(structures.get(BuiltinStructures.WOODLAND_MANSION).get()),
								ImmutableList.of(
										new SpawnerData(EntityType.PILLAGER, 2, 2, 3),
										new SpawnerData(EntityType.VINDICATOR, 2, 1, 2),
										new SpawnerData(EntityType.ILLUSIONER, 2, 1, 1),
										new SpawnerData(EntityType.WITCH, 1, 1, 1)
								)));
					}));
			event.addProvider(new TagProviderItems(output, lookupProvider, existingFileHelper));
		}
	}
}
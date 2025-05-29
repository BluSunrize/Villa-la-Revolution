package de.blusunrize.villageoverhaul.features;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

/**
 * Make Evokers not draw a totem if they are part of a raid
 */
public class RemoveRaidTotems
{
	@SubscribeEvent
	public void onMobDrops(LivingDropsEvent event)
	{
		if(!(event.getEntity() instanceof Evoker evoker))
			return;
		if(!(evoker.level() instanceof ServerLevel serverLevel))
			return;
		if(!serverLevel.structureManager().getStructureWithPieceAt(evoker.getOnPos(), s -> s.is(BuiltinStructures.WOODLAND_MANSION)).isValid())
			event.getDrops().removeIf(e -> e.getItem().is(Items.TOTEM_OF_UNDYING));
	}
}

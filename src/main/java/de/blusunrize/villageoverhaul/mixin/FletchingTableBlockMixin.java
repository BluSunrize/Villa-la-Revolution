package de.blusunrize.villageoverhaul.mixin;

import de.blusunrize.villageoverhaul.gui.FlechtingMenu;
import de.blusunrize.villageoverhaul.features.FletchingFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.FletchingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FletchingTableBlock.class)
public class FletchingTableBlockMixin extends CraftingTableBlock
{
	@Unique
	private static final Component CONTAINER_TITLE = Component.translatable("villageoverhaul.fletching");

	public FletchingTableBlockMixin(Properties properties)
	{
		super(properties);
	}

	@Inject(method = "useWithoutItem(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;", at = @At("HEAD"), cancellable = true)
	protected void useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> callback)
	{
		if(FletchingFeature.ENABLED)
			if(level.isClientSide)
				callback.setReturnValue(InteractionResult.SUCCESS);
			else
			{
				player.openMenu(this.getMenuProvider(state, level, pos));
				callback.setReturnValue(InteractionResult.CONSUME);
			}
	}

	@Override
	protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos)
	{
		return new SimpleMenuProvider(
				(id, inventory, player) -> new FlechtingMenu(id, inventory, ContainerLevelAccess.create(level, pos)), CONTAINER_TITLE
		);
	}
}

package de.blusunrize.villageoverhaul.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import de.blusunrize.villageoverhaul.features.TradesRequireGossipFeature;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prevent villagers from restocking trades if they haven't gossiped in 5 minutes
 */
@Mixin(Villager.class)
public class VillagerMixin
{
	@Unique
	private final static long GOSSIP_TIMEFRAME = 6000;

	@Shadow
	private long lastGossipTime;

	@Inject(method = "resetNumberOfRestocks()V", at = @At("HEAD"), cancellable = true)
	protected void resetNumberOfRestocks(CallbackInfo ci)
	{
		if(!TradesRequireGossipFeature.ENABLED)
			return;
		Villager villager = (Villager)(Object)this;
		long gameTime = villager.level().getGameTime();
		if(gameTime > lastGossipTime+GOSSIP_TIMEFRAME)
			ci.cancel();
	}

	@ModifyReturnValue(method = "allowedToRestock()Z", at = @At("TAIL"))
	protected boolean allowedToRestock(boolean original)
	{
		if(!original||!TradesRequireGossipFeature.ENABLED)
			return original;
		Villager villager = (Villager)(Object)this;
		long gameTime = villager.level().getGameTime();
		return (gameTime < lastGossipTime+GOSSIP_TIMEFRAME);
	}
}

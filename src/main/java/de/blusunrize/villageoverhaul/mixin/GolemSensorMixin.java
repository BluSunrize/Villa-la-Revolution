package de.blusunrize.villageoverhaul.mixin;

import net.minecraft.world.entity.ai.sensing.GolemSensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Reduce iron golem spawns to once every 5 minutes instead of 30 seconds
 */
@Mixin(GolemSensor.class)
public class GolemSensorMixin
{
	@ModifyArg(method = "golemDetected", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/Brain;setMemoryWithExpiry(Lnet/minecraft/world/entity/ai/memory/MemoryModuleType;Ljava/lang/Object;J)V"), index = 2)
	private static long modifyTime(long timeToLive)
	{
		return 5999;
	}
}

package de.blusunrize.villalarevolution.features;

import net.neoforged.bus.api.IEventBus;

public interface IFeature
{
	default void init(IEventBus modEventBus) {}

	default boolean hasEventHandling() {
		return false;
	}

}

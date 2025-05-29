package de.blusunrize.villageoverhaul;

import com.mojang.logging.LogUtils;
import de.blusunrize.villageoverhaul.features.RemoveRaidTotems;
import de.blusunrize.villageoverhaul.features.ResettingCartographerMaps;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(VillageOverhaul.MODID)
public class VillageOverhaul
{
    public static final String MODID = "villageoverhaul";
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public VillageOverhaul(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register features
        NeoForge.EVENT_BUS.register(new RemoveRaidTotems());
        NeoForge.EVENT_BUS.register(new ResettingCartographerMaps());
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }
}

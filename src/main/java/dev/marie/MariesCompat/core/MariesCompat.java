package dev.marie.MariesCompat.core;

import com.mojang.logging.LogUtils;
import dev.marie.MariesCompat.client.config.MariesCompatConfigScreen;
import dev.marie.MariesCompat.compat.lso.LSOCompat;
import dev.marie.MariesCompat.compat.peakstamina.PeakStaminaCompat;
import dev.marie.MariesCompat.compat.spiceoflifeonion.SpiceOfLifeOnionCompat;
import dev.marie.MariesCompat.compat.saturation.network.SaturationDisplayNetworking;
import dev.marie.MariesCompat.config.MariesCompatConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

@Mod("mariescompat")
public class MariesCompat {

    public static final String MODID = "mariescompat";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MariesCompat(IEventBus modEventBus, ModContainer modContainer) {
        MariesCompatConfig.register(modContainer);
        modEventBus.addListener(MariesCompatConfig::onModConfigLoading);
        modEventBus.addListener(MariesCompatConfig::onModConfigReloading);
        SaturationDisplayNetworking.register(modEventBus);

        if (ModList.get().isLoaded("peakstamina")) {
            PeakStaminaCompat.register();
        }
        if (ModList.get().isLoaded("solonion")) {
            SpiceOfLifeOnionCompat.register();
        }
        if (ModList.get().isLoaded("legendarysurvivaloverhaul")) {
            LSOCompat.register();
        }

        if (FMLEnvironment.dist == Dist.CLIENT) {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class,
                    (minecraft, parent) -> MariesCompatConfigScreen.create(parent));
        }

        LOGGER.info("Marie's Compat loaded.");
    }
}

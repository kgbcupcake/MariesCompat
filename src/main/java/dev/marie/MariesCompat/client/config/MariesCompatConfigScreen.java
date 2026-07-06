package dev.marie.MariesCompat.client.config;

import dev.marie.MariesCompat.config.MariesCompatConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class MariesCompatConfigScreen {

    private MariesCompatConfigScreen() {}

    public static Screen create(Screen parent) {
        MariesCompatConfig config = MariesCompatConfig.get();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Marie's Compat Config"))
                .setAlwaysShowTabs(true);
        ConfigEntryBuilder eb = builder.entryBuilder();

        ConfigCategory peakStamina = builder.getOrCreateCategory(Component.literal("Peak Stamina"));
        peakStamina.addEntry(eb.startBooleanToggle(Component.literal("Stamina Usage Modifier"), config.enablePSStaminaUsage())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Penalises stamina usage when nutrition is low; reduces it when high."))
                .setSaveConsumer(config::setEnablePSStaminaUsage)
                .build());
        peakStamina.addEntry(eb.startBooleanToggle(Component.literal("Penalty Decay Modifier"), config.enablePSPenaltyDecay())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Speeds up penalty decay when nutrition is high."))
                .setSaveConsumer(config::setEnablePSPenaltyDecay)
                .build());
        peakStamina.addEntry(eb.startBooleanToggle(Component.literal("Exhaustion Duration Modifier"), config.enablePSExhaustionDuration())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Extends exhaustion duration when nutrition is low."))
                .setSaveConsumer(config::setEnablePSExhaustionDuration)
                .build());

        ConfigCategory spiceOfLife = builder.getOrCreateCategory(Component.literal("Spice of Life: Onion"));
        spiceOfLife.addEntry(eb.startBooleanToggle(Component.literal("Diversity Health Bonus"), config.enableSOLDiversityHealth())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Grants max health bonus when food diversity is high."))
                .setSaveConsumer(config::setEnableSOLDiversityHealth)
                .build());
        spiceOfLife.addEntry(eb.startBooleanToggle(Component.literal("Diversity Health Penalty"), config.enableSOLDiversityPenalty())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Applies max health penalty when food diversity is low."))
                .setSaveConsumer(config::setEnableSOLDiversityPenalty)
                .build());

        ConfigCategory lso = builder.getOrCreateCategory(Component.literal("Legendary Survival Overhaul"));
        lso.addEntry(eb.startBooleanToggle(Component.literal("Thermal Resistance Modifier"), config.enableLSOThermalResistance())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Adjusts thermal resistance based on overall nutrition level."))
                .setSaveConsumer(config::setEnableLSOThermalResistance)
                .build());
        lso.addEntry(eb.startBooleanToggle(Component.literal("Broken Heart Resilience Modifier"), config.enableLSOBrokenHeartResilience())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Increases broken heart resilience when nutrition is high."))
                .setSaveConsumer(config::setEnableLSOBrokenHeartResilience)
                .build());
        lso.addEntry(eb.startBooleanToggle(Component.literal("Thirst Saturation"), config.enableLSOThirstSaturation())
                .setDefaultValue(true)
                .setTooltip(Component.literal("Adds thirst saturation when a nutrition source is applied."))
                .setSaveConsumer(config::setEnableLSOThirstSaturation)
                .build());

        builder.setSavingRunnable(MariesCompatConfig::saveNow);
        return builder.build();
    }
}

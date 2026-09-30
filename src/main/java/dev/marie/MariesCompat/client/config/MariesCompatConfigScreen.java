package dev.marie.MariesCompat.client.config;

import dev.marie.MariesCompat.compat.saturation.SaturationDisplayMode;
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

        ConfigCategory saturation = builder.getOrCreateCategory(Component.literal("Saturation Tweaks"));
        saturation.addEntry(eb.startBooleanToggle(Component.literal("Enable Saturation Tweaks"), config.enableSaturationTweaks())
                .setDefaultValue(false)
                .setTooltip(Component.literal("Master toggle. Off by default so installing this mod never changes hunger mechanics unless you opt in."))
                .setSaveConsumer(config::setEnableSaturationTweaks)
                .build());
        saturation.addEntry(eb.startDoubleField(Component.literal("Max Exhaustion"), config.maxExhaustion())
                .setDefaultValue(4.0)
                .setMin(0.1)
                .setMax(1000.0)
                .setTooltip(Component.literal("Exhaustion level that must be reached before hunger/saturation drains. Vanilla default is 4.0 — raise it to make food last longer."))
                .setSaveConsumer(config::setMaxExhaustion)
                .build());
        saturation.addEntry(eb.startIntField(Component.literal("Saturation Cap"), config.saturationCap())
                .setDefaultValue(-1)
                .setMin(-1)
                .setTooltip(Component.literal("Maximum saturation level. -1 disables the cap (vanilla behavior)."))
                .setSaveConsumer(config::setSaturationCap)
                .build());
        saturation.addEntry(eb.startBooleanToggle(Component.literal("Hunger Limits Saturation"), config.hungerLimitsSaturation())
                .setDefaultValue(false)
                .setTooltip(Component.literal("If enabled, saturation can never exceed your current food level."))
                .setSaveConsumer(config::setHungerLimitsSaturation)
                .build());
        saturation.addEntry(eb.startBooleanToggle(Component.literal("Always Can Eat"), config.alwaysCanEat())
                .setDefaultValue(false)
                .setTooltip(Component.literal("If enabled, you can always eat regardless of food level."))
                .setSaveConsumer(config::setAlwaysCanEat)
                .build());
        saturation.addEntry(eb.startEnumSelector(Component.literal("Saturation Display"), SaturationDisplayMode.class, config.saturationDisplayMode())
                .setDefaultValue(SaturationDisplayMode.NEVER)
                .setTooltip(Component.literal("ALWAYS: always show the raw saturation number. BEYOND_MAX: only show it once saturation exceeds what AppleSkin's hunger-bar overlay can visually represent (20). NEVER: don't show it."))
                .setSaveConsumer(config::setSaturationDisplayMode)
                .build());
        saturation.addEntry(eb.startBooleanToggle(Component.literal("Exhaustion Displayed"), config.exhaustionDisplayed())
                .setDefaultValue(false)
                .setTooltip(Component.literal("Shows exhaustion as a percentage of the threshold needed to drain hunger/saturation."))
                .setSaveConsumer(config::setExhaustionDisplayed)
                .build());
        builder.setSavingRunnable(MariesCompatConfig::saveNow);
        return builder.build();
    }
}

package xyz.enkdev.repairify.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import xyz.enkdev.repairify.Repairify;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class RepairifyConfig
{
    public static final String CATEGORY_SETTINGS = "settings";
    public static final String CATEGORY_GENERAL = "general";

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.ConfigValue<List<String>> REPAIR_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<String> REPAIR_SOUND;
    public static ForgeConfigSpec.DoubleValue SOUND_PITCH;
    public static ForgeConfigSpec.DoubleValue SOUND_VOLUME;

    static
    {
        COMMON_BUILDER.comment("Repairify 3.0.0 | Settings").push(CATEGORY_SETTINGS);
        COMMON_BUILDER.pop();

        setupGeneralConfig();

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupGeneralConfig()
    {
        COMMON_BUILDER.comment("General Settings").push(CATEGORY_GENERAL);

        REPAIR_BLACKLIST = COMMON_BUILDER
                .comment("Items which shouldn't be repaired with this mod. Use this if you're making a Modpack.")
                .translation(Repairify.MOD_ID + ".config.repairBlackList")
                .define("repairBlackList", new ArrayList<>());

        REPAIR_SOUND = COMMON_BUILDER
                .comment("The sound which plays upon a successful repair.")
                .translation(Repairify.MOD_ID + ".config.repairSound")
                .define("repairSound", "minecraft:entity.experience_orb.pickup");

        SOUND_PITCH = COMMON_BUILDER
                .comment("The pitch of the sound. The higher the pitch, the higher the sound.")
                .translation(Repairify.MOD_ID + ".config.soundPitch")
                .defineInRange("soundPitch", 0.0, 0.0, 1.0);

        SOUND_VOLUME = COMMON_BUILDER
                .comment("The volume of the sound. The higher the volume, the louder the sound.")
                .translation(Repairify.MOD_ID + ".config.soundVolume")
                .defineInRange("soundVolume", 1.0, 0.0, 1.0);

        COMMON_BUILDER.pop();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path)
    {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent)
    {
    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent)
    {
    }
}
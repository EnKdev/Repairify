package xyz.enkdev.repairify.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import xyz.enkdev.repairify.Repairify;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static ForgeConfigSpec spec;

    private static ForgeConfigSpec.ConfigValue<List<String>> repairBlackList;
    private static ForgeConfigSpec.ConfigValue<String> repairSound;
    private static ForgeConfigSpec.DoubleValue soundPitch;
    private static ForgeConfigSpec.DoubleValue soundVolume;

    static
    {
        initConfig();
    }

    private static void initConfig()
    {
        BUILDER.comment("Repairify 2.0.0 | Settings").push(Repairify.MOD_ID);

        repairBlackList = BUILDER
                .comment("List of items which shouldn't be repaired with this mod. Use this if you're making a modpack. [Default: Empty]")
                .translation(Repairify.MOD_ID + ".config.repairBlackList")
                .define("repairBlackList", new ArrayList<>());

        repairSound = BUILDER
                .comment("The sound which should be played after a successful repair check [Default: minecraft:entity.experience_orb_pickup]")
                .translation(Repairify.MOD_ID + ".config.repairSound")
                .define("repairSound", "minecraft:entity.experience_orb_pickup");

        soundPitch = BUILDER
                .comment("The pitch the sound is played with. The higher the pitch, the higher the sound. [Default: 0.0]")
                .translation(Repairify.MOD_ID + ".config.soundPitch")
                .defineInRange("soundPitch", 0.0, 0.0, 1.0);

        soundVolume = BUILDER
                .comment("The volume the sound is played with. The higher the volume, the louder the sound. [Default: 1.0]")
                .translation(Repairify.MOD_ID + ".config.soundVolume")
                .defineInRange("soundVolume", 1.0, 0.0, 1.0);

        BUILDER.pop();

        BUILDER.build();
    }

    public ConfigManager(Path path)
    {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();

        spec.setConfig(configData);
    }

    public static List<String> getRepairBlackList()
    {
        return repairBlackList.get();
    }

    public String getRepairSound()
    {
        return repairSound.get();
    }

    public double getSoundPitch()
    {
        return soundPitch.get();
    }

    public double getSoundVolume()
    {
        return soundVolume.get();
    }
}

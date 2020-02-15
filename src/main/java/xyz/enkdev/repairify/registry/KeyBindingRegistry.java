package xyz.enkdev.repairify.registry;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.settings.KeyBinding;
import xyz.enkdev.repairify.Repairify;

public class KeyBindingRegistry
{
    private static final String KEY_PREFIX = "key." + Repairify.MOD_ID + ".";
    private static final String KEY_CATEGORY = "key.categories." + Repairify.MOD_ID;

    public static final KeyBinding REPAIR_KEY = new KeyBinding(KEY_PREFIX + "repairItem", GLFW.GLFW_KEY_R, KEY_CATEGORY);
}

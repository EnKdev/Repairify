package xyz.enkdev.repairify.registry;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import xyz.enkdev.repairify.Repairify;

@Mod.EventBusSubscriber(modid = Repairify.MOD_ID, value = Dist.CLIENT)
public class KeyBindingRegistry
{
    private static final String KEY_PREFIX = "key." + Repairify.MOD_ID + ".";
    private static final String KEY_CATEGORY = "key.categories." + Repairify.MOD_ID;

    public static final KeyBinding REPAIR_KEY = new KeyBinding(KEY_PREFIX + "repairItem", GLFW.GLFW_KEY_R, KEY_CATEGORY);

    // Uncomment this once we got the Networking down. Otherwise keep this commented.

    /*@SubscribeEvent
    public static void handleRepairOnKeybindStroke(InputEvent.KeyInputEvent event)
    {
        PlayerEntity player = Minecraft.getInstance().player;

        if (player != null)
        {
            if (REPAIR_KEY.isPressed() && getItemToRepairInSlot(player) != -1 && (!Arrays.asList(ConfigManager.getRepairBlackList()).contains(player.getHeldItemMainhand().getItem().getRegistryName().toString())))
            {

            }
        }
    }*/
}

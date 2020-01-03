package xyz.enkdev.repairify.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Level;
import org.lwjgl.glfw.GLFW;

import xyz.enkdev.repairify.Repairify;
import xyz.enkdev.repairify.config.RepairifyConfig;
import xyz.enkdev.repairify.network.RepairifyPacketHandler;
import xyz.enkdev.repairify.network.packets.RepairPacket;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = Repairify.MOD_ID, value = Dist.CLIENT)
public class KeyBindingRegistry
{
    private static final String KEY_PREFIX = "key." + Repairify.MOD_ID + ".";
    private static final String KEY_CATEGORY = "key.categories." + Repairify.MOD_ID;

    public static final KeyBinding REPAIR_KEY = new KeyBinding(KEY_PREFIX + "repairItem", GLFW.GLFW_KEY_R, KEY_CATEGORY);

    @SubscribeEvent
    public static void handleRepairOnKeybindStroke(InputEvent.KeyInputEvent event)
    {
        PlayerEntity player = Minecraft.getInstance().player;

        if (player != null)
        {
            if (REPAIR_KEY.isPressed() && getItemToRepairInSlot(player) != -1 &&
                    (!Arrays.asList(RepairifyConfig.REPAIR_BLACKLIST)
                            .contains(player.getHeldItemMainhand()
                                    .getItem()
                                    .getRegistryName()
                                    .toString())))
            {
                RepairifyPacketHandler.INSTANCE.sendToServer(new RepairPacket(GLFW.GLFW_KEY_R));

                player.world.playSound(
                        player, player.posX, player.posY, player.posZ,
                        ForgeRegistries.SOUND_EVENTS.getValue(
                                new ResourceLocation(RepairifyConfig.REPAIR_SOUND.get())
                        ),
                        SoundCategory.AMBIENT, ((float) RepairifyConfig.SOUND_VOLUME.get().doubleValue()), ((float) RepairifyConfig.SOUND_PITCH.get().doubleValue())
                );
            }
        }
        else
            Repairify.LOGGER.log(Level.INFO, "The player is null, cannot repair like that!");
    }

    public static int getItemToRepairInSlot(PlayerEntity player)
    {
        ItemStack handItem = player.getHeldItemMainhand();

        if (handItem.isDamaged() && handItem.getItem().isRepairable(handItem))
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemStack = player.inventory.getStackInSlot(i);

                if (itemStack != handItem &&
                        itemStack.isItemEqualIgnoreDurability(handItem) &&
                        itemStack.getEnchantmentTagList().equals(handItem.getEnchantmentTagList()))
                {
                    return i;
                }
            }
        }

        return -1;
    }
}

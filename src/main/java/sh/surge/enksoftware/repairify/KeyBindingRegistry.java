package sh.surge.enksoftware.repairify;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Level;
import org.lwjgl.input.Keyboard;
import sh.surge.enksoftware.repairify.network.CPacketRepairify;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = Repairify.MODID, value = Side.CLIENT)
public class KeyBindingRegistry
{
    private static final String KEY_PREFIX = "key." + Repairify.MODID + ".";
    private static final String KEY_CATEGORY = "key.categories." + Repairify.MODID;


    public static final KeyBinding REPAIR_KEY = new KeyBinding(KEY_PREFIX + "repairItem", KeyConflictContext.IN_GAME, Keyboard.KEY_R, KEY_CATEGORY);

    @SubscribeEvent
    public static void handleRepairOnKeybindStroke(InputEvent.KeyInputEvent event)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;

        if (player != null)
        {
            if (REPAIR_KEY.isPressed() &&
                    getItemToRepairInSlot(player) != -1 &&
                    (!Arrays.asList(RepairifyConfig.repairBlackList).contains(
                            player.getHeldItemMainhand().getItem().getRegistryName().toString())
                    ))
            {
                Repairify.NETWORK.sendToServer(new CPacketRepairify());

                player.world.playSound(
                        player, player.posX, player.posY, player.posZ,
                        ForgeRegistries.SOUND_EVENTS.getValue(
                                new ResourceLocation(RepairifyConfig.repairSoundCue)
                        ),
                        SoundCategory.AMBIENT, RepairifyConfig.soundCueVolume, RepairifyConfig.soundCuePitch
                );
            }
        }
        else
            Repairify.LOGGER.log(Level.INFO, "The player is null, cannot repair like that!");
    }

    public static int getItemToRepairInSlot(EntityPlayer player)
    {
        ItemStack handItem = player.getHeldItemMainhand();

        if (handItem.isItemDamaged() && handItem.getItem().isRepairable())
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemStack = player.inventory.getStackInSlot(i);

                if (itemStack != handItem && itemStack.isItemEqualIgnoreDurability(handItem) &&
                    itemStack.getEnchantmentTagList().equals(handItem.getEnchantmentTagList()))
                {
                    return i;
                }
            }
        }
        return -1;
    }


}

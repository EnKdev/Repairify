package xyz.enkdev.repairify.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.enkdev.repairify.Repairify;
import xyz.enkdev.repairify.config.RepairifyConfig;
import xyz.enkdev.repairify.network.RepairifyPacketHandler;
import xyz.enkdev.repairify.network.packets.RepairPacket;
import xyz.enkdev.repairify.registry.KeyBindingRegistry;
import xyz.enkdev.repairify.util.RepairUtils;

public class KeybindEvents
{
    public static void handleRepairOnKeybindStroke(InputEvent.KeyInputEvent event)
    {
        ClientPlayerEntity player = Minecraft.getInstance().player;

        if (player != null)
        {
            if (KeyBindingRegistry.REPAIR_KEY.isPressed() && RepairUtils.getItemToRepairInSlot(player) != -1 &&
                !RepairifyConfig.REPAIR_BLACKLIST.get()
                    .contains(player.getHeldItemMainhand()
                        .getItem()
                        .getRegistryName()
                        .toString()))
            {
                RepairifyPacketHandler.INSTANCE.sendToServer(new RepairPacket());

                final SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(
                    new ResourceLocation(RepairifyConfig.REPAIR_SOUND.get())
                );

                if (sound != null)
                {
                    Minecraft.getInstance().world.playSound(
                        player.getPosX(), player.getPosY(), player.getPosZ(),
                        sound,
                        SoundCategory.AMBIENT,
                        RepairifyConfig.SOUND_VOLUME.get().floatValue(),
                        RepairifyConfig.SOUND_PITCH.get().floatValue(),
                        false
                    );
                }
            }
        }
        else
            Repairify.LOGGER.info("The player is null, cannot repair like that!");
    }
}

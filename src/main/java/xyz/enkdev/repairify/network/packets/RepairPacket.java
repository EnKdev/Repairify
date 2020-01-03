package xyz.enkdev.repairify.network.packets;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;
import xyz.enkdev.repairify.config.RepairifyConfig;
import xyz.enkdev.repairify.registry.KeyBindingRegistry;

import java.util.Arrays;
import java.util.function.Supplier;

public class RepairPacket
{
    private int key;

    public RepairPacket(int key)
    {
        this.key = key;
    }

    private RepairPacket()
    {
    }

    public static void handle(RepairPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.get().getSender();

            int slot = KeyBindingRegistry.getItemToRepairInSlot(sender);
            ItemStack held = sender.getHeldItemMainhand();

            if (slot != -1 &&
                    !Arrays.asList(RepairifyConfig.REPAIR_BLACKLIST)
                            .contains(sender.getHeldItemMainhand()
                                    .getItem()
                                    .getRegistryName()
                                    .toString()))
            {
                ItemStack inv = sender.inventory.getStackInSlot(slot);

                int remainA = inv.getMaxDamage() - inv.getDamage();
                int remainB = inv.getMaxDamage() - held.getDamage();
                int newDurability = remainA + remainB + (inv.getMaxDamage() * 5 / 100);
                int damage = inv.getMaxDamage() - newDurability;

                held.setDamage(damage);
                inv.shrink(1);

                sender.inventory.setInventorySlotContents(slot, inv);
                sender.setHeldItem(Hand.MAIN_HAND, held);
            }
        });
    }

    public static RepairPacket decode(PacketBuffer buf)
    {
        RepairPacket message = new RepairPacket();
        message.key = buf.readInt();
        return message;
    }

    public static void encode(RepairPacket msg, PacketBuffer buf)
    {
        buf.writeInt(msg.key);
    }
}

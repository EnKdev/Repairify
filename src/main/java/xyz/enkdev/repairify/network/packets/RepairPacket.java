package xyz.enkdev.repairify.network.packets;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;
import xyz.enkdev.repairify.config.RepairifyConfig;
import xyz.enkdev.repairify.util.RepairUtils;

import java.util.function.Supplier;

public class RepairPacket
{
    public RepairPacket()
    {
    }

    public static void handle(RepairPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ServerPlayerEntity sender = ctx.get().getSender();

        ctx.get().enqueueWork(() ->
        {
            int slot = RepairUtils.getItemToRepairInSlot(sender);
            ItemStack held = sender.getHeldItemMainhand();

            if (slot != -1 &&
                !RepairifyConfig.REPAIR_BLACKLIST.get()
                .contains(
                    held.getItem()
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

        ctx.get().setPacketHandled(true);
    }

    public static RepairPacket decode(PacketBuffer buf)
    {
        return new RepairPacket();
    }

    public static void encode(RepairPacket msg, PacketBuffer buf)
    {
    }
}

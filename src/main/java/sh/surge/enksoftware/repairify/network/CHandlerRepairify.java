package sh.surge.enksoftware.repairify.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sh.surge.enksoftware.repairify.KeyBindingRegistry;
import sh.surge.enksoftware.repairify.RepairifyConfig;

import java.util.Arrays;

public class CHandlerRepairify implements IMessageHandler<CPacketRepairify, IMessage>
{
    @Override
    public IMessage onMessage(CPacketRepairify message, MessageContext ctx)
    {
        EntityPlayerMP player = ctx.getServerHandler().player;
        WorldServer world = player.getServerWorld();

        world.addScheduledTask(() ->
        {
            int slot = KeyBindingRegistry.getItemToRepairInSlot(player);
            ItemStack held = player.getHeldItemMainhand();

            if (slot != -1 &&
                    !Arrays.asList(RepairifyConfig.repairBlackList).contains(player.getHeldItemMainhand().getItem().getRegistryName().toString())
            )
            {
                ItemStack inv = player.inventory.getStackInSlot(slot);

                int remainA = inv.getMaxDamage() - inv.getItemDamage();
                int remainB = inv.getMaxDamage() - held.getItemDamage();
                int newDurability = remainA + remainB + (inv.getMaxDamage() * 5 / 100);
                int damage = inv.getMaxDamage() - newDurability;

                held.setItemDamage(damage);
                inv.shrink(1);

                player.inventory.setInventorySlotContents(slot, inv);
                player.setHeldItem(EnumHand.MAIN_HAND, held);
            }
        });

        return null;
    }
}

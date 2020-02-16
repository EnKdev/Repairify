package xyz.enkdev.repairify.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class RepairUtils
{
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

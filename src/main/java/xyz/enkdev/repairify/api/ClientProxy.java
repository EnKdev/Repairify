package xyz.enkdev.repairify.api;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import xyz.enkdev.repairify.registry.KeyBindingRegistry;

@OnlyIn(Dist.CLIENT)
public class ClientProxy implements IProxy
{
    @Override
    public void init()
    {
        ClientRegistry.registerKeyBinding(KeyBindingRegistry.REPAIR_KEY);
    }

    @Override
    public World getClientWorld()
    {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer()
    {
        return Minecraft.getInstance().player;
    }
}

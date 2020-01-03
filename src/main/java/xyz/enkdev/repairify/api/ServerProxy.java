package xyz.enkdev.repairify.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ServerProxy implements IProxy
{
    @Override
    public void init()
    {
    }

    @Override
    public World getClientWorld()
    {
        throw new IllegalStateException("Client-side only code on server-side!");
    }

    @Override
    public PlayerEntity getClientPlayer()
    {
        throw new IllegalStateException("Jim, I am not sure how you got this error, but I believe you performed something you weren't supposed to do. (Only run this on the client-side!)");
    }
}

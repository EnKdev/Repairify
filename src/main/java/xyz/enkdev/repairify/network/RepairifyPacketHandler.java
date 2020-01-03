package xyz.enkdev.repairify.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import xyz.enkdev.repairify.Repairify;
import xyz.enkdev.repairify.network.packets.RepairPacket;

public class RepairifyPacketHandler
{
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Repairify.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init()
    {
        int regId = 0;
        INSTANCE.registerMessage(regId++, RepairPacket.class, RepairPacket::encode, RepairPacket::decode, RepairPacket::handle);
    }
}

package sh.surge.enksoftware.repairify.proxy;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import sh.surge.enksoftware.repairify.KeyBindingRegistry;

public class ClientProxy implements IProxy
{
    @Override
    public void init(FMLInitializationEvent event)
    {
        ClientRegistry.registerKeyBinding(KeyBindingRegistry.REPAIR_KEY);
    }
}

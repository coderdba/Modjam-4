package mod.badores.server;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mod.badores.BOProxy;
import mod.badores.network.PacketRandomTranslation;
import net.minecraftforge.common.config.Configuration;

/**
 * @author diesieben07
 */
public class BOServerProxy implements BOProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event, Configuration config) {

	}

	@Override
	public void init(FMLInitializationEvent event) {

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {

	}

	@Override
	public void openRandomWebsite() {

	}

	@Override
	public void handleRandomTranslation(PacketRandomTranslation message) {

	}
}

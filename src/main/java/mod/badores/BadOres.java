package mod.badores;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import mod.badores.event.TickEvents;
import mod.badores.items.ItemBOIngot;
import mod.badores.network.PacketRandomTranslation;
import mod.badores.ore.*;
import mod.badores.oremanagement.OreManager;
import mod.badores.worldgen.WorldGeneratorBadOres;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;
import sun.java2d.pipe.SpanShapeRenderer;

/**
 * @author diesieben07
 */
@Mod(modid = BadOres.MOD_ID, name = "BadOres", version = BadOres.VERSION)
public class BadOres {

	public static final String MOD_ID = "badores";
    public static final String VERSION = "0.1";

	@SidedProxy(clientSide = "mod.badores.client.BOClientProxy", serverSide = "mod.badores.server.BOServerProxy")
	public static BOProxy proxy;

    public static Logger logger;

	public static boolean devEnv = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    public static boolean gameBreakingFeatures;

	public static CreativeTabs creativeTab = new CreativeTabs("badores") {
		@Override
		public Item getTabIconItem() {
			// TODO
			return Items.apple;
		}
	};

	public static OreManager oreManager;

    public static ItemBOIngot ingot;

	public static SimpleNetworkWrapper network;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        gameBreakingFeatures = config.get("balancing", "enableGameBreakingFeatures", true).getBoolean(true); // TODO implement

		oreManager = new OreManager();

		oreManager.registerOre(new Polite());
		oreManager.registerOre(new Wannafite());
        oreManager.registerOre(new Breakium());
        oreManager.registerOre(new Crashium());
        oreManager.registerOre(new Stonium());
		oreManager.registerOre(new Crappium());
		oreManager.registerOre(new Enderite());
		oreManager.registerOre(new Website());
        oreManager.registerOre(new Lite());
        oreManager.registerOre(new Misleadium());
        oreManager.registerOre(new Ghostium());
        oreManager.registerOre(new Amadeum());
        oreManager.registerOre(new IncrediblyRarium());
        oreManager.registerOre(new Unobtainium());
        oreManager.registerOre(new Copper());
        oreManager.registerOre(new Uselessium());
        oreManager.registerOre(new Killium());
        oreManager.registerOre(new Balancium());

		ingot = new ItemBOIngot();
		GameRegistry.registerItem(ingot, "ingot");

		oreManager.createGameElements();

		proxy.preInit(event, config);
        config.save();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new WorldGeneratorBadOres(oreManager), 100);

		network = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
		network.registerMessage(PacketRandomTranslation.Handle.class, PacketRandomTranslation.class, 0, Side.CLIENT);

		FMLCommonHandler.instance().bus().register(TickEvents.INSTANCE);

        proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	public static String translateKey(String name) {
		return MOD_ID + "." + name;
	}

	public static String getTextureName(String name) {
		return MOD_ID + ":" + name;
	}
}

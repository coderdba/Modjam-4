package mod.badores.oremanagement;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.List;
import java.util.Random;

/**
 * @author diesieben07
 */
public interface BadOre {

	boolean hasTools();

	boolean hasArmor();

	boolean hasIngot();

	ToolInfo getToolInfo();

	ArmorInfo getArmorInfo();

	void onHarvest(EntityPlayer miner, World world, int x, int y, int z, Side side);

	void onRemove(EntityPlayer miner, World world, int x, int y, int z, Side side);

	void onToolMine(ToolType type, EntityPlayer player, World world, int x, int y, int z, Block block, Side side);

	void onToolEntityAttack(ToolType type, EntityPlayer player, EntityLivingBase target, World world, Side side);

	void onArmorTick(ArmorType type, EntityPlayer player, World world, Side side);

	void onInventoryTick(ItemStack stack, EntityPlayer player, World world, Side side);

	String getName();

	String getIconName();

	String getIngotIconName();

	String getArmorIconName(ArmorType type);

	List<ItemStack> getDroppedItems(World world, int x, int y, int z, BlockInfo blockInfo, int meta, int fortune);

	Entity createDropEntity(World world, double x, double y, double z, ItemStack stack);

	void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider);

	int lightLevel();

	int initialTickRate();

	void tick(World world, int x, int y, int z, BlockInfo blockInfo, Random random, Side side);

	float oreHardness();

	String getDisplayName();

	String getDisplayName(OreSubName name);

	float getExplosionResistance();

	boolean shouldSelectionRayTrace();

	float getSmeltingXP();
}

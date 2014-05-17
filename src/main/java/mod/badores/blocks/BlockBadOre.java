package mod.badores.blocks;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import mod.badores.BadOres;
import mod.badores.items.ItemBlockBadOre;
import mod.badores.oremanagement.BadOre;
import mod.badores.util.Sides;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author diesieben07
 */
public class BlockBadOre extends BOBlock {

	private static int instanceCounter = 0;
	private BadOre[] ores = new BadOre[16];

	private IIcon[] icons = new IIcon[16];

	public BlockBadOre() {
		super(Material.rock);
		setCreativeTab(BadOres.creativeTab);
		setResistance(5.0F);
		setStepSound(soundTypePiston);

		GameRegistry.registerBlock(this, ItemBlockBadOre.class, "badOre" + (instanceCounter++));
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < ores.length; ++i) {
			if (ores[i] != null) {
				//noinspection unchecked
				list.add(new ItemStack(item, 1, i));
			}
		}
	}

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        return BadOres.oreManager.getBlockInfo(getOre(world.getBlockMetadata(x, y, z))).asStack();
    }

    public void addOre(int metadata, BadOre ore) {
		ores[metadata] = ore;
	}

	public BadOre getOre(int metadata) {
		return metadata < ores.length ? ores[metadata] : ores[0];
	}

	public BadOre getOre(ItemStack stack) {
		return getOre(stack.getItemDamage());
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		super.harvestBlock(world, player, x, y, z, metadata);
		// side is always server
		getOre(metadata).onHarvest(player, world, x, y, z, Side.SERVER);
	}

	// evil hack, kids don't try this at home
	private int dropMeta = -1;

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		dropMeta = metadata;
		ArrayList<ItemStack> result = Lists.newArrayListWithCapacity(1);
		BadOre ore = getOre(metadata);
		result.addAll(ore.getDroppedItems(world, x, y, z, metadata, fortune));
		return result;
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		getOre(world.getBlockMetadata(x, y, z)).onRemove(player, world, x, y, z, Sides.logical(world));
		return super.removedByPlayer(world, player, x, y, z);
	}

	@Override
	protected void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack) {
		if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
			float f = 0.7F;
			double eX = (world.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
			double eY = (world.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
			double eZ = (world.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
			world.spawnEntityInWorld(getOre(dropMeta).createDropEntity(world, x + eX, y + eY, z + eZ, stack));
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon(BadOres.MOD_ID + ":" + "oreGeneric");

		for (int i = 0; i < ores.length; ++i) {
			if (ores[i] != null) {
				icons[i] = iconRegister.registerIcon(ores[i].getIconName());
			}
		}
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		BadOre ore = getOre(world.getBlockMetadata(x, y, z));

		int tickRate = ore.initialTickRate();
		if (tickRate >= 0)
			world.scheduleBlockUpdate(x, y, z, this, tickRate);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		BadOre ore = getOre(world.getBlockMetadata(x, y, z));
		ore.tick(world, x, y, z, BadOres.oreManager.getBlockInfo(ore), random, Sides.logical(world));
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return getOre(world.getBlockMetadata(x, y, z)).getHardness(world, x, y, z);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (icons[meta] != null)
			return icons[meta];

		return super.getIcon(side, meta);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return getOre(world.getBlockMetadata(x, y, z)).lightLevel();
	}

	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		return getOre(world.getBlockMetadata(x, y, z)).getExplosionResistance(world, x, y, z);
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 ray, Vec3 ray2) {
		if (getOre(world.getBlockMetadata(x, y, z)).shouldSelectionRayTrace())
			return super.collisionRayTrace(world, x, y, z, ray, ray2);
		return null;
	}

    @Override
    public boolean canHarvestBlock(EntityPlayer player, int meta) {
        return super.canHarvestBlock(player, meta);
    }

    @Override
    public String getHarvestTool(int metadata) {
        return "pickaxe";
    }

    @Override
    public int getHarvestLevel(int metadata) {
        return getOre(metadata).harvestLevelRequired();
    }
}

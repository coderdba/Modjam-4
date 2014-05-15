package mod.badores.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import mod.badores.BadOres;
import mod.badores.ore.BadOre;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.List;

/**
 * @author diesieben07
 */
public class ItemBOIngot extends BOItem {

	public static final String NBT_KEY = "badores.ingotOreID";

	public ItemBOIngot() {
		setHasSubtypes(true);
		setCreativeTab(BadOres.creativeTab);
	}

	public static BadOre getOre(ItemStack stack) {
		if (stack.stackTagCompound != null) {
			return BadOres.oreManager.getOreByName(stack.stackTagCompound.getString(NBT_KEY));
		}
		return null;
	}

	public static void setOre(ItemStack stack, BadOre ore) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		stack.stackTagCompound.setString(NBT_KEY, ore.getName());
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (BadOre ore : BadOres.oreManager.getAllOres()) {
			if (ore.hasIngot()) {
				ItemStack stack = new ItemStack(this);
				setOre(stack, ore);
				//noinspection unchecked
				list.add(stack);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		BadOre ore = getOre(stack);
		if (ore == null) {
			return "Unkown ore!";
		}
		return I18n.format("badores.ingot", StatCollector.translateToLocal("badores." + ore.getName()));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		BadOre ore = getOre(stack);
		return "badores.ingot." + (ore == null ? "unkown" : ore.getName());
	}
}
package mod.badores.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.badores.ore.BadOre;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

/**
 * @author diesieben07
 */
public class ItemBOPickaxe extends ItemPickaxe {

	private final BadOre ore;

	public ItemBOPickaxe(ToolMaterial material, BadOre ore) {
		super(material);
		this.ore = ore;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.format("badores.pickaxe", StatCollector.translateToLocal("badores." + ore.getName()));
	}

}

package brexxit.enchant;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class CustomEnchant {

	public EnchantType type;
	
	public CustomEnchant(EnchantType type) {
		this.type = type;
		EnchantManager.temp.put(this.type.getIndex(), this);
	}
	
	public void addEnchantmentTo(ItemStack stack, int level) {
		ItemMeta meta = stack.getItemMeta();
		List<String> lore = meta.getLore();		
		if (lore == null) lore = new ArrayList<String>();
		
		String enchantStr = ChatColor.GRAY + "" + ChatColor.MAGIC + "$" + ChatColor.RESET +
							" " + ChatColor.GRAY + this.type.getDisplayName() + " " + level + " " +
							ChatColor.MAGIC + "$" + ChatColor.RESET + ChatColor.GRAY + " #" + this.type.getIndex();
		lore.add(enchantStr);
		
		meta.setLore(lore);	
		stack.setItemMeta(meta);
	}
	
	public void onRightClick(Player player) {
		
	}
	
	public void onBlockBreak(Player player, ItemStack stack, Block block, BlockBreakEvent event) {
		
	}
	
	public void onLeftClick() {
		
	}
	
	public void onHurt() {
		
	}
	
}

package brexxit.enchant;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantManager {

	public static CustomEnchant[] enchants;
	public static HashMap<Integer, CustomEnchant> temp;
	
	public static void load() {
		temp = new HashMap<Integer, CustomEnchant>();
		
		
		
		new CustomEnchant(EnchantType.Smelting) {
			@Override
			public void onBlockBreak(Player player, ItemStack stack, Block block, BlockBreakEvent event) {
				event.setDropItems(false);
				for (ItemStack drop : event.getBlock().getDrops(stack)) {
					Material material = getSmeltedEquivalent(drop.getType());
					Location loc = block.getLocation();
					
					event.getBlock().getWorld().dropItem(new Location(loc.getWorld(), loc.getBlockX() + 0.5, loc.getBlockY() + 0.5, loc.getBlockZ() + 0.5), new ItemStack(material, drop.getAmount()));
				}
			}
		};
		
		new CustomEnchant(EnchantType.Vacuum) {
			@Override
			public void onBlockBreak(Player player, ItemStack stack, Block block, BlockBreakEvent event) {
				event.setDropItems(false);
				for (ItemStack drop : event.getBlock().getDrops(stack)) {
					player.getInventory().addItem(drop);
				}
			}
		};
		
		enchants = new CustomEnchant[temp.size()];
		for (Entry<Integer, CustomEnchant> entry : temp.entrySet()) {
			enchants[entry.getKey()] = entry.getValue();
		}
	} 
	
	public static boolean enchantItem(Player player, String[] args) {
		if (player.isOp()) {
			if (args.length < 2) {
				player.sendMessage(ChatColor.GOLD + "A command to enchant the item in the main hand of a player from an enchant ID.");
				return true;
			} else {
				try {
					
					ItemStack stack = player.getInventory().getItemInMainHand();
					if (stack == null) {
						player.sendMessage(ChatColor.RED + "You are not holding an item! Hold an item to enchant!");
						return true;
					}
					
					int id = Integer.parseInt(args[0]);
					int level = Integer.parseInt(args[1]);
					CustomEnchant enchant = enchants[id];
					enchant.addEnchantmentTo(stack, level);
					player.sendMessage(ChatColor.GOLD + "Item has been given " + enchant.type.getDisplayName() + " " + level + ".");
				} catch (Exception e) {
					player.sendMessage(ChatColor.RED + args[0] + " is not a valid ID.");
				}
				return true;
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must be opped to run this command!");
			return true;
		}
	}
	
	public static void processBlockBreakEvent(Player player, ItemStack stack, Block block, BlockBreakEvent event) {
		List<String> lore = stack.getItemMeta().getLore();
		
		for (String line : lore) {
			if (line.startsWith(ChatColor.GRAY + "" + ChatColor.MAGIC)) {
				try {
					int id = Integer.parseInt(line.split(" #")[1]);
					enchants[id].onBlockBreak(player, stack, block, event);
				} catch (Exception e) {
					
				}
			}
		}
	}
	
	public static Material getSmeltedEquivalent(Material material) {
		switch (material) {
			case RAW_IRON:
				return Material.IRON_INGOT;
			case RAW_GOLD:
				return Material.GOLD_INGOT;
			case RAW_COPPER:
				return Material.COPPER_INGOT;
			default:
				return material;
		}
	}
	
}

package brexxit.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;

public class ItemManager {

	private static JavaPlugin plugin;
	
	private static CustomItem[] customItems;
	
	public static void load(JavaPlugin myPlugin) {
		plugin = myPlugin;
		customItems = new CustomItem[11];

		customItems[0] = createHammerItem();
		customItems[1] = createHealingWand();
		customItems[2] = createBeatingStick();
		customItems[3] = createSpice();
		customItems[4] = createFeather();
		customItems[5] = createCompressedSpice();
		customItems[6] = createEnchGoldBlocks();
		customItems[7] = createEnchantedCompressedSpice();
		customItems[8] = createRadiant();
		customItems[9] = createChargedRadiant();
		customItems[10] = createRadiantSword();
	}
	
	// INGREDIENTS 
	
	public static CustomItem createRadiantSword() {
		NamespacedKey key = new NamespacedKey(plugin, "Radiant_Sword");
		ItemStack stack = createNewItemStack(Material.GOLDEN_SWORD, 1, "Radiant Sword");
		
		CustomItem item = new CustomItem(key, stack, Rarity.Divine, 10);
		item.addEnchant(Enchantment.DURABILITY, 5, "Unbreaking");
		item.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("generic.attackDamage", 16, Operation.ADD_NUMBER), "Attack Damage");
		item.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier("generic.movementSpeed", 0.02, Operation.ADD_NUMBER), "Movement Speed");
		item.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier("generic.armorToughness", 1, Operation.ADD_NUMBER), "Armor Toughness");
		
		ShapedRecipe recipe = new ShapedRecipe(key, stack);
		recipe.shape("ggr", 
					 "grg", 
					 "fgg");
		recipe.setIngredient('f', new RecipeChoice.ExactChoice( customItems[4].createNew()));
		recipe.setIngredient('g', new RecipeChoice.ExactChoice( customItems[6].createNew()));
		recipe.setIngredient('r', new RecipeChoice.ExactChoice( customItems[9].createNew()));
		Bukkit.addRecipe(recipe);
		
		return item;
	}
	
	public static CustomItem createChargedRadiant() {
		NamespacedKey key = new NamespacedKey(plugin, "Charged_Radiant");
		ItemStack stack = createNewItemStack(Material.NETHER_STAR, 1, "Charged Radiant");
		
		CustomItem item = new CustomItem(key, stack, Rarity.Divine, 9);
		item.addEnchLook();
		
		return item;
	}
	
	public static CustomItem createRadiant() {
		NamespacedKey key = new NamespacedKey(plugin, "Radiant_Crystal");
		ItemStack stack = createNewItemStack(Material.AMETHYST_CLUSTER, 1, "Radiant Crystal");
		
		CustomItem item = new CustomItem(key, stack, Rarity.Divine, 8) {
			@Override
			public void onRightClick(Player player, Action action, ItemStack stack) {				
				int levelsToAdd = player.getLevel();
				if (stack.getAmount() > 1) {
					player.sendMessage(ChatColor.RED + "You do not have the energy to imbue more than one crystal!");
					return;
				}
				
				if (player.getLevel() < 5) {
					player.sendMessage(ChatColor.RED + "You do not have the levels to add to this crystal!");
				}
				levelsToAdd = 5;
				player.setLevel(player.getLevel() - levelsToAdd);
				
				ItemMeta meta = stack.getItemMeta();
				List<String> lore = meta.getLore();
				if (lore.size() < 3) {
					lore.add(ChatColor.GRAY + "Levels: " + levelsToAdd);
				} else {
					int levelsInItemStack = Integer.parseInt(lore.get(2).split(" ")[1]);
					
					if (levelsInItemStack >= 45) {
						removeOneOfItemStack(stack, player);
						player.getInventory().addItem(customItems[9].createNew());
						return;
					}
					
					lore.set(2, ChatColor.GRAY + "Levels: " + (levelsToAdd + levelsInItemStack));
				}
				meta.setLore(lore);
				stack.setItemMeta(meta);	
			}
			@Override
			public void onLeftClick(Player player, Action action, ItemStack stack) {
				if (stack.getAmount() > 1) {
					player.sendMessage(ChatColor.RED + "You do not have the energy to imbue more than one crystal!");
					return;
				}
				
				ItemMeta meta = stack.getItemMeta();
				List<String> lore = meta.getLore();
				if (lore.size() < 3) {
					player.sendMessage(ChatColor.RED + "There are no levels embued to this crystal!");
					return;
				} else {
					int levelsInItemStack = Integer.parseInt(lore.get(2).split(" ")[1]);
					if (levelsInItemStack < 5) {
						player.sendMessage(ChatColor.RED + "There are not enough levels to take from this crystal!");
						return;
					}
					player.setLevel(player.getLevel() + 5);
					lore.set(2, ChatColor.GRAY + "Levels: " + (levelsInItemStack - 5));
					meta.setLore(lore);
					stack.setItemMeta(meta);	
				}
			}
		};
		item.addEnchLook();
		
		FurnaceRecipe recipe = new FurnaceRecipe(new NamespacedKey(plugin, "Furnace_Radiant_Crystal"), item.createNew(), new RecipeChoice.ExactChoice(customItems[7].createNew()), 5, 20000);
		Bukkit.addRecipe(recipe);	
		
		return item;	
	}
	
	public static CustomItem createEnchantedCompressedSpice() {
		NamespacedKey key = new NamespacedKey(plugin, "Enchanted_Compressed_Spice");
		ItemStack stack = createNewItemStack(Material.LARGE_AMETHYST_BUD, 1, "Enchanted Compressed Spice");
		
		CustomItem item = new CustomItem(key, stack, Rarity.Legendary, 7);
		item.addEnchLook();
		
		return item;
	}
	
	
	public static CustomItem createEnchGoldBlocks() {
		NamespacedKey key = new NamespacedKey(plugin, "Enchanted_Gold_Blocks");
		ItemStack stack = createNewItemStack(Material.GOLD_BLOCK, 1, "Enchanted Gold Blocks");
		
		CustomItem item = new CustomItem(key, stack, Rarity.Epic, 6);
		item.addEnchLook();
		
		return item;
	}
	
	public static CustomItem createCompressedSpice() {
		NamespacedKey key = new NamespacedKey(plugin, "Compressed_Spice");
		ItemStack stack = createNewItemStack(Material.MEDIUM_AMETHYST_BUD, 1, "Compressed Spice");
		
		CustomItem item = new CustomItem(key, stack, Rarity.Epic, 5) {
			@Override
			public void onRightClick(Player player, Action action, ItemStack stack) {
				if (player.getLevel() > 10) {
					player.setLevel(player.getLevel() - 10);
					removeOneOfItemStack(stack, player);
					player.getInventory().addItem(customItems[7].createNew());
				} else {
					player.sendMessage(ChatColor.RED + "You do not have the levels to imbue the spice.");
				}
			}
		};
		item.addEnchLook();
		
		ShapedRecipe recipe = new ShapedRecipe(key, stack);
		recipe.shape("111", 
					 "111", 
					 "111");
		recipe.setIngredient('1', new RecipeChoice.ExactChoice( customItems[3].createNew()));
		Bukkit.addRecipe(recipe);
		
		return item;
	}
	 
	public static CustomItem createFeather() {
		NamespacedKey key = new NamespacedKey(plugin, "Feather_of_the_Divine");
		ItemStack stack = createNewItemStack(Material.FEATHER, 1, "Feather of the Divine");
		
		CustomItem item = new CustomItem(key, stack, Rarity.Divine, 4);
		item.addEnchLook();
		
//		ShapedRecipe recipe = new ShapedRecipe(key, stack);
//		recipe.shape(" NW", 
//					 " SN", 
//					 "S  ");
//		recipe.setIngredient('S', Material.STICK);
//		recipe.setIngredient('N', Material.NETHERITE_INGOT);
//		recipe.setIngredient('W', Material.NETHER_STAR);
//		Bukkit.addRecipe(recipe);
		
		return item;
	}
	
	public static CustomItem createSpice() {
		NamespacedKey spiceStickKey = new NamespacedKey(plugin, "Spice");
		ItemStack stack = createNewItemStack(Material.SMALL_AMETHYST_BUD, 1, "Spice");
		
		CustomItem spice = new CustomItem(spiceStickKey, stack, Rarity.Uncommon, 3);
		spice.addEnchLook();
		
		return spice;
	}
	
	// BEATING STICK
	
	public static CustomItem createBeatingStick() {
		NamespacedKey beatingStickKey = new NamespacedKey(plugin, "Beating_Stick");
		ItemStack stack = createNewItemStack(Material.STICK, 1, "Beating Stick");
		
		CustomItem beatingStick = new CustomItem(beatingStickKey, stack, Rarity.Divine, 2);
		beatingStick.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, 
					new AttributeModifier("generic.attackDamage", 1000000, Operation.ADD_NUMBER), 
					"Attack Damage");
		
		
		return beatingStick;
	}
	
	
	public static CustomItem createHealingWand() {
		NamespacedKey healingWandKey = new NamespacedKey(plugin, "Healing_Wand");
		ItemStack stack = createNewItemStack(Material.BLAZE_ROD, 1, "Healing Wand");
		
		CustomItem healingWand = new CustomItem(healingWandKey, stack, Rarity.Epic, 1) {
			@Override
			public void onRightClick(Player player, Action action, ItemStack stack) {
				
				if (!doesPlayerHave(Material.GOLD_INGOT, player)) {
					player.sendMessage(ChatColor.RED + "You do not have enough gold to use the wand!");
					return;
				}
				removeOneMaterial(Material.GOLD_INGOT, player);
				
				double newHealth = player.getHealth() + 5;
				if (newHealth > 20) newHealth = 20;
				player.setHealth(newHealth);
			}
		};
		
		ShapedRecipe recipe = new ShapedRecipe(healingWandKey, stack);
		recipe.shape("  W", 
					 " S ", 
					 "S  ");
		recipe.setIngredient('S', Material.BLAZE_ROD);
		recipe.setIngredient('W', Material.NETHER_STAR);
		Bukkit.addRecipe(recipe);
		
		return healingWand;
	}
	
	// HAMMER
	
	public static CustomItem createHammerItem() {
		NamespacedKey hammerKey = new NamespacedKey(plugin, "Hammer");
		ItemStack stack = createNewItemStack(Material.NETHERITE_INGOT, 1, "Hammer");
		
		CustomItem hammer = new CustomItem(hammerKey, stack, Rarity.Epic, 0) {
			@Override
			public void onBlockBreak(Player player, ItemStack item, Block block) {
				if (!doesPlayerHave(Material.GOLD_INGOT, player)) {
					player.sendMessage(ChatColor.RED + "You do not have enough gold to use the hammer!");
					return;
				}
				removeOneMaterial(Material.GOLD_INGOT, player);
				
				Location loc = block.getLocation();
				player.getWorld().createExplosion(new Location(loc.getWorld(), loc.getBlockX() + 0.5, loc.getBlockY() + 0.5, loc.getBlockZ() + 0.5), 1f, false, false, player);				
				
				for (int x = -1; x <= 1; x++) {
					for (int z = -1; z <= 1; z++) {
						for (int y = -1; y <= 1; y++) {
							Block blockAt = player.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
							if (blockAt.getType() != Material.BEDROCK) blockAt.breakNaturally();
						}
					}					
				}
			}
		};
		hammer.addEnchant(Enchantment.ARROW_FIRE, 1, "Flame");
		
		ShapedRecipe recipe = new ShapedRecipe(hammerKey, stack);
		recipe.shape(" NW", 
					 " SN", 
					 "S  ");
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('N', Material.NETHERITE_INGOT);
		recipe.setIngredient('W', Material.NETHER_STAR);
		Bukkit.addRecipe(recipe);
		
		return hammer;
	}
	
	// HEALING WAND
	
	
	public static void processBlockBreakEvent(Player player, ItemStack stack, Block block) {
		List<String> lore = stack.getItemMeta().getLore();
		if (lore.size() > 0) {
			String idStr = ChatColor.stripColor(lore.get(0));
			if (idStr.startsWith("ID: ")) {
				int id = Integer.parseInt(idStr.split(" ")[1]);
				
				customItems[id].onBlockBreak(player, stack, block);
			}
		}
	}
	
	public static void processInteractEvent(Player player, Action action, ItemStack stack) {
		List<String> lore = stack.getItemMeta().getLore();
		if (lore.size() > 0) {
			String idStr = ChatColor.stripColor(lore.get(0));
			if (idStr.startsWith("ID: ")) {
				int id = Integer.parseInt(idStr.split(" ")[1]);
				
				if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
					customItems[id].onLeftClick(player, action, stack);
				} else {
					customItems[id].onRightClick(player, action, stack);
				}
			}
		}
		
	}
	
	public static void processBlockPlaceEvent(BlockPlaceEvent event) {
		ItemStack item = event.getItemInHand();
		
		List<String> lore = item.getItemMeta().getLore();
		if (lore.size() > 0) {
			String idStr = ChatColor.stripColor(lore.get(0));
			if (idStr.startsWith("ID: ")) {
				int id = Integer.parseInt(idStr.split(" ")[1]);
				if (id >= 0 && id < customItems.length) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	public static void processGoldBlockRightClickEvent(Player player, Action action, ItemStack stack) {
		if (stack.getType() == Material.GOLD_BLOCK && (Action.RIGHT_CLICK_AIR == action) && (!stack.hasItemMeta() || !stack.getItemMeta().hasLore())) {
			if (player.getLevel() >= 30) {
				player.setLevel(player.getLevel() - 30);
				
				if (stack.getAmount() > 1) {
					stack.setAmount(stack.getAmount()-1);
				} else {
					player.getInventory().remove(stack);
				}
				player.getInventory().addItem(customItems[6].createNew());
			} else {
				player.sendMessage(ChatColor.RED + "You do not have the levels to convert this golden block.");
				return;
			}
		}
	}
	
	public static ItemStack createNewItemStack(Material material, int count, String name) {
		ItemStack newItemStack = new ItemStack(material, count);
		
		ItemMeta meta = newItemStack.getItemMeta();
		
		meta.setDisplayName(name);
		
		newItemStack.setItemMeta(meta);
		
		return newItemStack;
	}
	
	public static boolean giveItem(Player player, String[] args) {
		if (player.isOp()) {
			if (args.length < 1) {
				player.sendMessage(ChatColor.GOLD + "A command to give an OP player a custom item from an ID.");
				return true;
			} else {
				try {
					int id = Integer.parseInt(args[0]);
					CustomItem item = customItems[id];
					
					player.getInventory().addItem(item.createNew());
					player.sendMessage(ChatColor.GOLD + "You have been given " + item.getID() + ".");
					return true;
				} catch (Exception e) {
					player.sendMessage(ChatColor.RED + args[0] + " is not a valid ID.");
					return true;
				}
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must be opped to run this command!");
			return true;
		}
	}
	
	public static boolean doesPlayerHave(Material material, Player player) {
		return player.getInventory().contains(material);
	}
	
	public static void removeOneOfItemStack(ItemStack stackToRemove, Player player) {
		if (stackToRemove.getAmount() > 1) {
			stackToRemove.setAmount(stackToRemove.getAmount());
		} else {
			player.getInventory().remove(stackToRemove);
		}
	}
	
	public static void removeOneMaterial(Material material, Player player) {	
		for (ItemStack stack : player.getInventory().getContents()) {
			if (stack != null && stack.getType() == material) {
				int count = stack.getAmount();
				if (count == 1) {
					player.getInventory().remove(stack);
				} else {
					stack.setAmount(stack.getAmount() - 1);
				}
				break;
			}
		}
		
	}
	
	public static void setLoreAtIndex(String str, int i, ItemStack itemStack) {
		ItemMeta meta = itemStack.getItemMeta();
		List<String> lore = meta.getLore();
		if (lore == null) lore = new ArrayList<String>();
		lore.set(i, str);
		meta.setLore(lore);
		itemStack.setItemMeta(meta);
	}
	
}

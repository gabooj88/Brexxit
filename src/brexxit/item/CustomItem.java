package brexxit.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.block.Action;

public class CustomItem {

	public NamespacedKey key;
	public ItemStack itemStack;
	public Rarity rarity;
	public int id;
	
	public CustomItem(NamespacedKey key, ItemStack itemStack, Rarity rarity, int id) {
		this.key = key;
		this.itemStack = itemStack;
		this.rarity = rarity;
		this.id = id;
		
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName(rarity.toDisplayableString() + meta.getDisplayName());
		itemStack.setItemMeta(meta);
		addToLore(ChatColor.GRAY + "ID: " + id);
		addToLore(ChatColor.GRAY + rarity.toString());
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void addEnchLook() {
		ItemMeta meta = itemStack.getItemMeta();
		meta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemStack.setItemMeta(meta);
	}
	
	public void addEnchant(Enchantment enchant, int level, String nameOfEnchant) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.addEnchant(enchant, level, true);
		itemStack.setItemMeta(meta);
		addToLore(ChatColor.GRAY + nameOfEnchant + " " + level);
	}
	
	public void setFlag(ItemFlag flag) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.addItemFlags(flag);
		itemStack.setItemMeta(meta);
	}
	
//	public void setCanNotBePlaced() {
//		net.minecraft.world.item.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
//
//		NBTTagList tags = (NBTTagList) stack.getTag().get("CanPlaceOn");
//
//		if (tags == null)
//		    tags = new NBTTagList();
//		
//		tags.add(NBTTagString.a("minecraft:emerald_block"));
//
//		stack.getTag().set("CanPlaceOn", tags);
//
//		itemStack = CraftItemStack.asCraftMirror(stack);
//		setFlag(ItemFlag.HIDE_PLACED_ON);
//	}
	
	public ItemStack createNew() {
		ItemStack newItemStack = new ItemStack(itemStack.getType(), itemStack.getAmount());
		newItemStack.setItemMeta(itemStack.getItemMeta());
		
		return newItemStack;
	}
	
	public NamespacedKey getNamespacedKey() {
		return key;
	}
	
	public void addAttributeModifier(Attribute attribute, AttributeModifier modifier, String nameOfModifier) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.addAttributeModifier(attribute, modifier);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemStack.setItemMeta(meta);
		
		addToLore(ChatColor.GRAY + "Adds " + modifier.getAmount() + " " + nameOfModifier);
	}
	
	public void onRightClick(Player player, Action action, ItemStack stack) {
		
	}
	
	public void onLeftClick(Player player, Action action, ItemStack stack) {
		
	}
	
	public void onBlockBreak(Player player, ItemStack item, Block block) {
		
	}
	
	public void addToLore(String str) {
		ItemMeta meta = itemStack.getItemMeta();
		List<String> lore = meta.getLore();
		if (lore == null) lore = new ArrayList<String>();
		lore.add(str);
		meta.setLore(lore);
		itemStack.setItemMeta(meta);
	}
	
}

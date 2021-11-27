package brexxit.main;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import brexxit.enchant.EnchantManager;
import brexxit.entity.EntityManager;
import brexxit.item.ItemManager;

public class Manager extends JavaPlugin implements Listener {

	public static Server server;
	
	@Override
	public void onEnable() {
		server = getServer();
		server.getPluginManager().registerEvents(this, this);
		server.getConsoleSender().sendMessage("Brexxit is enabled.");
		
		EnchantManager.load();
		ItemManager.load(this);
		EntityManager.load();
	}
	
	@Override
	public void onDisable() {
		server.getConsoleSender().sendMessage("Brexxit is disabled.");
	}
	
	//
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getItem() != null ) {
			if (event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasLore()) {
				ItemManager.processInteractEvent(event.getPlayer(), event.getAction(), event.getItem());
			} else {
				ItemManager.processGoldBlockRightClickEvent(event.getPlayer(), event.getAction(), event.getItem());
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
		if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
			ItemManager.processBlockBreakEvent(event.getPlayer(), item, event.getBlock());
			EnchantManager.processBlockBreakEvent(event.getPlayer(), item, event.getBlock(), event);
		}
	}
	
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {		
		if (event.getItemInHand() != null && event.getItemInHand().hasItemMeta() && event.getItemInHand().getItemMeta().hasLore()) {
			ItemManager.processBlockPlaceEvent(event);
		}
	}
	
	//
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to run this command!");
			return false;
		}
		Player player = (Player) sender;
		
		if (label.equalsIgnoreCase("customitem")) {
			return ItemManager.giveItem(player, args);
		} else if (label.equalsIgnoreCase("enchantitem")) {
			return EnchantManager.enchantItem(player, args);
		} else if (label.equalsIgnoreCase("customsummon")) {
			return EntityManager.customSummon(player, args);
		}
		return super.onCommand(sender, command, label, args);
	}
	
}
package brexxit.entity;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EntityManager {

	public static HashMap<EntityType, CustomEntity[]> entities = new HashMap<EntityType, CustomEntity[]>();
	
	public static void load() {
		// Zombie
		CustomEntity[] zombies = new CustomEntity[1];
		zombies[0] = new CustomEntity(0) {
			@Override
			public void createNewEntity(Location loc) {				
				this.createEntity(EntityType.ZOMBIE, loc);
				this.setHealth(50);
				this.setSpeed(0.2f);
				this.setDamage(8);
			}
		};
		entities.put(EntityType.ZOMBIE, zombies);
	}
	
	public static boolean customSummon(Player player, String[] args) {
		if (player.isOp()) {
			if (args.length < 2) {
				player.sendMessage(ChatColor.GOLD + "A command to spawn a custom mob at an operator.");
				return true;
			} else {
				try {
					EntityType entityType = getEntityByName(args[0]);
					int id = Integer.parseInt(args[1]);
					player.sendMessage(entityType.toString());
					entities.get(entityType)[id].createNewEntity(player.getLocation());
					player.sendMessage(ChatColor.GOLD + "Successfully spawned the custom mob to your location!");
					return true;
				} catch (Exception e) {
					player.sendMessage(ChatColor.RED + "That command does not work! Use <Entity Name> then <ID>");
					e.printStackTrace();
					return true;
				}
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must be opped to run this command!");
			return true;
		}
	}
	
	public static EntityType getEntityByName(String name) {
        for (EntityType type : EntityType.values()) {
            if(type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
	
}

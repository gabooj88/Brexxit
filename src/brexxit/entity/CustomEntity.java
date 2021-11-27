package brexxit.entity;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class CustomEntity {
	
	public LivingEntity entity;
	private int id;
	
	public CustomEntity(int id) {
		this.id = id;
	}	
	
	public LivingEntity getEntity() {
		return entity;
	}

	public int getId() {
		return id;
	}

	public void createNewEntity(Location loc) {
		
	}
	
	public void createEntity(EntityType type, Location loc) {
		this.entity = (LivingEntity) loc.getWorld().spawnEntity(loc, type);
	}
	
	public void setHealth(int health) {
		AttributeInstance attribute = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		attribute.setBaseValue(health);
		this.entity.setHealth(health);
	}
	
	public void setSpeed(float speed) {
		AttributeInstance attribute = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
		attribute.setBaseValue(speed);
	}
	
	public void setDamage(float damage) {
		AttributeInstance attribute = entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
		attribute.setBaseValue(damage);
	}
	
	public void addPotionEffect(PotionEffect effect) {
		this.entity.addPotionEffect(effect);
	}
	
	public void setEquipment(ItemStack helmet,     float helmetDropChance,
							 ItemStack chestplate, float chestplateDropChance,
							 ItemStack leggings,   float leggingsDropChance,
							 ItemStack boots,      float bootsDropChance) {
		
		EntityEquipment equipment = this.entity.getEquipment();
		equipment.setHelmet(helmet);
	    equipment.setHelmetDropChance(helmetDropChance);

		equipment.setChestplate(chestplate);
		equipment.setChestplateDropChance(chestplateDropChance);

		equipment.setLeggings(leggings);
		equipment.setLeggingsDropChance(leggingsDropChance);

		equipment.setBoots(boots);
		equipment.setBootsDropChance(bootsDropChance);
	}
	
	public void setHand(ItemStack stack, float chance) {
		this.entity.getEquipment().setItemInMainHand(stack);
		this.entity.getEquipment().setItemInMainHandDropChance(chance);
	}
	
}

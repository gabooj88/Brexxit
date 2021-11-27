package brexxit.item;

import org.bukkit.ChatColor;

public enum Rarity {
	
	Common(ChatColor.WHITE, null),
	Uncommon(ChatColor.GRAY, null),
	Rare(ChatColor.BLUE, null),
	Epic(ChatColor.DARK_AQUA, ChatColor.BOLD),
	Legendary(ChatColor.RED, ChatColor.BOLD),
	Divine(ChatColor.GOLD, ChatColor.BOLD);
	
	private final ChatColor color;
	private final ChatColor modifier;
	
	Rarity(ChatColor color, ChatColor modifier) {
		this.color = color;
		this.modifier = modifier;
	}
	
	public String toDisplayableString() {
		return color + "" + (modifier == null ? "" : modifier);
	}
	
}

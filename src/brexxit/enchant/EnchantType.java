package brexxit.enchant;

public enum EnchantType {

	Smelting(0, "Smelting", 1), Vacuum(1, "Vacuum", 1);
	
	private final int index;
	private final String displayName;
	private final int maxLevel;
	
	EnchantType(int index, String displayName, int maxLevel) {
		this.index = index;
		this.displayName = displayName;
		this.maxLevel = maxLevel;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}

	public int getIndex() {
		return index;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
}

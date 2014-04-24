package me.HugoDaBosss.skyrim.shouts;

import org.bukkit.ChatColor;

public enum ShoutType {
	
	BLOCKED(ChatColor.RED + "",0),
	UNRELETINGFORCE("Unreleting Force", 25), //everyone in a 10x10 area gets blown away //TODO
	WHIRLWINDSPRINT("Whirlwind Sprint", 10), //speed 2 for 10 sec
	WHIRLWINDJUMP("Whirlwind Jump", 10), //you jump 8 blocks far
	RAGEBLAST("Rage Blast", 20), //everyone in a 4x4 gets blown up in the air and 6 damage
	FIREBLAST("Fire Blast", 30), //same as BLAST + fire
	TOXINBLOW("Toxin Blow", 15), //everyone in a 10x10 area gets poison (8 sec)
	PROJECTILEBLAST("Projectile Blast", 30), //shoots 5 arrows in the direction you are looking
	FIREBREATH("Fire Breath", 15), //everyone in a 6x6 area gets on fire
	INHALEBOOST("Inhale Boost", 10), //everyone in a 10x10 area gets shooted towards you
	DRAGONMOVE("Dragon Move", 15), //You get moved to a random location close to you
	DRAGONESCAPE("Dragon Escape", 20), //You get moved to a random location close to you, everyone else will be moved towards your old place
	NONE("No Shout",0),
	RELOADING("%",0);
	
	private String shout;
	private double mana;
	
	private ShoutType(String shout, double mana)
	{
		this.shout = shout;
		this.mana = mana;
	}
	
	public String getName()
	{
		return super.toString().toLowerCase();
	}
	
	public String getFullName()
	{
		if(this == ShoutType.NONE)
		{
			return ChatColor.GRAY + shout;
		}
		return shout;
	}
	
	public String getFullCode()
	{
		return shout;
	}
	
	
	//TODO FIX VALUES
	public double getMana()
	{
		return mana;
	}
	
	public Shout getShout()
	{
		try {
			return (Shout) Class.forName("me.HugoDaBosss.skyrim.shouts." + shout.replaceAll(" ", "")).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
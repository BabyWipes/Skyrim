package me.HugoDaBosss.skyrim;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import me.HugoDaBosss.skyrim.Users.Rank;
import me.HugoDaBosss.skyrim.shouts.ShoutType;
import me.HugoDaBosss.skyrim.util.Items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.google.common.io.Files;

public class User 
{
	private boolean newuser;
	private FileConfiguration config;
	private String name;
	private ShoutType shout1, shout2, shout3;
	private ArrayList<ShoutType> shouts; 
	private File f;
	private int money;
	private Rank rank;
	private double mana, manaregen,manamax, manamulti;
	private String uuid;
	
	public User(String uuid, String name)
	{
		this.uuid = uuid;
		this.name = name;
		shouts = new ArrayList<ShoutType>();
		f = new File(Skyrim.getPlugin().getDataFolder().getPath() + "\\players\\", uuid + ".yml");
		if(!f.exists())
		{
			newuser = true;
			File o = new File(Skyrim.getPlugin().getDataFolder().getPath() + "\\players\\", name + ".yml");
			if(o.exists())
			{
				Skyrim.info("omigadooo");
				try {
					Files.copy(o, f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		config = YamlConfiguration.loadConfiguration(f);
		if(newuser)
		{
			config.set("shouts.unlocked", Arrays.asList(ShoutType.UNRELETINGFORCE.getName()));
			config.set("shouts.current.1", "none");
			config.set("shouts.current.2", "none");
			config.set("shouts.current.3", "none");
			config.set("money", 250);
			config.set("rank", "user");
			config.set("mana", 50.0);
			config.set("manaregen", 1.0);
			config.set("manamax", 50.0);
		}
		config.set("name", name);
		mana = config.getDouble("mana");
		manaregen = config.getDouble("manaregen");
		manamax = config.getDouble("manamax");
		money = config.getInt("money");
		manamulti = 1.0;
		rank = Skyrim.Users.toRank(config.getString("rank"));
		final UUID id = UUID.fromString(uuid);
		new org.bukkit.scheduler.BukkitRunnable()
		{

			@Override
			public void run() {
				User u = Skyrim.Users.getUserUUID(id.toString());
				u.setActiveShout(1, Skyrim.Shouts.getShout(config.getString("shouts.current.1")));
				u.setActiveShout(2, Skyrim.Shouts.getShout(config.getString("shouts.current.2")));
				u.setActiveShout(3, Skyrim.Shouts.getShout(config.getString("shouts.current.3")));
			}
			
		}.runTaskLater(Skyrim.getPlugin(), 1L);
		for(String s : config.getStringList("shouts.unlocked"))
		{
			ShoutType st = Skyrim.Shouts.getShout(s);
			if(st != ShoutType.NONE && st != ShoutType.BLOCKED && st != ShoutType.RELOADING)
				shouts.add(st);
		}
		save();
	}
	
	public void saveConfig()
	{
		try {
			config.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void save()
	{
		Skyrim.Users.setUser(this);
	}
	
	public void saveAll()
	{
		save();
		saveConfig();
	}
	
	public boolean isNew()
	{
		return newuser;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getUUID()
	{
		return uuid;
	}
	
	public int addMoney(int i)
	{
		if(i <= 0)return money;
		money = money + i;
		config.set("money", money);
		return money;
	}
	
	public int removeMoney(int i)
	{
		if(i <= 0)return money;
		if(i > money)
			return -1;
		money = money - i;
		config.set("money", money);
		return money;
	}
	
	public int getMoney()
	{
		return money;
	}
	
	public int setMoney(int i)
	{
		if(i < 0)return money;
		money = i;
		config.set("money", money);
		return money;
	}
	
	public void setMana(double mana)
	{
		if(mana > manamax)
			mana = manamax;
		this.mana = mana;
		config.set("mana", mana);
	}
	
	public void addMana(double mana)
	{
		this.mana += mana;
		config.set("mana", this.mana);
	}
	
	public double getMana()
	{
		return mana;
	}
	
	public void setManaRegen(double manaregen)
	{
		this.manaregen = manaregen;
		config.set("manaregen", manaregen);
	}
	
	public double getManaRegen()
	{
		return manaregen;
	}
	
	public double getManaRegen(boolean multiplier)
	{
		return manaregen * manamulti;
	}
	
	public void setManaMultiplier(double multiplier)
	{
		this.manamulti = multiplier;
	}
	
	public void setMaxMana(double max)
	{
		this.manamax = max;
		config.set("manamax", max);
	}
	
	public double getMaxMana()
	{
		return manamax;
	}
	
	public void setRank(Rank r)
	{
		this.rank = r;
		config.set("rank", r.name().toLowerCase());
		save();
	}
	
	public Rank getRank()
	{
		return rank;
	}
	
	public Player toPlayer()
	{
		return Bukkit.getPlayer(UUID.fromString(uuid));
	}
	
	public ShoutType getActiveShout(int i)
	{
		if(i == 1)
			return shout1;
		else if(i == 2)
			return shout2;
		else if(i == 2)
			return shout3;
		return null;
	}
	
	public void setActiveShout(int i, ShoutType st)
	{
		if(i >= 1 && i <= 3)
		{
			if(i == 1)
				shout1 = st;
			else if(i == 2)
				shout2 = st;
			else if(i == 3)
				shout3 = st;
			Player p = Skyrim.getPlayer(name);
			p.getInventory().setItem(i + 5, Items.createItem(Material.NETHER_STAR, ChatColor.GOLD + st.getFullName(), 1));
			config.set("shouts.current." + i, st.getName());
		}		
	}
	
	public ArrayList<ShoutType> getUnlockedShouts()
	{
		return shouts;
	}
	
	public boolean unlockShout(ShoutType st)
	{
		if(!shouts.contains(st))
		{
			shouts.add(st);
			ArrayList<String> a = new ArrayList<String>();
			for(ShoutType s : shouts)
			{
				a.add(s.getName());
			}
			config.set("shouts.unlocked", a);
			this.save();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean lockShout(ShoutType st)
	{
		if(shouts.contains(st))
		{
			shouts.remove(st);
			ArrayList<String> a = new ArrayList<String>();
			for(ShoutType s : shouts)
			{
				a.add(s.getName());
			}
			config.set("shouts.unlocked", a);
			this.save();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isUnlocked(ShoutType st)
	{
		if(shouts.contains(st))
			return true;
		return false;
	}
	
	public void reloadShouts()
	{
		org.bukkit.inventory.PlayerInventory i = Skyrim.getPlayer(name).getInventory();
		i.remove(Material.NETHER_STAR);
		i.setItem(6, Items.createItem(Material.NETHER_STAR, ChatColor.GOLD + shout1.getFullName(), 1));
		i.setItem(7, Items.createItem(Material.NETHER_STAR, ChatColor.GOLD + shout2.getFullName(), 1));
		i.setItem(8, Items.createItem(Material.NETHER_STAR, ChatColor.GOLD + shout3.getFullName(), 1));
	}
}

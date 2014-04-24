package me.HugoDaBosss.skyrim;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import me.HugoDaBosss.skyrim.util.BossBar;
import me.HugoDaBosss.skyrim.util.RegionUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.bobacadodl.JSONChatLib.JSONChatColor;

public class Users
{

	private java.util.HashMap<String, User> users;
	
	public void loadUsers()
	{
		users = new java.util.HashMap<String, User>();
		for(Player p : Bukkit.getOnlinePlayers())
		{
			this.addUser(p);
		}
	}
	
	public void saveUsers()
	{
		for(User u : this.getOnlineUsers())
		{
			u.saveConfig();
		}
	}
	
	public Users(){}
	
	public ArrayList<User> getOnlineUsers()
	{
		ArrayList<User> u = new ArrayList<User>();
		for(Map.Entry<String, User> a : users.entrySet())
		{
			u.add(a.getValue());
		}
	    return u;
	}
	
	@Deprecated
	public User getUser(String name)
	{
		for(Map.Entry<String, User> u : users.entrySet())
		{
			if(u.getValue().getName().equalsIgnoreCase(name))
				return u.getValue();
		}
		return null;
	}
	
	public Player getPlayer(String name)
	{
		for(Map.Entry<String, User> u : users.entrySet())
		{
			if(u.getValue().getName().equalsIgnoreCase(name))
			{
				return Bukkit.getPlayer(UUID.fromString(u.getKey()));
			}
		}
		return null;
	}
	
	public User getUserUUID(String uuid)
	{
		return users.get(uuid);
	}
	
	public User getUserPlayer(Player p)
	{
		return users.get(p.getUniqueId().toString());
	}
	
	public User getUserSender(org.bukkit.command.CommandSender sender)
	{
		if(!(sender instanceof Player))
			return null;
		return users.get(((Player)sender).getUniqueId().toString());
	}
	
	public void addUser(final Player player)
	{
		final String uuid, name;
		uuid = player.getUniqueId().toString();
		name = player.getName();
		users.put(uuid, new User(uuid, name));
		new org.bukkit.scheduler.BukkitRunnable()
		{
			UUID id = null;
			int outside = 0;
			@Override
			public void run() {
				if(id == null)
				{
					id = UUID.fromString(uuid);
				}
				User u = Skyrim.Users.getUserUUID(uuid);
				Player p = Bukkit.getPlayer(UUID.fromString(uuid));
				if(p == null || !p.isOnline())
				{
					this.cancel();
					return;
				}
				Block b = p.getLocation().getBlock();
				if(b.getType() == Material.STATIONARY_WATER || b.getType() == Material.WATER)
				{
					if(p.isDead())return;
					if(p.getGameMode() == org.bukkit.GameMode.CREATIVE)return;
					double health = p.getHealth();
					p.damage(1.0);
					if(health >= 1)
					p.setHealth(health - 1);
				}
				if(b.getRelative(BlockFace.DOWN).getType() == Material.GRAVEL || b.getRelative(BlockFace.DOWN,2).getType() == Material.GRAVEL)
				{
					if(p.getWalkSpeed() != 0.25)
						p.setWalkSpeed((float) 0.25);
				}
				else
				{
					if(p.getWalkSpeed() != 0.2)
						p.setWalkSpeed((float) 0.2);
				}
				if(RegionUtils.isOutsideMap(p.getLocation()) && p.getGameMode() != GameMode.CREATIVE && !p.isDead())
				{
					outside++;
					if(outside == 1)
					{
						p.sendMessage(ChatColor.RED + "You are out of the map! Please return");
					}
					else if(outside == 5)
					{
						p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You are out of the map! Please return");
					}
					else if(outside > 9)
					{
						p.playSound(p.getLocation(), Sound.GHAST_SCREAM, outside /2, 1);
						p.setFireTicks(10);
						double health = p.getHealth();
						p.damage(2.0);
						if(health >= 2)
						p.setHealth(health - 2);
					}
				}
				else
				{
					outside = 0;
				}
				if(p.getItemInHand().getType() == Material.FERMENTED_SPIDER_EYE)
				{
					u.setManaMultiplier(2.0);
				}
				else
				{
					u.setManaMultiplier(1.0);
				}
				u.setMana(u.getMana() + u.getManaRegen(true) / 2);
				BossBar.displayTextBar(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + (int) u.getMana() + " mana" , p, (int)(u.getMana() / u.getMaxMana() * 100));
				}
			
		}.runTaskTimer(Skyrim.getPlugin(), 10L, 10L);
	}
	
	@Deprecated
	public void removeUser(String uuid)
	{
		users.remove(uuid);
	}
	
	public void removeUserUUID(String uuid)
	{
		users.remove(uuid);
	}
	
	public void removeUserPlayer(Player p)
	{
		users.remove(p.getUniqueId().toString());
	}
	
	public void setUser(User u)
	{
		users.put(u.getUUID(), u);
	}
	
	public enum Rank
	{
		NONE("None", JSONChatColor.WHITE, JSONChatColor.GRAY, -1),
		USER("User", JSONChatColor.WHITE, JSONChatColor.GRAY, 0),
		MODERATOR("Moderator ", JSONChatColor.YELLOW, JSONChatColor.WHITE, 80),
		ADMIN("Admin", JSONChatColor.RED, JSONChatColor.WHITE, 90),
		OWNER("Owner", JSONChatColor.LIGHT_PURPLE, JSONChatColor.LIGHT_PURPLE, 100);
		
		private String name;
		private JSONChatColor color;
		private JSONChatColor chat;
		private int rank;
		
		private Rank(String name, JSONChatColor color, JSONChatColor chat, int rank)
		{
			this.name = name;
			this.chat = chat;
			this.color = color;
			this.rank = rank;
		}
		
		public int getInt()
		{
			return rank;
		}
		
		public String getName()
		{
			return name;
		}
		
		public JSONChatColor getColor()
		{
			return color;
		}
		
		public JSONChatColor getChatColor()
		{
			return chat;
		}
		
		public ChatColor getBukkitColor()
		{
			return ChatColor.getByChar(color.getColorCode());
		}
	}
	
	public Rank toRank(String rank)
	{
		for(Rank k : Rank.values())
		{
			if(k.name().equalsIgnoreCase(rank))
			{
				return k;
			}
		}
		return Rank.NONE;
	}
	
	/*public void startTask(String name)
	{
		final String player = name;
		new org.bukkit.scheduler.BukkitRunnable()
		{
			String n = player;
			@Override
			public void run() {
				long i = System.nanoTime();
			}
			
		}.runTaskTimer(plugin, 10L, 10L);
	}*/
}

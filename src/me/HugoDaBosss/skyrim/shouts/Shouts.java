package me.HugoDaBosss.skyrim.shouts;

import java.util.ArrayList;

import me.HugoDaBosss.skyrim.Skyrim;
import me.HugoDaBosss.skyrim.User;
import me.HugoDaBosss.skyrim.util.BossBar;
import me.HugoDaBosss.skyrim.util.Items;
import me.HugoDaBosss.skyrim.util.RegionUtils;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


public class Shouts
{
	private ArrayList<String> block;
	
	public Shouts(Plugin plugin, Skyrim skyrim)
	{
		block = new ArrayList<String>();
	}
	
	public ShoutType getShout(String shout)
	{
		for(ShoutType st : ShoutType.values())
		{
			if(st.getName().equalsIgnoreCase(shout))
				return st;
		}
		return ShoutType.NONE;
	}
	
	public ShoutType getShoutFull(String fullname)
	{
		fullname = ChatColor.stripColor(fullname);
		for(ShoutType st : ShoutType.values())
		{
			if(fullname.equalsIgnoreCase(st.getFullName()))
			{
				return st;
			}
		}
		if(fullname.contains("%"))return ShoutType.RELOADING;
		return ShoutType.NONE;
	}
	
	public ShoutType getShoutSimple(String simple)
	{
		for(ShoutType st : ShoutType.values())
		{
			if(simple.equalsIgnoreCase(st.toString()))
				return st;
		}
		return ShoutType.NONE;
	}
	
	public boolean addBlock(final Player p)
	{
		final String name = p.getName();
		if(block.contains(name))
			return false;
		block.add(name);
		
		new BukkitRunnable()
		{
			int i = 0;
			
			@Override
			public void run() {
				org.bukkit.inventory.ItemStack is = Items.createItem(Material.NETHER_STAR, ChatColor.RED + "" + i*5 + "%", 1);
				PlayerInventory pi = p.getInventory();
				pi.setItem(6, is);
				pi.setItem(7, is);
				pi.setItem(8, is);
				i++;
				if(i == 21)
				{
					this.cancel();
					if(p.isOnline() && p != null)
					{
						me.HugoDaBosss.skyrim.Skyrim.Users.getUserPlayer(p).reloadShouts();
					}
					block.remove(name);
				}
			}
			
		}.runTaskTimer(me.HugoDaBosss.skyrim.Skyrim.getPlugin(), 0L, (p.getGameMode() == GameMode.CREATIVE ? 4L : 20L));
		return true;
	}
	
	public void doShout(Player p, ShoutType s)
	{
		if(s == ShoutType.NONE)
		{
			p.sendMessage(ChatColor.RED + "No shout selected! Right click to select a shout!");
			return;
		}
		/*if(s == ShoutType.RELOADING)
		{
			p.sendMessage(ChatColor.RED + "Your shout ability is reloading! (" + p.getInventory().getItem(6).getItemMeta().getDisplayName() + ")");
			return;
		}
		if(!addBlock(p))
		{
			p.sendMessage(ChatColor.RED + "You shout ability is cooling down!");
			return;
		}*/
		if(RegionUtils.isInSpawn(p.getLocation()))
		{
			p.sendMessage(ChatColor.RED + "You cannot use shouts in spawn!");
			return;
		}
		User u = Skyrim.Users.getUserPlayer(p);
		if(u.getMana() - s.getMana() < 0)
		{
			p.sendMessage(ChatColor.RED + "You don't have enough mana!");
			return;
		}
		u.setMana(u.getMana() - s.getMana());
		BossBar.displayTextBar(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + (int) u.getMana() + " mana" , p, (int)(u.getMana() / u.getMaxMana() * 100));
		s.getShout().shout(p);		
	}
}

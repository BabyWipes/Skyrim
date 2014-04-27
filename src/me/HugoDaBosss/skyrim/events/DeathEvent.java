package me.HugoDaBosss.skyrim.events;

import java.util.HashMap;
import java.util.Random;

import me.HugoDaBosss.skyrim.Mobs;
import me.HugoDaBosss.skyrim.Skyrim;
import me.HugoDaBosss.skyrim.User;
import me.HugoDaBosss.skyrim.util.BossBar;
import me.HugoDaBosss.skyrim.util.Locations;
import me.HugoDaBosss.skyrim.util.Locations.Names;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DeathEvent implements org.bukkit.event.Listener
{

	private Location spawn;
	private HashMap<String, Long> deaths;
	
	public DeathEvent()
	{
		spawn = Locations.getLocation(Names.SPAWN);
		deaths = new HashMap<String, Long>();
	}
	
	@EventHandler
	public void death(org.bukkit.event.entity.PlayerDeathEvent e)
	{
		e.setDeathMessage(null);
		Player p = e.getEntity();
		BossBar.hasHealthBar.put(p.getName(), false);
		if(deaths.containsKey(p.getName()) && deaths.get(p.getName()) + 1000 > System.currentTimeMillis())
			return;
		else
			deaths.put(p.getName(), System.currentTimeMillis());
		me.HugoDaBosss.skyrim.User u = Skyrim.Users.getUserPlayer(p);
		if(p.getVehicle() != null)
		{
			p.getVehicle().remove();
		}
		if(p.getKiller() != null)
		{
			Player k = p.getKiller();
			k.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You got " + ChatColor.BOLD + "20g" + ChatColor.GRAY + ChatColor.ITALIC + " for killing " + Skyrim.getRankName(p) + p.getName());
			Skyrim.Users.getUserPlayer(p).addMoney(20);
			p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You lost " + ChatColor.BOLD + "10g" + ChatColor.GRAY + ChatColor.ITALIC + " for getting killed by " + Skyrim.getRankName(k) + k.getName());
			u.removeMoney(10);
		}
		else
		{
			if(p.getLastDamageCause() instanceof EntityDamageByEntityEvent)
			{
				Entity l = ((EntityDamageByEntityEvent) p.getLastDamageCause()).getDamager();
				String name = "";
				if(l instanceof Zombie)
				{
					Zombie z = (Zombie) l;
					if(z.getEquipment().getItemInHand().getType() == Mobs.z2[4].getType())
					{
						name = "Bandit";
					}
					else
					{
						name = "Weak Bandit";
					}
				}
				else if(l instanceof Skeleton)
				{
					Skeleton s = (Skeleton) l;
					if(s.getEquipment().getItemInHand().getEnchantmentLevel(Enchantment.ARROW_DAMAGE) == Mobs.s2[4].getEnchantmentLevel(Enchantment.ARROW_DAMAGE))
					{
						name = "Archer";
					}
					else
					{
						name = "Weak Archer";
					}
				}
				else if(l instanceof Wolf)
				{
					name = "Wolf";
				}
				if(!name.equalsIgnoreCase(""))
					p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You lost " + ChatColor.BOLD + "10g" + ChatColor.GRAY + ChatColor.ITALIC + " for getting killed by " + (name.startsWith("A") ? "an " : "a ") + ChatColor.WHITE + ChatColor.BOLD + name);
				else
					p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You lost " + ChatColor.BOLD + "10g" + ChatColor.GRAY + ChatColor.ITALIC + " for dying");
			}
			else
			{
				p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You lost " + ChatColor.BOLD + "10g" + ChatColor.GRAY + ChatColor.ITALIC + " for dying");
			}
			u.removeMoney(5);
		}
		u.setActiveShout(1, u.getActiveShout(1));
	}
	
	@EventHandler
	public void entitydeath(org.bukkit.event.entity.EntityDeathEvent e)
	{
		if(e.getEntity() instanceof Player)
			return;
		if(!(e.getEntity() instanceof LivingEntity))
			return;
		LivingEntity en = e.getEntity();
		if(en instanceof Zombie)
		{
			e.getDrops().clear();
			e.setDroppedExp(0);
			if(en.getKiller() == null)
			{
				return;
			}
			Player p = en.getKiller();
			Zombie z = (Zombie) en;
			int reward;
			String name;
			if(z.getEquipment().getItemInHand().getType() == Mobs.z2[4].getType())
			{
				reward = 5;
				name = "Bandit";
			}
			else
			{
				reward = 3;
				name = "Weak Bandit";
			}
			p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You got " + ChatColor.BOLD + reward + "g" + ChatColor.GRAY + ChatColor.ITALIC + " for killing a " + ChatColor.WHITE + ChatColor.BOLD +  name);
			User u = Skyrim.Users.getUserPlayer(p);
			u.addMoney(reward);
			if(p.getItemInHand().getMaxStackSize() == 1)
			{
				p.getItemInHand().setDurability((short) (p.getItemInHand().getDurability() - reward*3));
				p.setItemInHand(p.getItemInHand());
			}
			String a = z.getEquipment().getHelmet().getItemMeta().getDisplayName();
			//Skyrim.info(z.getEquipment().getHelmet().getItemMeta().getDisplayName());
			if(a != null)
			{
				if(a.equalsIgnoreCase("gold"))
				{
					int i = (new Random().nextInt(9)) + 1;
					p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You found " + ChatColor.YELLOW + ChatColor.BOLD + i + "g" + ChatColor.RESET + ChatColor.GRAY + ChatColor.ITALIC + " in the " + ChatColor.RESET + ChatColor.WHITE + ChatColor.BOLD + name);
					u.addMoney(i);
				}
				else if(a.equalsIgnoreCase("mana"))
				{
					int i = (new Random().nextInt(20)) + 1;
					p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You took " + ChatColor.DARK_PURPLE + ChatColor.BOLD + i + " mana" + ChatColor.RESET + ChatColor.GRAY + ChatColor.ITALIC + " from the " + ChatColor.RESET + ChatColor.WHITE + ChatColor.BOLD + name);
					u.addMana(i);
				}
			}
		}
		else if(en instanceof Skeleton)
		{
			e.getDrops().clear();
			e.setDroppedExp(0);
			if(en.getKiller() == null)
			{
				return;
			}
			Player p = en.getKiller();
			Skeleton s = (Skeleton) en;
			int reward;
			String name;
			if(s.getEquipment().getItemInHand().getEnchantmentLevel(Enchantment.ARROW_DAMAGE) == Mobs.s2[4].getEnchantmentLevel(Enchantment.ARROW_DAMAGE))
			{
				reward = 5;
				name = "Archer";
			}
			else
			{
				reward = 3;
				name = "Weak Archer";
			}
			
			p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You got " + ChatColor.BOLD + reward + "g" + ChatColor.GRAY + ChatColor.ITALIC + " for killing " + (name.startsWith("A") ? "an " : "a ") + ChatColor.WHITE + ChatColor.BOLD +  name);
			User u = Skyrim.Users.getUserPlayer(p);
			u.addMoney(reward);
			if(p.getItemInHand().getMaxStackSize() == 1)
			{
				p.getItemInHand().setDurability((short) (p.getItemInHand().getDurability() - reward*3));
				p.setItemInHand(p.getItemInHand());
			}
			String a = s.getEquipment().getHelmet().getItemMeta().getDisplayName();
			if(a != null)
			{
				if(a.equalsIgnoreCase("gold"))
				{
					int i = (new Random().nextInt(9)) + 1;
					p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You found " + ChatColor.YELLOW + ChatColor.BOLD + i + "g" + ChatColor.RESET + ChatColor.GRAY + ChatColor.ITALIC + " in the " + ChatColor.RESET + ChatColor.WHITE + ChatColor.BOLD + name);
					u.addMoney(i);
				}
				else if(a.equalsIgnoreCase("mana"))
				{
					int i = (new Random().nextInt(20)) + 1;
					p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You took " + ChatColor.DARK_PURPLE + ChatColor.BOLD + i + " mana" + ChatColor.RESET + ChatColor.GRAY + ChatColor.ITALIC + " from the " + ChatColor.RESET + ChatColor.WHITE + ChatColor.BOLD + name);
					u.addMana(i);
				}
			}
		}
		else if(en instanceof Wolf)
		{
			e.getDrops().clear();
			e.setDroppedExp(0);
			if(en.getKiller() == null)
			{
				return;
			}
			Player p = en.getKiller();
			int reward = 5;
			String name = "Wolf";
			p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You got " + ChatColor.BOLD + reward + "g" + ChatColor.GRAY + ChatColor.ITALIC + " for killing " + (name.startsWith("A") ? "an " : "a ") + ChatColor.WHITE + ChatColor.BOLD +  name);
			Skyrim.Users.getUserPlayer(p).addMoney(reward);
			if(p.getItemInHand().getMaxStackSize() == 1)
			{
				p.getItemInHand().setDurability((short) (p.getItemInHand().getDurability() - reward*3));
				p.setItemInHand(p.getItemInHand());
			}
		}
		else if(en instanceof Horse)
		{
			((Horse)en).getInventory().setSaddle(null);
			e.getDrops().clear();
			if(en.getKiller() == null)
			{
				return;
			}
			Player p = en.getKiller();
			p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You lost " + ChatColor.BOLD + 5 + "g" + ChatColor.GRAY + ChatColor.ITALIC + " for killing a " + ChatColor.WHITE + ChatColor.BOLD + "Horse");
			Skyrim.Users.getUserPlayer(p).removeMoney(5);
		}
		else if(en instanceof EnderDragon)
		{
			e.getDrops().clear();
			
		}
		else
		{
			e.getDrops().clear();
		}
	}
	
	@EventHandler
	public void playerrespawn(org.bukkit.event.player.PlayerRespawnEvent e)
	{
		e.setRespawnLocation(spawn);
	}

}

package me.HugoDaBosss.skyrim;

import java.util.Arrays;
import java.util.Random;

import me.HugoDaBosss.skyrim.util.Locations;
import me.HugoDaBosss.skyrim.util.Locations.Names;
import net.minecraft.server.v1_7_R3.EntityWolf;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftWolf;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class Mobs {
	
	private static World w;
	public static ItemStack[] z1,z2,s1,s2;
	private static boolean dragon;
	private static int id;
	
	public void initializeMobs()
	{
		//horse = new Location(Bukkit.getWorlds().get(0), 1, 60, 299);
		w = Bukkit.getWorlds().get(0);
		new BukkitRunnable()
		{

			@Override
			public void run() {
				Skyrim.getPlugin().getLogger().info("Initializing mobs...");
				for(LivingEntity e : w.getLivingEntities())
				{
					if(!(e instanceof Player))
						e.remove();
				}
				for(Entity e : w.getEntities())
				{
					if(e instanceof WitherSkull)
					{
						e.remove();
					}
				}
				z1 = new ItemStack[]{new ItemStack(Material.IRON_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.STONE_AXE)};
				z2 = new ItemStack[]{new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.STONE_SWORD)};
				s1 = new ItemStack[]{new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.BOW)};
				ItemStack bow = new ItemStack(Material.BOW);
				bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
				s2 = new ItemStack[]{new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.IRON_BOOTS), bow};
				startTask();
				Skyrim.getPlugin().getLogger().info("Initialized mobs!");
			}
			
		}.runTaskLater(Skyrim.getPlugin(), 20L);
	}
	
	public static void spawnZombie(Location l)
	{
		Random r = new Random();
		Zombie z = (Zombie) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
		if(r.nextBoolean())
		{
			z.getEquipment().setArmorContents(Arrays.copyOfRange(z1, 0, 3));
			z.getEquipment().setHelmet(z1[0]);
			z.getEquipment().setItemInHand(z1[4]);
		}
		else
		{
			z.getEquipment().setArmorContents(Arrays.copyOfRange(z2, 0, 3));
			z.getEquipment().setHelmet(z2[0]);
			z.getEquipment().setItemInHand(z2[4]);
		}
		int a = r.nextInt(20);
		if(a == 2)
		{
			ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
			LeatherArmorMeta lam = (LeatherArmorMeta)lhelmet.getItemMeta();
			lam.setColor(Color.fromRGB(220, 220, 45));
			lam.setDisplayName("gold");
			lhelmet.setItemMeta(lam);
			z.getEquipment().setHelmet(lhelmet);
		}
		else if(a == 3)
		{
			ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
			LeatherArmorMeta lam = (LeatherArmorMeta)lhelmet.getItemMeta();
			lam.setColor(Color.fromRGB(150, 50, 200));
			lam.setDisplayName("mana");
			lhelmet.setItemMeta(lam);
			z.getEquipment().setHelmet(lhelmet);
		}
		z.getEquipment().setBootsDropChance(0);
		z.getEquipment().setLeggingsDropChance(0);
		z.getEquipment().setChestplateDropChance(0);
		z.getEquipment().setHelmetDropChance(0);
		z.getEquipment().setItemInHandDropChance(0);
		z.setCanPickupItems(false);
		//zs.add(z);
	}
	
	public static void spawnSkeleton(Location l)
	{
		Random r = new Random();
		Skeleton s = (Skeleton) l.getWorld().spawnEntity(l, EntityType.SKELETON);
		if(r.nextBoolean())
		{
			s.getEquipment().setArmorContents(Arrays.copyOfRange(s1, 0, 3));
			s.getEquipment().setHelmet(s1[0]);
			s.getEquipment().setItemInHand(s1[4]);
		}
		else
		{
			s.getEquipment().setArmorContents(Arrays.copyOfRange(s2, 0, 3));
			s.getEquipment().setHelmet(s2[0]);
			s.getEquipment().setItemInHand(s2[4]);
		}
		int a = r.nextInt(20);
		if(a == 3)
		{
			ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
			LeatherArmorMeta lam = (LeatherArmorMeta)lhelmet.getItemMeta();
			lam.setColor(Color.fromRGB(220, 220, 45));
			lam.setDisplayName("gold");
			lhelmet.setItemMeta(lam);
			s.getEquipment().setHelmet(lhelmet);
		}
		else if(a == 2)
		{
			ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
			LeatherArmorMeta lam = (LeatherArmorMeta)lhelmet.getItemMeta();
			lam.setColor(Color.fromRGB(150, 50, 200));
			lam.setDisplayName("mana");
			lhelmet.setItemMeta(lam);
			s.getEquipment().setHelmet(lhelmet);
		}
		s.getEquipment().setBootsDropChance(0);
		s.getEquipment().setLeggingsDropChance(0);
		s.getEquipment().setChestplateDropChance(0);
		s.getEquipment().setHelmetDropChance(0);
		s.getEquipment().setItemInHandDropChance(0);
		s.setCanPickupItems(false);
		//ss.add(s);
	}
	
	public static void spawnHorse(Location l)
	{
		Horse h = (Horse) l.getWorld().spawnEntity(l, EntityType.HORSE);
		h.setTamed(true);
		h.setAdult();
		h.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		h.setMaxHealth(40.0);
		h.setJumpStrength(1.0);
		Random r = new Random();
		switch(r.nextInt(5))
		{
		case 0:	h.setColor(Horse.Color.BROWN); break;
		case 1:	h.setColor(Horse.Color.BLACK); break;
		case 2: h.setColor(Horse.Color.CHESTNUT); break;
		case 3: h.setColor(Horse.Color.DARK_BROWN); break;
		case 4: h.setColor(Horse.Color.GRAY); break;
		}
		switch(r.nextInt(5))
		{
		case 1: h.setStyle(Horse.Style.BLACK_DOTS); break;
		case 2: h.setStyle(Horse.Style.NONE); break;
		case 3: h.setStyle(Horse.Style.WHITE); break;
		case 4: h.setStyle(Horse.Style.WHITE_DOTS); break;
		case 5: h.setStyle(Horse.Style.WHITEFIELD); break;
		}
		//hs.add(h);
	}
	
	private static void spawnWolf(Location l)
	{
		Wolf w = (Wolf) l.getWorld().spawnEntity(l, EntityType.WOLF);
		w.setAdult();
		w.setMaxHealth(40.0);
		w.setHealth(40.0);
		w.setAngry(true);
		//ws.add(w);
	}
	
	public static void spawnDragon(Location l)
	{
		EnderDragon d = (EnderDragon) l.getWorld().spawnEntity(l, EntityType.ENDER_DRAGON);
		dragon = true;
		id = d.getEntityId();
		d.setMaxHealth(500.0);
		d.setHealth(500.0);
		Skeleton s = (Skeleton) l.getWorld().spawnEntity(l, EntityType.SKELETON);
		s.getEquipment().setItemInHand(null);
		s.getEquipment().setArmorContents(new ItemStack[]{new ItemStack(Material.SKULL_ITEM, 1, (byte) 1), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS)});
		d.setPassenger(s);
		s.setNoDamageTicks(720000);
	}
	
	public static boolean isDragon()
	{
		return dragon;
	}
	
	public static int getDragonId()
	{
		return id;
	}
	
	private static void startTask()
	{
		new BukkitRunnable()
		{

			@Override
			public void run() {
				Random r = new Random();
				int online = Bukkit.getOnlinePlayers().length;
				for(int i = w.getEntitiesByClasses(Skeleton.class, Zombie.class).size(); i < (online * 5) + 30; i++)
				{
					if(r.nextBoolean())
						Mobs.spawnSkeleton(Skyrim.getRandomLocation());
					else
						Mobs.spawnZombie(Skyrim.getRandomLocation());
				}
				for(int i = w.getEntitiesByClasses(Horse.class).size(); i < 2; i++)
				{
					Mobs.spawnHorse(Locations.getLocation(Names.HORSE));
				}
				for(int i = w.getEntitiesByClasses(Wolf.class).size(); i < 5; i++)
				{
					Mobs.spawnWolf(Skyrim.getRandomLocation());
				}
			}
			
		}.runTaskTimer(Skyrim.getPlugin(), 100L, 100L);
		
		new BukkitRunnable()
		{

			@Override
			public void run() {
				for(Wolf w : Bukkit.getWorlds().get(0).getEntitiesByClass(Wolf.class))
				{
					if(w != null && !w.isDead())
					{
						for(Entity e : w.getNearbyEntities(32.0, 32.0, 32.0))
						{
							if(e instanceof Player)
							{
								Player p = (Player)e;
								EntityWolf nmsWolf = ((CraftWolf) w).getHandle();
								nmsWolf.setTarget(((CraftPlayer) p).getHandle());
								nmsWolf.setGoalTarget(((CraftPlayer) p).getHandle());
							}
						}
					}
				}
				for(Horse h : Bukkit.getWorlds().get(0).getEntitiesByClass(Horse.class))
				{
					if(h != null && !h.isDead())
					{
						Block b = h.getLocation().getBlock();
						if(b.getType() == Material.STATIONARY_WATER || b.getType() == Material.WATER)
							h.damage(5.0);
					}
				}
			}
		}.runTaskTimer(Skyrim.getPlugin(), 50L, 50L);
		
		/*
		new BukkitRunnable()
		{

			@Override
			public void run() {
				if(Calendar.getInstance().get(Calendar.MINUTE) == 30)
				{
					if(!Mobs.isDragon())
					{
						Mobs.spawnDragon(Locations.getLocation("dragon"));
					}
				}
			}
			
		}.runTaskTimer(Skyrim.getPlugin(), 1000L, 1000L);
		*/
	}

}

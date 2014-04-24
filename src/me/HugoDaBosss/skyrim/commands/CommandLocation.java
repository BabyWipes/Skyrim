package me.HugoDaBosss.skyrim.commands;

import java.lang.reflect.Field;

import me.HugoDaBosss.skyrim.Mobs;
import me.HugoDaBosss.skyrim.User;
import me.HugoDaBosss.skyrim.Users.Rank;
import me.HugoDaBosss.skyrim.util.BarAPI;
import me.HugoDaBosss.skyrim.util.Locations;
import me.HugoDaBosss.skyrim.util.RankUtils;
import net.minecraft.server.v1_7_R3.DataWatcher;
import net.minecraft.server.v1_7_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_7_R3.PacketPlayOutSpawnEntityLiving;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandLocation extends SkyrimCommand 
{

	public CommandLocation(Plugin plugin, me.HugoDaBosss.skyrim.Skyrim skyrim) {
		super(plugin, skyrim);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("location"))
		{
			if(!(sender instanceof Player))
			{
				sender.sendMessage(ChatColor.RED + "Stupid console");return true;
			}
			if(!RankUtils.isHighEnough(sender, Rank.ADMIN))
			{
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
				return true;
			}
			if(args.length > 0)
			{
				if(args[0].equalsIgnoreCase("rank"))
				{
					User u = me.HugoDaBosss.skyrim.Skyrim.Users.getUser(sender.getName());
					u.setRank(me.HugoDaBosss.skyrim.Skyrim.Users.toRank(args[1]));
					sender.sendMessage(ChatColor.GREEN + "You are now " + me.HugoDaBosss.skyrim.Skyrim.Users.toRank(args[1]).toString());
					return true;
				}
				else if(args[0].equalsIgnoreCase("spawndragon"))
				{
					Mobs.spawnDragon(Locations.getLocation("dragon"));
				}
				else if(args[0].equalsIgnoreCase("death"))
				{
					Player p = (Player) sender;
					
					//SPAWNDRAGON
					PacketPlayOutSpawnEntityLiving mobPacket = new PacketPlayOutSpawnEntityLiving();
					Location loc = p.getLocation();
					loc.setY(loc.getY() + 20);
					try {
						Field a = BarAPI.getField(mobPacket.getClass(), "a");
				        a.setAccessible(true);
						a.set(mobPacket, (int) BarAPI.ENTITY_ID);
						
						Field b = BarAPI.getField(mobPacket.getClass(), "b");
				        b.setAccessible(true);
						b.set(mobPacket, (byte) EntityType.ENDER_DRAGON.getTypeId());
						
						Field c = BarAPI.getField(mobPacket.getClass(), "c");
				        c.setAccessible(true);
						c.set(mobPacket, (int) Math.floor(loc.getBlockX() * 32.0D));
						
						Field d = BarAPI.getField(mobPacket.getClass(), "d");
				        d.setAccessible(true);
						d.set(mobPacket, (int) Math.floor((loc.getBlockY() - 4)* 32.0D));
						
						Field e = BarAPI.getField(mobPacket.getClass(), "e");
				        e.setAccessible(true);
						e.set(mobPacket, (int) Math.floor(loc.getBlockZ() * 32.0D));
						
						Field f = BarAPI.getField(mobPacket.getClass(), "f");
				        f.setAccessible(true);
						f.set(mobPacket, (byte) 0);
						
						Field g = BarAPI.getField(mobPacket.getClass(), "g");
				        g.setAccessible(true);
						g.set(mobPacket, (byte) 0);
						
						Field h = BarAPI.getField(mobPacket.getClass(), "h");
				        h.setAccessible(true);
						h.set(mobPacket, (byte) 0);
						
						Field i = BarAPI.getField(mobPacket.getClass(), "i");
				        i.setAccessible(true);
						i.set(mobPacket, (byte) 0);
						
						Field j = BarAPI.getField(mobPacket.getClass(), "j");
				        j.setAccessible(true);
						j.set(mobPacket, (byte) 0);
						
						Field k = BarAPI.getField(mobPacket.getClass(), "k");
				        k.setAccessible(true);
						k.set(mobPacket, (byte) 0);
					
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					DataWatcher watcher = BarAPI.getWatcher("", 200);
					
					try{
						Field t = PacketPlayOutSpawnEntityLiving.class.getDeclaredField("l");
						t.setAccessible(true);
						t.set(mobPacket, watcher);
					} catch(Exception ex){
						ex.printStackTrace();
					}
					
					BarAPI.sendPacket(p, mobPacket);
					
					//ENTITY ZERO HEALTH
					watcher = BarAPI.getWatcher("", 0);
					PacketPlayOutEntityMetadata metaPacket = new PacketPlayOutEntityMetadata();
					
					Field a = BarAPI.getField(metaPacket.getClass(), "a");
			        a.setAccessible(true);
					try {
						a.set(metaPacket, (int) BarAPI.ENTITY_ID);
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}
					
					try{
						Field b = PacketPlayOutEntityMetadata.class.getDeclaredField("b");
						b.setAccessible(true);
						b.set(metaPacket, watcher.c());
					} catch(Exception e){
						e.printStackTrace();
					}
					
					BarAPI.sendPacket(p, metaPacket);	
					
					final String name = p.getName();
					
					new BukkitRunnable()
					{

						@Override
						public void run() {
							PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy();
							
							Field a = BarAPI.getField(destroyPacket.getClass(), "a");
					        a.setAccessible(true);
							try {
								a.set(destroyPacket, new int[]{BarAPI.ENTITY_ID});
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
							BarAPI.sendPacket(Bukkit.getPlayer(name), destroyPacket);
						}
						
					}.runTaskLater(me.HugoDaBosss.skyrim.Skyrim.getPlugin(), 220L);
					return true;
				}
				else if(args[0].equalsIgnoreCase("test"))
				{
					//MapUtils.writeText((Player)sender, "§48;Have a nice fight :)");
					return true;
				}
				Locations.setLocation(Bukkit.getPlayer(sender.getName()).getLocation(), args[0]);
				sender.sendMessage(ChatColor.GREEN + "Updated location " + args[0]);
				
			}
			else
			{
				sender.sendMessage(ChatColor.GOLD + "rl_out1, rl_out2, rl_in1, rl_in2");
			}
			return true;
		}
		return false;
	}
	
}

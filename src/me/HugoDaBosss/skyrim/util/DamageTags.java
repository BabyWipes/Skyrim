package me.HugoDaBosss.skyrim.util;

import java.lang.reflect.Field;
import java.util.Random;

import me.HugoDaBosss.skyrim.Skyrim;
import net.minecraft.server.v1_7_R3.DataWatcher;
import net.minecraft.server.v1_7_R3.Packet;
import net.minecraft.server.v1_7_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_7_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R3.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_7_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_7_R3.PacketPlayOutSpawnEntityLiving;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DamageTags {
	
	private static int c = 0;
	
	public static void addLine(Location loc, String line) {
        final Horse entity = (Horse)loc.getWorld().spawnEntity(loc.clone().add(0,55,0), EntityType.HORSE);
        entity.setCustomName(line);
        entity.setCustomNameVisible(true);
        entity.setAge(-1700000);
        entity.setAgeLock(true);
        final Entity veh = loc.getWorld().spawnEntity(loc.clone().add(0,55,0), EntityType.WITHER_SKULL);
        if (veh != null) {
            veh.setVelocity(new Vector(0,0,0));
            veh.setPassenger(entity);
        }
        new BukkitRunnable()
        {

			@Override
			public void run() {
				entity.remove();
				veh.remove();
			}
        	
        }.runTaskLater(Skyrim.getPlugin(), 20L);
    }
	
	public static void sendPacket(Player player, Packet packet)
	{
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	
	public static void showDamage(final Player p, Location l, String damage)
	{
		Random r = new Random();
		c += 2;
		if(c > 100)
			c = 1;
		final int current = c + 1235;
		l.setX(l.getX() + (r.nextDouble() * 4 - 2));
		l.setZ(l.getZ() + (r.nextDouble() * 4 - 2));
		l.setY(l.getY() + (r.nextDouble() * 2 + 4));
		PacketPlayOutSpawnEntityLiving spawnpacket = getMobPacket("-" + damage, l, current, EntityType.HORSE, true);
		sendPacket(p,spawnpacket);
		PacketPlayOutSpawnEntity skullpacket = getEntityPacket("-" + damage, l, current, EntityType.WITHER_SKULL, true);
		sendPacket(p,skullpacket);
		Skyrim.getPlugin().getLogger().info("OMFG");
		PacketPlayOutAttachEntity attachPacket = getAttachPacket(current);
		sendPacket(p,attachPacket);
		/*PacketPlayOutEntityEffect effectPacket = getEffectPacket(current);
		sendPacket(p,effectPacket);*/
		new BukkitRunnable()
		{

			@Override
			public void run() {
				PacketPlayOutEntityDestroy dpacket = getDestroyEntityPacket(current);
				sendPacket(p,dpacket);
			}
			
		}.runTaskLater(Skyrim.getPlugin(), 20L);
	}
	
	@SuppressWarnings("deprecation")
	public static PacketPlayOutSpawnEntityLiving getMobPacket(String text, Location loc, int id, EntityType type, boolean data){
		PacketPlayOutSpawnEntityLiving mobPacket = new PacketPlayOutSpawnEntityLiving();
		try {
			Field a = getField(mobPacket.getClass(), "a");
	        a.setAccessible(true);
			a.set(mobPacket, (int) id);
			
			Field b = getField(mobPacket.getClass(), "b");
	        b.setAccessible(true);
			b.set(mobPacket, (byte) type.getTypeId());
			
			Field c = getField(mobPacket.getClass(), "c");
	        c.setAccessible(true);
			c.set(mobPacket, (int) Math.floor(loc.getBlockX() * 32.0D));
			
			Field d = getField(mobPacket.getClass(), "d");
	        d.setAccessible(true);
			d.set(mobPacket, (int) Math.floor((loc.getBlockY() - 4)* 32.0D));
			
			Field e = getField(mobPacket.getClass(), "e");
	        e.setAccessible(true);
			e.set(mobPacket, (int) Math.floor(loc.getBlockZ() * 32.0D));
			
			Field f = getField(mobPacket.getClass(), "f");
	        f.setAccessible(true);
			f.set(mobPacket, (byte) 0);
			
			Field g = getField(mobPacket.getClass(), "g");
	        g.setAccessible(true);
			g.set(mobPacket, (byte) 0);
			
			Field h = getField(mobPacket.getClass(), "h");
	        h.setAccessible(true);
			h.set(mobPacket, (byte) 0);
			
			Field i = getField(mobPacket.getClass(), "i");
	        i.setAccessible(true);
			i.set(mobPacket, (byte) 0);
			
			Field j = getField(mobPacket.getClass(), "j");
	        j.setAccessible(true);
			j.set(mobPacket, (byte) 0);
			
			Field k = getField(mobPacket.getClass(), "k");
	        k.setAccessible(true);
			k.set(mobPacket, (byte) 0);
		
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(data)
		{
			DataWatcher watcher = getWatcher(text, 200);
			
			try{
				Field t = PacketPlayOutSpawnEntityLiving.class.getDeclaredField("l");
				t.setAccessible(true);
				t.set(mobPacket, watcher);
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		return mobPacket;
	}
	
	public static PacketPlayOutSpawnEntity getEntityPacket(String text, Location loc, int id, EntityType type, boolean data){
		PacketPlayOutSpawnEntity mobPacket = new PacketPlayOutSpawnEntity();
		try {
			Field a = getField(mobPacket.getClass(), "a");
	        a.setAccessible(true);
			a.set(mobPacket, (int) id);
			
			Field b = getField(mobPacket.getClass(), "b");
	        b.setAccessible(true);
			b.set(mobPacket, (byte) 66);
			
			Field c = getField(mobPacket.getClass(), "c");
	        c.setAccessible(true);
			c.set(mobPacket, (int) Math.floor(loc.getBlockX() * 32.0D));
			
			Field d = getField(mobPacket.getClass(), "d");
	        d.setAccessible(true);
			d.set(mobPacket, (int) Math.floor((loc.getBlockY() - 4)* 32.0D));
			
			Field e = getField(mobPacket.getClass(), "e");
	        e.setAccessible(true);
			e.set(mobPacket, (int) Math.floor(loc.getBlockZ() * 32.0D));
			
			Field f = getField(mobPacket.getClass(), "f");
	        f.setAccessible(true);
			f.set(mobPacket, (byte) 0);
			
			Field g = getField(mobPacket.getClass(), "g");
	        g.setAccessible(true);
			g.set(mobPacket, (byte) 0);
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(data)
		{
			DataWatcher watcher = getWatcher(null, 200);
			
			try{
				Field t = PacketPlayOutSpawnEntityLiving.class.getDeclaredField("h");
				t.setAccessible(true);
				t.set(mobPacket, watcher);
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		return mobPacket;
	}
	
	public static PacketPlayOutEntityEffect getEffectPacket(int id)
	{
		PacketPlayOutEntityEffect effectPacket = new PacketPlayOutEntityEffect();
		
		try {
			Field a = getField(effectPacket.getClass(), "a");
	        a.setAccessible(true);
			a.set(effectPacket, (int) id);
			
			Field b = getField(effectPacket.getClass(), "b");
	        b.setAccessible(true);
			b.set(effectPacket, (byte) 14);
			
			Field c = getField(effectPacket.getClass(), "c");
	        c.setAccessible(true);
			c.set(effectPacket, (byte) 1);
			
			Field d = getField(effectPacket.getClass(), "d");
	        d.setAccessible(true);
			d.set(effectPacket, (short) 10);
		
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		return effectPacket;
	}
	
	public static PacketPlayOutAttachEntity getAttachPacket(int id)
	{
		PacketPlayOutAttachEntity effectPacket = new PacketPlayOutAttachEntity();
		
		try {
			Field a = getField(effectPacket.getClass(), "a");
	        a.setAccessible(true);
			a.set(effectPacket, (int) id);
			
			Field b = getField(effectPacket.getClass(), "b");
	        b.setAccessible(true);
			b.set(effectPacket, (int) id);
		
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return effectPacket;
	}
	
	public static PacketPlayOutEntityDestroy getDestroyEntityPacket(int id){
		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy();
		
		Field a = getField(packet.getClass(), "a");
        a.setAccessible(true);
		try {
			a.set(packet, new int[]{id});
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return packet;
	}
	
	public static Field getField(Class<?> cl, String field_name){
        try {
            Field field = cl.getDeclaredField(field_name);
            return field;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static DataWatcher getWatcher(String text, int health){
		DataWatcher watcher = new DataWatcher(null);
		
		//watcher.a(0, (Byte) (byte) 0x20); //Flags, 0x20 = invisible
		//watcher.a(6, (Float) (float) health);
		if(text != null)
		{
			watcher.a(10, (String) text); //Entity name
			watcher.a(11, (Byte) (byte) 1.0); //Show name, 1 = show, 0 = don't show
			watcher.a(12, (int) -170000); 
		}
		//watcher.a(16, (Integer) (int) health); //Wither health, 300 = full health
		
		return watcher;
	}

}

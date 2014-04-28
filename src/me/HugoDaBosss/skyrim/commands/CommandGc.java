package me.HugoDaBosss.skyrim.commands;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

import me.HugoDaBosss.skyrim.Users.Rank;
import me.HugoDaBosss.skyrim.util.Lag;
import me.HugoDaBosss.skyrim.util.RankUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class CommandGc implements org.bukkit.command.CommandExecutor 
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(!RankUtils.isHighEnough(sender, Rank.ADMIN))
		{
			sender.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		double tps = Lag.getTPS();
		String msg = ChatColor.GOLD + "Current TPS: " + (tps >= 19 ? ChatColor.GREEN : (tps < 18 ? ChatColor.DARK_RED : ChatColor.RED)) + "" + tps;
		msg += "\n" + ChatColor.GOLD + "Maximum Memory: " + ChatColor.GREEN + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + " MB";
		msg += "\n" + ChatColor.GOLD + "Allocated Memory: " + ChatColor.GREEN + (Runtime.getRuntime().totalMemory() / 1024 / 1024) + " MB";
		msg += "\n" + ChatColor.GOLD + "Used Memory: " + ChatColor.GREEN + (Runtime.getRuntime().freeMemory() / 1024 / 1024) + " MB";
		for(World w : Bukkit.getWorlds())
		{
			msg += "\n" + ChatColor.RED + w.getName() + ":";
			msg += " " + ChatColor.GOLD + "Chunks: " +  ChatColor.GREEN + w.getLoadedChunks().length;
			msg += " " + ChatColor.GOLD + "Entities: " +  ChatColor.GREEN + w.getEntities().size() + " (" + w.getLivingEntities().size() + ")";
			int tile = 0;
			for(Chunk c : w.getLoadedChunks())
			{
				tile += c.getTileEntities().length;
			}
			msg += " " + ChatColor.GOLD + "Tile Entities: " + ChatColor.GREEN + tile;
		}
		sender.sendMessage(msg);
		if(args.length > 0 && args[0].equalsIgnoreCase("entities"))
		{
			HashMap<EntityType,Integer> entities = new HashMap<EntityType,Integer>();
			for(Entity e : Bukkit.getWorlds().get(0).getEntities())
			{
				if(entities.containsKey(e.getType()))
					entities.put(e.getType(), entities.get(e.getType()) + 1);
				else
					entities.put(e.getType(), 1);
			}
			EntityComparator comp = new EntityComparator(entities);
			TreeMap<EntityType, Integer> sorted = new TreeMap<EntityType,Integer>(comp);
			sorted.putAll(entities);
			for(EntityType e : sorted.keySet())
			{
				sender.sendMessage(ChatColor.YELLOW + e.toString() + ": " + ChatColor.GREEN + "" + entities.get(e));
			}
		}
		return true;
	}

}

class EntityComparator implements Comparator<EntityType> {

	HashMap<EntityType, Integer> base;
    public EntityComparator(HashMap<EntityType, Integer> base) {
        this.base = base;
    }
    
	@Override
	public int compare(EntityType a, EntityType b) {
		if(base.get(a) >= base.get(b))
			return -1;
		return 1;
	}
}

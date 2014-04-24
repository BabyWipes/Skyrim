package me.HugoDaBosss.skyrim.events;

import java.util.Arrays;

import me.HugoDaBosss.skyrim.Skyrim;
import me.HugoDaBosss.skyrim.Users.Rank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.bobacadodl.JSONChatLib.JSONChatClickEventType;
import com.bobacadodl.JSONChatLib.JSONChatColor;
import com.bobacadodl.JSONChatLib.JSONChatExtra;
import com.bobacadodl.JSONChatLib.JSONChatFormat;
import com.bobacadodl.JSONChatLib.JSONChatHoverEventType;
import com.bobacadodl.JSONChatLib.JSONChatMessage;

public class ChatEvent implements org.bukkit.event.Listener 
{

	public ChatEvent(){}
	
	@org.bukkit.event.EventHandler
	public void chat(org.bukkit.event.player.AsyncPlayerChatEvent e)
	{
		e.setCancelled(true);
		String player = e.getPlayer().getName();
		Rank r = Skyrim.Users.getUserPlayer(e.getPlayer()).getRank();
		JSONChatMessage msg = new JSONChatMessage("", JSONChatColor.WHITE, null);
		JSONChatExtra name = new JSONChatExtra(player, r.getColor(), Arrays.asList(JSONChatFormat.BOLD));
		name.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, ChatColor.BOLD + "" + r.getBukkitColor() + r.getName() + " " + player);
		name.setClickEvent(JSONChatClickEventType.SUGGEST_COMMAND, player);
		msg.addExtra(name);
		JSONChatExtra m = new JSONChatExtra(": ", JSONChatColor.WHITE,null);
		msg.addExtra(m);
		JSONChatExtra me = new JSONChatExtra(e.getMessage(), r.getChatColor(), null);
		msg.addExtra(me);
		for(Player p : Bukkit.getOnlinePlayers())
		{
			msg.sendToPlayer(p);
		}
		Bukkit.getLogger().info(player + "> " + ChatColor.translateAlternateColorCodes('&',e.getMessage()));
	}
}
package me.HugoDaBosss.skyrim.commands;

import java.util.Arrays;
import java.util.List;

import me.HugoDaBosss.skyrim.Users.Rank;
import me.HugoDaBosss.skyrim.util.RankUtils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class CommandRename extends SkyrimCommand 
{

	public CommandRename(Plugin plugin, me.HugoDaBosss.skyrim.Skyrim skyrim) {
		super(plugin, skyrim);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("rename"))
		{
			if(!(sender instanceof Player))
			{
				sender.sendMessage(ChatColor.RED + "Stupid console");
				return true;
			}
			if(!RankUtils.isHighEnough(sender, Rank.MODERATOR))
			{
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
				return true;
			}
			Player p = (Player) sender;
			if(args.length < 2)
			{
				p.sendMessage(ChatColor.RED + "Please use /rename <name|lore> <string>");
				p.sendMessage(ChatColor.RED + "Multiple lore lines are achieved by using :, for spaces use _");
			}
			else
			{
				if(args[0].equalsIgnoreCase("name"))
				{
					String name = args[1];
					name = name.replaceAll("_", " ");
					name = ChatColor.translateAlternateColorCodes('&', name);
					if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR)
					{
						p.sendMessage(ChatColor.RED + "You don't have an item in your hand!");
						return true;
					}
					ItemStack i = p.getItemInHand();
					ItemMeta im = i.getItemMeta();
					im.setDisplayName(name);
					i.setItemMeta(im);
					p.updateInventory();
				}
				else if(args[0].equalsIgnoreCase("lore"))
				{
					String name = args[1];
					name = name.replaceAll("_", " ");
					name = ChatColor.translateAlternateColorCodes('&', name);
					String[] lore = name.split(":");
					if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR)
					{
						p.sendMessage(ChatColor.RED + "You don't have an item in your hand!");
						return true;
					}
					ItemStack i = p.getItemInHand();
					ItemMeta im = i.getItemMeta();
					List<String> lores = Arrays.asList(lore);
					im.setLore(lores);
					i.setItemMeta(im);
					p.updateInventory();
				}
				else
				{
					p.sendMessage(ChatColor.RED + "Please use /rename <name|lore> <string>");
					p.sendMessage(ChatColor.RED + "Multiple lore lines are achieved by using :, for spaces use _");
				}
			}
			return true;
		}
		return false;
	}

	
	
}

package me.HugoDaBosss.skyrim.events;

import java.util.Arrays;
import java.util.Random;

import me.HugoDaBosss.skyrim.Skyrim;
import me.HugoDaBosss.skyrim.User;
import me.HugoDaBosss.skyrim.shouts.ShoutType;
import me.HugoDaBosss.skyrim.util.BossBar;
import me.HugoDaBosss.skyrim.util.Items;
import me.HugoDaBosss.skyrim.util.MaterialNames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class InteractEvent implements org.bukkit.event.Listener 
{
	
	public InteractEvent(){}
	
	@SuppressWarnings("deprecation")
	@org.bukkit.event.EventHandler
	public void interact(org.bukkit.event.player.PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		if(e.getItem() != null)
		{
			if(e.getMaterial() == Material.NETHER_STAR)
			{
				if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					e.setCancelled(true);
					User u = Skyrim.Users.getUser(p.getName());
					Inventory i = Bukkit.createInventory(null, 27,ChatColor.translateAlternateColorCodes('&', "&5Please select a shout"));
					for(ShoutType st : u.getUnlockedShouts())
					{
						i.addItem(Items.createItem(Material.NETHER_STAR, ChatColor.GOLD + st.getFullName(), 1));
					}
					i.setItem(26, Skyrim.disableshout);
					p.openInventory(i);
				}
				else if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
				{
					e.setCancelled(true);
					String d = e.getItem().getItemMeta().getDisplayName().toLowerCase();
					d = ChatColor.stripColor(d);
					Skyrim.Shouts.doShout(p, Skyrim.Shouts.getShoutFull(d));
				}
				return;
			}
			else if(e.getMaterial() == Material.getMaterial(351))
			{
				DyeColor c = DyeColor.getByDyeData(e.getItem().getData().getData());
				if(c == DyeColor.PINK || c == DyeColor.MAGENTA || c == DyeColor.PURPLE)
				{
					int max = 0, click = 0;
					String name = "";
					if(c == DyeColor.PINK)
					{
						max = 25;
						click = 5;
						name = "Weak";
					}
					else if(c == DyeColor.MAGENTA)
					{
						max = 50;
						click = 10;
						name = "Normal";
					}
					else if(c == DyeColor.PURPLE)
					{
						max = 100;
						click = 20;
						name = "Strong";
					}
					int mana;
					if(e.getItem().getItemMeta().getLore() == null)
					{
						mana = 0;
					}
					else
					{
						String lore = e.getItem().getItemMeta().getLore().get(0);
						lore = ChatColor.stripColor(lore);
						lore = lore.replaceAll(" mana", "");
						mana = Integer.parseInt(lore);
					}
					User u = Skyrim.Users.getUser(p.getName());
					double umana = u.getMana();
					int imana = mana;
					if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
					{
						if(imana == max)
						{
							p.playSound(p.getLocation(), Sound.NOTE_BASS, 10, 1);
						}
						else if(umana >= click)
						{
							u.setMana(umana - click);
							imana += click;
							p.playSound(p.getLocation(), Sound.ARROW_HIT, 10, 1);
						}
						else
						{
							if(umana < 1)
							{
								p.playSound(p.getLocation(), Sound.NOTE_BASS, 10, 1);
							}
							else
							{
								u.setMana(0);
								imana += umana;
								p.playSound(p.getLocation(), Sound.ARROW_HIT, 10, 1);
							}
						}
					}
					else
					{
						if(imana == 0)
						{
							p.playSound(p.getLocation(), Sound.NOTE_BASS, 10, 1);
						}
						else
						{
							if(imana < click)
							{
								u.setMana(umana + imana);
								imana = 0;		
								p.playSound(p.getLocation(), Sound.ARROW_HIT, 10, 1);
							}
							else if(umana + click > u.getMaxMana())
							{
								if(umana == u.getMaxMana())
								{
									p.playSound(p.getLocation(), Sound.NOTE_BASS, 10, 1);
								}
								else
								{
									u.setMana(u.getMaxMana());
									double dif = umana + click - u.getMaxMana();
									imana -= dif;
								}
							}
							else
							{
								u.setMana(umana + click);
								imana -= click;
								p.playSound(p.getLocation(), Sound.ARROW_HIT, 10, 1);
							}
						}
					}
					if(imana > max)
					{
						int dif = imana - max;
						imana -= dif;
						u.setMana(u.getMana() + dif);
					}
					BossBar.displayTextBar(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + (int) u.getMana() + " mana" , p, (int)(u.getMana() / u.getMaxMana() * 100));
					if(imana != mana)
					{
						String newlore = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + imana + " mana";
						ItemMeta meta = e.getItem().getItemMeta();
						meta.setLore(Arrays.asList(newlore));
						if(meta.getDisplayName() == null || meta.getDisplayName().equalsIgnoreCase(""))
						{
							meta.setDisplayName(ChatColor.LIGHT_PURPLE + name + " Mana Crystal");
						}
						e.getItem().setItemMeta(meta);
						p.updateInventory();
					}
					e.setCancelled(true);
				}
			}
		}
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN_POST)
			{
				Sign s = (Sign) e.getClickedBlock().getState();
				String lines[] = s.getLines();
				if(lines[2].contains("buy for") && lines[3].contains("g"))
				{
					e.setCancelled(true);
					String price = lines[3];
					price = ChatColor.stripColor(price);
					if(p.isSneaking() && p.getGameMode() == GameMode.CREATIVE)
					{
						Skyrim.Shops.addShop(s.getLocation(), p.getItemInHand());
						s.setLine(3, ChatColor.BOLD + price);
						s.update();
						p.sendMessage(ChatColor.GREEN + "Added shop!");
						return;
					}
					ItemStack is = Skyrim.Shops.getItem(s.getLocation());
					if(is == null)
					{
						p.sendMessage(ChatColor.RED + "This shop is not configured correctly!");
						return;
					}
					else if(is.getType() == Material.GOLD_NUGGET)
					{
						price = price.replaceAll("g", "");
						final String pricefinal = price;
						final ItemStack hopper = is;
						final Player player = p;
						new BukkitRunnable()
						{

							@Override
							public void run() {
								if(hopper.getItemMeta().getDisplayName() == null)
								{
									player.sendMessage(ChatColor.RED + "This shop is not configured correctly!");
								}
								else
								{
									User u = Skyrim.Users.getUser(player.getName());
									if(u.removeMoney(Integer.parseInt(pricefinal)) != -1)
									{
									}
									else
									{
										player.sendMessage(ChatColor.RED + "You don't have enough gold!");
										return;
									}
									String name = hopper.getItemMeta().getDisplayName();
									if(name.equalsIgnoreCase("mistery"))
									{
										final Random r = new Random();
										final Inventory i = Bukkit.createInventory(null, 27, "Randomizing...");
										player.openInventory(i);
										new BukkitRunnable()
										{
											int c = 0;
											
											@Override
											public void run() {
												if(c == 30)
												{
													Inventory r = Bukkit.createInventory(null, 27, "You won!");
													final ItemStack prize = Items.getRandomItem();
													r.setItem(13, prize);
													player.openInventory(r);
													new BukkitRunnable()
													{

														@Override
														public void run() {
															player.getInventory().addItem(prize);
															String name = MaterialNames.itemName(prize.getType());
															if(prize.getType() == Material.INK_SACK)
															{
																name = "Weak Mana Crystal";
															}
															player.closeInventory();
															player.sendMessage(ChatColor.GREEN + "You won " + ChatColor.BOLD + name);
														}
														
													}.runTaskLater(Skyrim.getPlugin(), 40L);
													this.cancel();
												}
												else
												{
													i.setItem(r.nextInt(27), Items.getRandomItem());
												}
												c++;
											}
											
										}.runTaskTimer(Skyrim.getPlugin(), 5L, 5L);
									}
									else if(name.startsWith("shout "))
									{
										String shout = name.split(" ")[1];
										ShoutType type = Skyrim.Shouts.getShoutSimple(shout);
										if(type == ShoutType.NONE)
										{
											player.sendMessage(ChatColor.RED + "This shout is not configured correctly!");
											return;
										}
										else
										{
											u.unlockShout(type);
											player.sendMessage(ChatColor.GREEN + "You unlocked " + ChatColor.BOLD + type.getFullName() + ChatColor.RESET + ChatColor.GREEN + ", right click to select!");
											return;
										}
									}
								}
							}
							
						}.runTaskLater(Skyrim.getPlugin(), 2L);
					}
					else
					{
						User u = Skyrim.Users.getUser(p.getName());
						price = price.replaceAll("g", "");
						if(u.removeMoney(Integer.parseInt(price)) != -1)
						{
							String from;
							p.getInventory().addItem(is);
							p.updateInventory();
							if(ChatColor.stripColor(lines[0]).equalsIgnoreCase("") || lines[0].contains("======="))
							{
								from = "";
							}
							else
							{
								from = " from " + ChatColor.stripColor(lines[0]);
							}
							p.sendMessage(ChatColor.GREEN + "Bought " + lines[1] + ChatColor.GREEN + from);
						}
						else
						{
							p.sendMessage(ChatColor.RED + "You don't have enough gold!");
						}
					}
				}
				else if(lines[1].contains("Random") && lines[2].contains("Location"))
				{
					Skyrim.randomLocation(e.getPlayer());
					e.setCancelled(true);
				}
			}
		}
	}
}

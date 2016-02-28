package com.lwer0.playreward;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin implements Listener {
	
	
	
	public static Economy econ = null;
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	public void onEnable() {
		this.getLogger().info("Plugin loaded correctly!");
		if (!this.getDataFolder().exists()) {
			this.getDataFolder().mkdirs();
		}
		File file = new File(this.getDataFolder(), "config.yml");
		if (!file.exists()) {
			this.getLogger().info("Config.yml not found, creating!");
			saveDefaultConfig();
		} else {
			this.getLogger().info("Config.yml found, loading it!");
		}
	}
	public void onDisable() {
		this.getLogger().info("Plugin loaded correctly!");
	}
	
	public static String color(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public boolean onCommand (CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("killrewards")) {
			if (args[0].equalsIgnoreCase("reload")) {
				this.reloadConfig();
			} else if (args.length == 0) {
				sender.sendMessage(ChatColor.GREEN + "-------------------------------------");
				sender.sendMessage(ChatColor.YELLOW + "/killewards reload " + ChatColor.RED + "- " + ChatColor.YELLOW + "Reload the config.");
				sender.sendMessage(ChatColor.GREEN + "-------------------------------------");
			}
		}
		return true;
	}
	
	public void PDeath(PlayerDeathEvent event) {
		Player p = (Player) event.getEntity();
		if (event.getEntity().getKiller() instanceof Player) {
			String kUUID = event.getEntity().getKiller().getUniqueId().toString();
			String pUUID = event.getEntity().getUniqueId().toString();
			int money = this.getConfig().getInt("KillRewards.Economy.Money");
			int kills = this.getConfig().getInt("Players." + kUUID + ".kills");
			int deaths = this.getConfig().getInt("Players." + pUUID + ".deaths");
			this.getConfig().set("Players." + kUUID + ".kills", +1);
			this.getConfig().set("Players." + pUUID + ".deaths", +1);
			saveConfig();
			}
			if (!event.getEntity().getKiller().hasPermission("killrewards.reward")) {
				return;
			} else {
				if (this.getConfig().getBoolean("KillRewards.MultiGroup")) {
					if (event.getEntity().getKiller().hasPermission("killrewards.notify")) {
						if (event.getEntity().getKiller().hasPermission("killrewards.notify." + this.getConfig().getString("KillRewards.Message.Permissions.Perm1"))) {
							event.getEntity().getKiller().sendMessage(color(this.getConfig().getString("KillRewards.Message.Perm1")));
							if (this.getConfig().getBoolean("KillRewards.Economy.Enabled")) {
								econ.depositPlayer(event.getEntity().getKiller(), this.getConfig().getInt("KillRewards.Economy.MultiGroup.Money.Perm1"));
						}
						else if (event.getEntity().getKiller().hasPermission("killrewards.notify." + this.getConfig().getString("KillRewards.Permissions.Perm2"))) {
							event.getEntity().getKiller().sendMessage(color(this.getConfig().getString("KillRewards.Message.Perm2")));
							if (this.getConfig().getBoolean("KillRewards.Economy.Enabled")) {
								econ.depositPlayer(event.getEntity().getKiller(), this.getConfig().getInt("KillRewards.Economy.MultiGroup.Money.Perm2"));
						}
						else if (event.getEntity().getKiller().hasPermission("killrewards.notify." + this.getConfig().getString("KillRewards.Permissions.Perm3"))) {
							event.getEntity().getKiller().sendMessage(color(this.getConfig().getString("KillRewards.Message.Perm3")));
							if (this.getConfig().getBoolean("KillRewards.Economy.Enabled")) {
								econ.depositPlayer(event.getEntity().getKiller(), this.getConfig().getInt("KillRewards.Economy.MultiGroup.Money.Perm3"));
						}
						else if (event.getEntity().getKiller().hasPermission("killrewards.notify." + this.getConfig().getString("KillRewards.Permissions.Perm4"))) {
							event.getEntity().getKiller().sendMessage(color(this.getConfig().getString("KillRewards.Message.Perm4")));
							if (this.getConfig().getBoolean("KillRewards.Economy.Enabled")) {
								econ.depositPlayer(event.getEntity().getKiller(), this.getConfig().getInt("KillRewards.Economy.MultiGroup.Money.Perm4"));
						}
					}
				} else {
					event.getEntity().getKiller().sendMessage(color(this.getConfig().getString("KillRewards.Message")));
					if (this.getConfig().getBoolean("KillRewards.Economy.Enabled")) {
						econ.depositPlayer(event.getEntity().getKiller(), this.getConfig().getInt("KillRewards.Economy.Money"));
				}
			}
		}
}
					}
				}
			}
	}
}
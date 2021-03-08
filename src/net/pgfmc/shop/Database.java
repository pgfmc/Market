package net.pgfmc.shop;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Database {
	
	public static void save(Player p, ItemStack item, int cost)
	{
		File file = new File(Main.plugin.getDataFolder() + File.separator + "database.yml"); // Creates a File object
		FileConfiguration database = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
		
		database.set("", Main.listings);
	}
	
	public static void load()
	{
		File file = new File(Main.plugin.getDataFolder() + File.separator + "database.yml"); // Creates a File object
		FileConfiguration database = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
		
		
	}

}

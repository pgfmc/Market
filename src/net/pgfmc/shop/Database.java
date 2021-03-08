package net.pgfmc.shop;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Database {
	
	public static void save(Player p, ItemStack item, int cost, FileConfiguration db, File file, List<List<Object>> listing)
	{
		
		db.set("listings", listing);
		
		try {
			db.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void load()
	{
		
		
	}

}

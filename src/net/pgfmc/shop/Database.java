package net.pgfmc.shop;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Database {
	
	public static void save(Player p, ItemStack item, int cost, FileConfiguration db, File file, List<List<Object>> listings)
	{
		
		db.set("listings", listings);
		
		try {
			db.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<List<Object>> load(FileConfiguration db, File file)
	{
		return (List<List<Object>>) (db.get("listings"));	
	}

}

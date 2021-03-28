package net.pgfmc.shop.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.geysermc.connector.common.ChatColor;

// Written by CrimsonDart

// this code is to make it to where one can open (right click) a special book to bring them to the shops interface
// this in an attempt to make the PGF experience more intuitive, and use less commands
// the special book is created by signing a Writable book with the title "shop" (not case sensitive) and then the plugin takes care of the rest

public class PlayerEvents implements Listener{
	
	@EventHandler
	public void editBook(PlayerEditBookEvent e) { // -------------------- sets a book to be able to be used by the PGF shops plugin
		if (e.getNewBookMeta().getTitle().toLowerCase() == "shop") {
			
			BookMeta bookMeta = e.getNewBookMeta();
			
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.AQUA + "Use this book to access the PGF shop!");
			lore.add("");
			lore.add(ChatColor.AQUA + "Use the shop to trade or buy items");
			lore.add(ChatColor.AQUA + "from other players!");
			lore.add("");
			lore.add(ChatColor.AQUA + "You can put your items on sale, or");
			lore.add(ChatColor.AQUA + "auction them!");
			
			List<String> pages = new ArrayList<String>();
			pages.add("");
			
			bookMeta.setTitle(ChatColor.UNDERLINE + ChatColor.BOLD + ChatColor.YELLOW + "- SHOP -");
			bookMeta.setAuthor("PGF");
			bookMeta.setLore(lore);
			bookMeta.setPages(pages);
			bookMeta.setDisplayName(ChatColor.UNDERLINE + ChatColor.BOLD + ChatColor.YELLOW + "- SHOP -");
			
			e.setSigning(true);
			e.setNewBookMeta(bookMeta);
		}
	}
	
	public void clickAirBros(PlayerInteractEvent e) { // ----------------- if the player opens the SHOP book, then it pulls up the SHOP inventory/interface
		
		if (e.hasItem() && e.getMaterial() == Material.WRITTEN_BOOK && e.getItem().getItemMeta().getDisplayName() == ChatColor.UNDERLINE + ChatColor.BOLD + ChatColor.YELLOW + "- SHOP -") {
			e.setCancelled(true);
			
			// insert the code for pulling up the shop inventory here :)
					
		}
	}
}

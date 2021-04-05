package net.pgfmc.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Listing {
	
	private transient static List<Listing> instances = new ArrayList<Listing>();

	public enum listingType {
		LISTING,
		TRADEOFFER
	}
	
	listingType type;
	ItemStack itemBeingSold;
	ItemStack tradeItem;
	UUID playerUuid;
	
	
	// ------------------------------------------------------------------------ Constructors
	
	public Listing(OfflinePlayer seller, ItemStack itemBeingSold, ItemStack tradeItem, listingType type) {
		this.type = type;
		this.itemBeingSold = itemBeingSold;
		
		this.playerUuid = seller.getUniqueId();
		
		if (tradeItem == null) {
			tradeItem = new ItemStack(Material.DIAMOND, 1);
		}
		this.tradeItem = tradeItem;
		
		instances.add(this);
		Listing.saveListings();
	}
	
	// ------------------------------------------------------------------------ Save and Load
	
	public static void saveListings() {
		Database.save(instances);
	}
	
	public static void loadListings() {
		instances.clear();
		Database.load();
	}
	
	// ------------------------------------------------------------------------ Get Listings (instances)
	
	public static List<Listing> getListings() {
		return instances;
	}
	
	// ------------------------------------------------------------------------ Get Listing Type
	
	public listingType getType() {
		return type;
	}
	
	// ------------------------------------------------------------------------ Get itemBeingSold / icon
	
	public ItemStack getItem() {
		return itemBeingSold;
	}
	
	// ------------------------------------------------------------------------ Get Seller
	
	public OfflinePlayer getPlayer() {
		return Bukkit.getOfflinePlayer(playerUuid);
	}
	
	// ------------------------------------------------------------------------ Get Price
	
	public String getPrice() { // returns a string representation of the cost of this listing
		return Main.makePlural(tradeItem);
	}
	
	// ------------------------------------------------------------------------ Get TradeItem
	
	public ItemStack getTrade() {
		return tradeItem;
	}
	
	// ------------------------------------------------------------------------ Confirm / Buy
	
	public void purchase(Player player) {
		
	}
	
	public void deleteListing() {
		instances.remove(this);
		System.gc();
	}
}

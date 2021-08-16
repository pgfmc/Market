package net.pgfmc.shop.inventories;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.pgfmc.shop.Database;
import net.pgfmc.shop.Main;

public class Notifications implements InventoryHolder {
	
	Inventory inv;
	Player player;
	List<ItemStack> items = new LinkedList<ItemStack>();
	List<Notifications> instances = new ArrayList<Notifications>();
	int currentPage = 1;
	int pages;
	
	public Notifications(Player player) {
		inv = Bukkit.createInventory(this, 27, "My Money");
		items = Database.getPlayerMoney(player);
		this.player = player;
		instances.add(this);
		pages = ((int) Math.ceil(items.size() / 21.0));
		if (pages < 1) {
			pages = 1;
		}
		
		inv.setItem(0, Main.createItem("§eBack", Material.FEATHER));
		
		goToPage(currentPage);
	}
	
	public void goToPage(int page) { // interprets the listing data, and adds each listing to the interface
		
		for (int index = 0; index <= 20; index++) {
			ItemStack itemStack = null;
			try {
					itemStack = items.get(index);
					ItemMeta itemMeta = itemStack.getItemMeta();
					itemMeta.setLore(new ArrayList<String>());
					itemStack.setItemMeta(itemMeta);
					
			} catch (Exception e) {
			} finally {
				
				switch(index) {
				case 0: inv.setItem(2, itemStack);
				case 1: inv.setItem(3, itemStack);
				case 2: inv.setItem(4, itemStack);
				case 3: inv.setItem(5, itemStack);
				case 4: inv.setItem(6, itemStack);
				case 5: inv.setItem(7, itemStack);
				case 6: inv.setItem(8, itemStack);
				case 7: inv.setItem(11, itemStack);
				case 8: inv.setItem(12, itemStack);
				case 9: inv.setItem(13, itemStack);
				case 10: inv.setItem(14, itemStack);
				case 11: inv.setItem(15, itemStack);
				case 12: inv.setItem(16, itemStack);
				case 13: inv.setItem(17, itemStack);
				case 14: inv.setItem(20, itemStack);
				case 15: inv.setItem(21, itemStack);
				case 16: inv.setItem(22, itemStack);
				case 17: inv.setItem(23, itemStack);
				case 18: inv.setItem(24, itemStack);
				case 19: inv.setItem(25, itemStack);
				case 20: inv.setItem(26, itemStack);
				}
			}
		}
		
		if (currentPage != pages) {
			inv.setItem(18, Main.createItem("§aNext Page", Material.ARROW));
		} else {
			inv.clear(18);
		}
		
		if (currentPage != 1) {
			inv.setItem(9, Main.createItem("§aPrevious Page", Material.IRON_HOE));
		} else {
			inv.clear(9);
		}
	}
	
	public void flipPage(boolean advance) { // flips the page forwards/backwards (true/false)
		
		if (advance) {
			currentPage++;
			if ((double) currentPage > pages + 1.0) {
				currentPage = (int) Math.ceil(pages);
			}
			goToPage(currentPage);
			
		} else {
			currentPage--;
			if ((double) currentPage < 1) {
				currentPage = 1;
			}
			goToPage(currentPage);
		}
	}
	
	public int getPage() {
		return currentPage;
	}
	
	public void itemTaken(int slot) {
		
		if (slot >= 2 && slot <= 8) {
			items.set(slot - 2 + ((currentPage - 1) * 21), null);
			
		} else if (slot >= 11 && slot <= 17) {
			items.set(slot - 4 + ((currentPage - 1) * 21), null);
			
		} else if (slot >= 20 && slot <= 26) {
			items.set(slot - 6 + ((currentPage - 1) * 21), null);
		}
	}
	
	public void inventoryClose() {
		
		List<ItemStack> lizst = new ArrayList<ItemStack>();
		
		if (items != null) {
			
			for (ItemStack item : items) {
				if (item != null) {
					lizst.add(item);
				}
			}
		}
		Database.setPlayerMoney(player, lizst);
	}

	@Override
	public Inventory getInventory() {
		return inv;
	}

}

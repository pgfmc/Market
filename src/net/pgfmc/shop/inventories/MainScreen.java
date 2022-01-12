package net.pgfmc.shop.Inventories;

import java.util.List;

import net.pgfmc.core.inventoryAPI.ListInventory;
import net.pgfmc.core.inventoryAPI.extra.Button;
import net.pgfmc.core.inventoryAPI.extra.SizeData;
import net.pgfmc.core.playerdataAPI.PlayerData;
import net.pgfmc.shop.Listing;

public class MainScreen extends ListInventory {

    PlayerData pd;


    public MainScreen(PlayerData pd) {
        super(SizeData.BIG , "Market");

        this.pd = pd;
    }

    @Override
    public List<Button> load() {
        return Listing.getButtons(pd);
    }    
}

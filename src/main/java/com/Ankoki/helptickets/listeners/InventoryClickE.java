package com.Ankoki.helptickets.listeners;

import com.Ankoki.helptickets.inventories.TicketInventory;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickE implements Listener {

    TicketInventory inventoryClass = new TicketInventory();

    public void invClick(InventoryClickEvent e) {
        if (e.getInventory() != inventoryClass.ticketInv) return;
    }
}

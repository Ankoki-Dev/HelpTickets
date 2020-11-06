package com.Ankoki.helptickets.listeners;

import com.Ankoki.helptickets.inventories.TicketInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryClickE implements Listener {

    @EventHandler
    public void invClick(InventoryClickEvent e) {
        if (e.getInventory() != TicketInventory.ticketInv) return;
        if (e.getCurrentItem() == null) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void invDrag(InventoryDragEvent e) {
        if (e.getInventory() != TicketInventory.ticketInv) return;
        e.setCancelled(true);
    }
}

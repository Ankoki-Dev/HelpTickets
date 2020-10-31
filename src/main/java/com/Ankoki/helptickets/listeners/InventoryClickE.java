package com.Ankoki.helptickets.listeners;

import com.Ankoki.helptickets.inventories.TicketInventory;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryClickE implements Listener {

    TicketInventory inventoryClass = new TicketInventory();

    public void invClick(InventoryClickEvent e) {
        if (e.getInventory() != inventoryClass.ticketInv) return;
        if (e.getCurrentItem().getType() == Material.AIR) return;
        e.setCancelled(true);
    }

    public void invDrag(InventoryDragEvent e) {
        if (e.getInventory() != inventoryClass.ticketInv) return;
        e.setCancelled(true);
    }
}

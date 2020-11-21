package com.Ankoki.helptickets.listeners;

import com.Ankoki.helptickets.api.events.TicketDeleteEvent;
import com.Ankoki.helptickets.commands.TicketCMD;
import com.Ankoki.helptickets.inventories.TicketInventory;
import com.Ankoki.helptickets.main.HelpTickets;
import com.Ankoki.helptickets.files.Lang;
import com.Ankoki.helptickets.utils.Priority;
import com.Ankoki.helptickets.utils.Ticket;
import com.Ankoki.helptickets.utils.Utils;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickE implements Listener {

    @EventHandler
    public void invClick(InventoryClickEvent e) {
        if (e.getInventory() != TicketInventory.ticketInv) return;
        if (e.getCurrentItem() == null) return;
        if (!(e.getWhoClicked() instanceof Player)) return;
        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        NBTItem clickedNBT = new NBTItem(clickedItem);

        if (clickedItem.getType() == Material.PAPER) {
            return;
        }

        Ticket ticket = HelpTickets.ticketIDs.get(clickedNBT.getUUID("TicketID"));



        if (e.getClick() == ClickType.LEFT) {
            if (!p.hasPermission("helptickets.solve")) {
                p.sendMessage(Utils.cC(Lang.PREFIX + Lang.CLICK_NOPERM_MESSAGE));
                return;
            }
            ticket.setSolved(!ticket.isSolved());
            if (ticket.isSolved()) {
                p.sendMessage(Utils.cC(Lang.PREFIX + Lang.CLICK_SOLVETICKET_MESSAGE));
            } else {
                p.sendMessage(Utils.cC(Lang.PREFIX + Lang.CLICK_OPENTICKET_MESSAGE));
            }
        }

        else if (e.getClick() == ClickType.MIDDLE) {
            if (!p.hasPermission("helptickets.delete")) {
                p.sendMessage(Utils.cC(Lang.PREFIX + Lang.CMD_NOPERM_MESSAGE));
                return;
            }
            TicketDeleteEvent event = new TicketDeleteEvent(p, ticket);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                p.sendMessage(Lang.PREFIX + Lang.TICKET_DELETE_CANCELLED);
                return;
            }
            HelpTickets.ticketIDs.remove(ticket.getTicketID());
            TicketCMD.tickets.remove(ticket);
            TicketCMD.playersTicket.remove(ticket.getPlayerID());
            ticket = null;
            p.sendMessage(Utils.cC(Lang.PREFIX + Lang.CLICK_REMOVETICKET_MESSAGE));
        }

        else if (e.getClick() == ClickType.RIGHT) {
            if (!p.hasPermission("helptickets.prioritise")) {
                p.sendMessage(Utils.cC(Lang.PREFIX + Lang.CLICK_NOPERM_MESSAGE));
                return;
            }
            switch (ticket.getPriority()) {
                case UNDETERMINED:
                    ticket.setPriority(Priority.LOW);
                    break;
                case LOW:
                    ticket.setPriority(Priority.MEDIUM);
                    break;
                case MEDIUM:
                    ticket.setPriority(Priority.HIGH);
                    break;
                case HIGH:
                    ticket.setPriority(Priority.URGENT);
                    break;
                case URGENT:
                    ticket.setPriority(Priority.UNDETERMINED);
                    break;
            }
            p.sendMessage(Utils.cC(Lang.PREFIX + Lang.CLICK_UPDATEDPRIORITY_MESSAGE));
        }
        TicketInventory.openInventory(p);
    }

    @EventHandler
    public void invDrag(InventoryDragEvent e) {
        if (e.getInventory() != TicketInventory.ticketInv) return;
        e.setCancelled(true);
    }
}

package com.Ankoki.helptickets.inventories;

import com.Ankoki.helptickets.commands.TicketCMD;
import com.Ankoki.helptickets.utils.Lang;
import com.Ankoki.helptickets.utils.Ticket;
import com.Ankoki.helptickets.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TicketInventory {

    public static ItemStack limeGlass;
    public static ItemStack redGlass;
    public static Inventory ticketInv = Bukkit.createInventory(null, 54, Utils.cC("&2Help&aTickets"));

    public static void openInventory(Player p) {
        try {
            formatTickets();
            p.openInventory(ticketInv);
        } catch (NullPointerException e) {
            p.sendMessage(Lang.PREFIX + " " + Lang.CMD_NOTICKETS_MESSAGE);
        }
    }

    private static void formatTickets() {
        if (TicketCMD.tickets.size() == 0) {
            throw new NullPointerException();
        } else if (TicketCMD.tickets.size() > 54) {
            throw new NullPointerException();
        }
        limeGlass = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        redGlass = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);

        int i = 0;
        for (Ticket t : TicketCMD.tickets) {
            if (t.isSolved()) {
                ticketInv.setItem(i, guiITEM(limeGlass, t.getName(), t.getReason(), t));
            } else {
                ticketInv.setItem(i, guiITEM(redGlass, t.getName(), t.getReason(), t));
            }
            i++;
        }
    }

    protected static ItemStack guiITEM(ItemStack item, String displayName, String lore, Ticket t) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.cC("&c" + displayName));
        List<String> newLore = Utils.readableLore(lore);
        newLore.add(" ");
        newLore.add(Utils.cC("&aSolved: " + t.getSolvedYN()));
        newLore.add(" ");
        newLore.add(Utils.cC("&aReporter Online: &7" + t.reporterOnline()));
        newLore.add(" ");
        newLore.add(Utils.cC("&aPriority: &7" + t.getPriority().toString()));
        newLore.add(Utils.cC("&9Left click to solve this ticket."));
        newLore.add(Utils.cC("&9MMB to delete this ticket."));
        newLore.add(Utils.cC("&9Right click to up the priority."));
        meta.setLore(Utils.cCList(newLore));
        item.setItemMeta(meta);
        return item;
    }
}

package com.Ankoki.helptickets.inventories;

import com.Ankoki.helptickets.commands.TicketCMD;
import com.Ankoki.helptickets.files.Lang;
import com.Ankoki.helptickets.utils.Ticket;
import com.Ankoki.helptickets.utils.Utils;
import com.google.common.collect.Lists;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class TicketInventory {

    public static ItemStack limeGlass;
    public static ItemStack redGlass;
    public static Inventory ticketInv = Bukkit.createInventory(null, 54, Utils.cC("&3Help&bTickets"));

    public static void openInventory(Player p, int page) {
        try {
            p.closeInventory();
            formatTickets(ticketInv, page);
            p.openInventory(ticketInv);
        } catch (NullPointerException e) {
            p.sendMessage(Utils.cC(Lang.PREFIX + Lang.CMD_NOTICKETS_MESSAGE));
        }
    }

    private static void formatTickets(Inventory inv, int page) {
        if (TicketCMD.tickets.size() == 0) {
            throw new NullPointerException();
        }
        limeGlass = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        redGlass = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);

        TicketCMD.tickets.sort((o1, o2) -> Utils.getUncolouredItemName(o1).compareToIgnoreCase(Utils.getUncolouredItemName(o2)));

        final List<List<Ticket>> pages = Lists.partition(TicketCMD.tickets, 53);

        List<Ticket> content;

        try {
            content = pages.get(page);
        } catch (IndexOutOfBoundsException ex) {
            content = pages.get(0);
            page = 0;
        }

        int i = 0;
        for (Ticket t : content) {
            if (t.isSolved()) {
                inv.setItem(i, guiITEM(limeGlass, t.getName(), t.getReason(), t, 0));
            } else {
                inv.setItem(i, guiITEM(redGlass, t.getName(), t.getReason(), t, 0));
            }
            i++;
        }
        inv.setItem(53, guiITEM(new ItemStack(Material.PAPER), Utils.cC("&2Next Page"), Utils.cC("&9Click this paper to view the next page!"), null, page + 1));
    }

    protected static ItemStack guiITEM(ItemStack item, String displayName, String lore, @Nullable Ticket ticket, int page) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.cC("&c" + displayName));
        List<String> newLore = Utils.readableLore(lore);
        if (ticket == null) {
            meta.setLore(Arrays.asList(Utils.cC(lore)));
            NBTItem itemNBT = new NBTItem(item);
            itemNBT.addCompound("NextPage");
            itemNBT.setInteger("NextPage", page);
            ItemStack pageItem = itemNBT.getItem();
            pageItem.setItemMeta(meta);
            return pageItem;
        }
        newLore.add(" ");
        newLore.add(Utils.cC("&aSolved: " + ticket.getSolvedYN()));
        newLore.add(" ");
        newLore.add(Utils.cC("&aReporter Online: &7" + ticket.reporterOnline()));
        newLore.add(" ");
        newLore.add(Utils.cC("&aPriority: &7" + ticket.getPriority().toString()));
        newLore.add(Utils.cC("&9Left click to solve this ticket."));
        newLore.add(Utils.cC("&9MMB to delete this ticket."));
        newLore.add(Utils.cC("&9Right click to up the priority."));
        meta.setLore(Utils.cCList(newLore));
        item.setItemMeta(meta);
        NBTItem itemNBT = new NBTItem(item);
        itemNBT.addCompound("TicketID");
        itemNBT.setUUID("TicketID", ticket.getTicketID());
        item = itemNBT.getItem();
        return item;
    }
}

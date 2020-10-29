package com.Ankoki.helptickets.inventories;

import com.Ankoki.helptickets.commands.TicketCMD;
import com.Ankoki.helptickets.main.HelpTickets;
import com.Ankoki.helptickets.utils.Lang;
import com.Ankoki.helptickets.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class TicketInventory {

    public ItemStack limeGlass;
    public ItemStack redGlass;
    public Inventory ticketInv = Bukkit.createInventory(null, 54, Utils.cC("&2Help&aTickets"));

    public void openInventory(Player p) {
        try {
            formatTickets();
        } catch (NullPointerException e) {
            p.sendMessage(Lang.PREFIX + " " + Lang.CMD_NOTICKETS_MESSAGE);
        }
        p.openInventory(ticketInv);
    }

    private void formatTickets() {
        if (TicketCMD.tickets.size() == 0) {
            throw new NullPointerException();
        }
        if (HelpTickets.plugin().serverVersion() > 12) {
            limeGlass = new ItemStack(Material.LEGACY_STAINED_GLASS_PANE, 1, (short) 5);
            redGlass = new ItemStack(Material.LEGACY_STAINED_GLASS_PANE, 1, (short) 14);
        } else {
            limeGlass = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
            redGlass = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        }
    }

    protected ItemStack guiITEM(ItemStack item, String displayName, String[] lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
}

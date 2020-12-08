package com.Ankoki.helptickets.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String cC(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> cCList(List<String> list) {
        List<String> newList = new ArrayList<String>();
        for (String s : list) {
            newList.add(Utils.cC(s));
        }
        return newList;
    }

    public static List<String> readableLore(String lore) {
        String[] splitLore;
        splitLore = lore.split(" ");
        List<String> readLore = new ArrayList<String>();
        int i = 0;
        readLore.add(" ");
        String newline = Utils.cC("&aReason:&7");
        for (String s : splitLore) {
            i++;
            if (i != 5) {
                newline += s + " ";
            }
            if (i == 5) {
                newline += s;
                readLore.add(ChatColor.translateAlternateColorCodes('&', "&7") + newline);
                newline = "";
                i = 0;
            }
        }
        if (newline != "") {
            readLore.add(ChatColor.translateAlternateColorCodes('&', "&7") + newline);
        }
        return readLore;
    }

    public static void formattedTicket(Player p, Ticket t, int i, boolean isLast) {
        p.sendMessage(Utils.cC("&b*********************&3HelpTickets&b*********************"));
        p.sendMessage(Utils.cC("&cTicket " + i + "&c:"));
        p.sendMessage(Utils.cC("&7Reporter: " + t.getName()));
        p.sendMessage(Utils.cC("&7Reason: " + t.getReason()));
        p.sendMessage(Utils.cC("&7Solved: " + t.getSolvedYN()));
        p.sendMessage(Utils.cC("&7Priority: &c" + t.getPriority()));
        if (isLast) {
            p.sendMessage(Utils.cC("&b*********************&3HelpTickets&b*********************"));
        }
    }

    public static String getUncolouredItemName(Ticket item) {
        return item.getName();
    }

}

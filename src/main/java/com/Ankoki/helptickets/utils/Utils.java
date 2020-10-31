package com.Ankoki.helptickets.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {

    public static String cC(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void formattedTicket(Player p, Ticket t, int i, boolean isLast) {
        p.sendMessage(Utils.cC("&a**************************&2HelpTickets&a************************"));
        p.sendMessage(Utils.cC("&cTicket " + i + "&c:"));
        p.sendMessage(Utils.cC("&7Reporter: " + t.getName()));
        p.sendMessage(Utils.cC("&7Reason: " + t.getReason()));
        p.sendMessage(Utils.cC("&7Solved: " + t.getSolvedYN()));
        p.sendMessage(Utils.cC("&7Priority: &c" + t.getPriority()));
        if (isLast) {
            p.sendMessage(Utils.cC("&a**************************&2HelpTickets&a************************"));
        }
    }

}

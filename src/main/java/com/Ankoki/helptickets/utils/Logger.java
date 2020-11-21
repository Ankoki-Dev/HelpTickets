package com.Ankoki.helptickets.utils;

import com.Ankoki.helptickets.main.HelpTickets;

public class Logger {

    HelpTickets plugin;

    public Logger(HelpTickets plugin) {
        this.plugin = plugin;
    }

    public void send(String s){
        plugin.getLogger().info(Utils.cC("&8[&3Help&bTickets&8] &7" + s));
    }

    public void err(String s) {
        plugin.getLogger().info(Utils.cC("&c" + s));
    }
}

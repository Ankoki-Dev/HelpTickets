package com.Ankoki.helptickets.main;

import com.Ankoki.helptickets.commands.TicketCMD;
import com.Ankoki.helptickets.listeners.InventoryClickE;
import com.Ankoki.helptickets.utils.Lang;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings({"unused", "redundant"})
public class HelpTickets extends JavaPlugin {

    private static HelpTickets plugin;

    public Lang lang = null;

    @Override
    public void onEnable() {
        System.out.println(this.getServer().getBukkitVersion());
        plugin = this;
        System.out.println("[HT] Fetching commands...");
        commands();
        System.out.println("[HT] Registering listeners...");
        listeners();
        System.out.println("[HT] Grabbing lang.yml...");
        this.lang = new Lang(this);
        System.out.println("[HT] HelpTickets is enabled.");
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    //STFU I HAVENT DONE THIS YET
    private void listeners() {
        this.getServer().getPluginManager().registerEvents(new InventoryClickE(), this);
    }

    private void commands() {
        this.getServer().getPluginCommand("helptickets").setExecutor(new TicketCMD());
    }

    public static HelpTickets plugin() {
        return plugin;
    }

    public int serverVersion() {
        String[] serv = this.getServer().getBukkitVersion().split(".");
        return Integer.parseInt(serv[1]);
    }
}

package com.Ankoki.helptickets.main;

import com.Ankoki.helptickets.commands.TicketCMD;
import com.Ankoki.helptickets.listeners.InventoryClickE;
import com.Ankoki.helptickets.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


@SuppressWarnings({"unused", "redundant"})
public class HelpTickets extends JavaPlugin {

    private static HelpTickets plugin;

    public Lang lang = null;

    @Override
    public void onEnable() {
        plugin = this;
        System.out.println("[HT] Checking server version...");
        if (getVersion() <= 12) {
            System.out.println("## FATAL ERROR");
            System.out.println("##");
            System.out.println("## This server version is unsupported by HelpTickets");
            System.out.println("## There will be a Legacy release of this plugin shortly.");
            System.out.println("## ");
            System.out.println("## Download link to legacy:");
            System.out.println("## <LEGACY IS NOT OUT YET. CHECK ANOTHER TIME>");
            System.out.println("## ");
            System.out.println("## FATAL ERROR END");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
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

    private void listeners() {
        this.getServer().getPluginManager().registerEvents(new InventoryClickE(), this);
    }

    private void commands() {
        this.getServer().getPluginCommand("helptickets").setExecutor(new TicketCMD());
    }

    public static HelpTickets plugin() {
        return plugin;
    }

    public enum Version {
        v1_8_R1,
        v1_9_R1,
        v1_10_R1,
        v1_11_R1,
        v1_12_R1,
        v1_12_R2,
        v1_13_R1,
        v1_14_R1,
        v1_15_R1,
        v1_16_R1,
        v1_16_R2,
    }

    public int getVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);
        String[] str = Version.valueOf(version).toString().split("_");
        return Integer.parseInt(str[1]);
    }
}

package com.Ankoki.helptickets.main;

import com.Ankoki.helptickets.commands.TicketCMD;
import com.Ankoki.helptickets.files.Config;
import com.Ankoki.helptickets.listeners.InventoryClickE;
import com.Ankoki.helptickets.files.Lang;
import com.Ankoki.helptickets.utils.Logger;
import com.Ankoki.helptickets.utils.Ticket;
import com.Ankoki.helptickets.utils.Version;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;


@SuppressWarnings({"unused", "redundant"})
public class HelpTickets extends JavaPlugin {

    public static HashMap<UUID, Ticket> ticketIDs = new HashMap<UUID, Ticket>();

    private static HelpTickets plugin;

    public Lang lang = null;
    public Config config = null;
    public Logger logger = null;

    private PluginManager pm;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        plugin = this;
        pm = this.getServer().getPluginManager();
        logger = new Logger(this);
        logger.send("Checking server version...");
        if (this.getVersion() == 0) {
            return;
        }
        logger.send("Found server version 1." + this.getVersion()  + "!");
        if (this.getVersion() <= 12) {
            logger.err("## FATAL ERROR");
            logger.err("##");
            logger.err("## This server version is unsupported by HelpTickets");
            logger.err("## There will be a legacy release of this plugin,");
            logger.err("## or support will be added in the near future.");
            logger.err("## ");
            logger.err("## FATAL ERROR END");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        logger.send("Hooking into bStats...");
        try {
            if (Class.forName("org.bstats.bukkit.Metrics") != null) {
                int pluginId = 9467;
                Metrics metrics = new Metrics(this, pluginId);
                logger.send("bStats enabled. Thank you for helping!");
            }
        } catch (ClassNotFoundException e) {
            logger.send("bStats not found, integration disabled.");
        }
        finally {
            logger.send("Fetching commands...");
            this.commands();
            logger.send("Registering listeners...");
            this.listeners();
            logger.send("Grabbing files...");
            this.lang = new Lang(this);
            this.config = new Config(this);
            logger.send(String.format("%s v%s was enabled in %.2f seconds!", getDescription().getName(), getDescription().getVersion(), (float) System.currentTimeMillis() - start));
            logger.send("Keep up to date on our github! &bhttps://www.github.com/ankoki-dev/HelpTickets");
        }
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    private void listeners() {
        pm.registerEvents(new InventoryClickE(), this);
    }

    private void commands() {
        this.getServer().getPluginCommand("helptickets").setExecutor(new TicketCMD());
    }

    public static HelpTickets plugin() {
        return plugin;
    }

    @Nullable
    public int getVersion() {
        try {
            String packageName = Bukkit.getServer().getClass().getPackage().getName();
            String version = packageName.substring(packageName.lastIndexOf('.') + 1);
            String[] str = Version.valueOf(version).toString().split("_");
            return Integer.parseInt(str[1]);
        } catch (NullPointerException ex) {
            logger.err("## We couldn't find any version linked to your minecraft");
            logger.err("## You are on a version BELOW 1.8, or an unsupported version.");
            logger.err("## We are going to disable for now, if you are on a newer version");
            logger.err("## that isn't supported, let me know at Ankoki#0001 on discord.");
            pm.disablePlugin(plugin);
        }
        return 0;
    }
}

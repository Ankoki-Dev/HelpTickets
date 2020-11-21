package com.Ankoki.helptickets.files;

import com.Ankoki.helptickets.main.HelpTickets;
import com.Ankoki.helptickets.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config {

    public static int MAX_TICKETS;

    HelpTickets plugin;

    private FileConfiguration config;
    private File cfile;

    public Config(HelpTickets plugin) {
        this.plugin = plugin;
        reloadConfig();
        matchConfigFile();
    }

    public void reloadConfig() {
        if (cfile == null) {
            cfile = new File(plugin.getDataFolder(), "lang.yml");
        }
        if (!cfile.exists()) {
            plugin.saveResource("lang.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(cfile);
        matchConfigFile();
        loadFile();
    }

    private void matchConfigFile() {
        try {
            boolean hasUpdated = false;
            InputStream stream = plugin.getResource(cfile.getName());
            assert stream != null;
            InputStreamReader is = new InputStreamReader(stream);
            YamlConfiguration defLand = YamlConfiguration.loadConfiguration(is);
            for (String key : defLand.getConfigurationSection("").getKeys(true)) {
                if (!config.contains(key)) {
                    config.set(key, defLand.get(key));
                    hasUpdated = true;
                }
            }
            for (String key : config.getConfigurationSection("").getKeys(true)) {
                if (!defLand.contains(key)) {
                    config.set(key, null);
                    hasUpdated = true;
                }
            }
            if (hasUpdated)
                config.save(cfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFile() {
        MAX_TICKETS = config.getInt("max-tickets");
    }
}

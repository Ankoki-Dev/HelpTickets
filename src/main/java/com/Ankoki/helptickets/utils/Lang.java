package com.Ankoki.helptickets.utils;

import com.Ankoki.helptickets.main.HelpTickets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Lang {

    //ty steel for letting me yoink codes from BN<3333 and shane for writing the base of that<3333

    public static String PREFIX;
    public static String CMD_CREATE_MESSAGE;
    public static String CMD_REVOKE_MESSAGE;
    public static String CMD_CLEAR_MESSAGE;
    public static String CMD_NOTICKETS_MESSAGE;
    public static String CMD_NOPERM_MESSAGE;
    public static String CLICK_NOPERM_MESSAGE;
    public static String CLICK_SOLVETICKET_MESSAGE;
    public static String CLICK_OPENTICKET_MESSAGE;
    public static String CLICK_REMOVETICKET_MESSAGE;
    public static String CLICK_UPDATEDPRIORITY_MESSAGE;
    public static String CMD_INVALID_USAGE_MESSAGE;
    public static String CMD_INVALID_USAGE_MESSAGE_ADMIN;
    public static List<String> CMD_HELP_MESSAGE = new ArrayList<String>();

    HelpTickets plugin;

    private FileConfiguration lang;
    private File lfile;

    public Lang(HelpTickets plugin) {
        this.plugin = plugin;
        reloadLang();
        matchLangFile();
    }

    public void reloadLang() {
        if (lfile == null) {
            lfile = new File(plugin.getDataFolder(), "lang.yml");
        }
        if (!lfile.exists()) {
            plugin.saveResource("lang.yml", false);
        }
        lang = YamlConfiguration.loadConfiguration(lfile);
        matchLangFile();
        loadFile();
    }

    private void matchLangFile() {
        try {
            boolean hasUpdated = false;
            InputStream stream = plugin.getResource(lfile.getName());
            assert stream != null;
            InputStreamReader is = new InputStreamReader(stream);
            YamlConfiguration defLand = YamlConfiguration.loadConfiguration(is);
            for (String key : defLand.getConfigurationSection("").getKeys(true)) {
                if (!lang.contains(key)) {
                    lang.set(key, defLand.get(key));
                    hasUpdated = true;
                }
            }
            for (String key : lang.getConfigurationSection("").getKeys(true)) {
                if (!defLand.contains(key)) {
                    lang.set(key, null);
                    hasUpdated = true;
                }
            }
            if (hasUpdated)
                lang.save(lfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFile() {
        PREFIX = Utils.cC(lang.getString("PREFIX"));
        CMD_CREATE_MESSAGE = Utils.cC(lang.getString("CMD_CREATE_MESSAGE"));
        CMD_REVOKE_MESSAGE = Utils.cC(lang.getString("CMD_REVOKE_MESSAGE"));
        CMD_CLEAR_MESSAGE = Utils.cC(lang.getString("CMD_CLEAR_MESSAGE"));
        CMD_NOPERM_MESSAGE = Utils.cC(lang.getString("CMD_NOPERM_MESSAGE"));
        CMD_NOTICKETS_MESSAGE = Utils.cC(lang.getString("CMD_NOTICKETS_MESSAGE"));
        CLICK_NOPERM_MESSAGE = Utils.cC(lang.getString("CLICK_NOPERM_MESSAGE"));
        CLICK_SOLVETICKET_MESSAGE = Utils.cC(lang.getString("CLICK_SOLVETICKET_MESSAGE"));
        CLICK_OPENTICKET_MESSAGE = Utils.cC(lang.getString("CLICK_OPENTICKET_MESSAGE"));
        CLICK_REMOVETICKET_MESSAGE = Utils.cC(lang.getString("CLICK_REMOVETICKET_MESSAGE"));
        CLICK_UPDATEDPRIORITY_MESSAGE= Utils.cC(lang.getString("CLICK_UPDATEDPRIORITY_MESSAGE"));
        CMD_INVALID_USAGE_MESSAGE = Utils.cC(lang.getString("CMD_INVALID_USAGE_MESSAGE"));
        CMD_INVALID_USAGE_MESSAGE_ADMIN = Utils.cC(lang.getString("CMD_INVALID_USAGE_MESSAGE_ADMIN"));
        CMD_HELP_MESSAGE = Utils.cCList(lang.getStringList("CMD_HELP_MESSAGE"));
    }
}

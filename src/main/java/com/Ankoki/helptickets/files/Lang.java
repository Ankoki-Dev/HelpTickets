package com.Ankoki.helptickets.files;

import com.Ankoki.helptickets.main.HelpTickets;
import com.Ankoki.helptickets.utils.Utils;
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
    public static String CREATE_CANCELLED;
    public static String REVOKE_CANCELLED;
    public static String TICKET_DELETE_CANCELLED;
    public static String CMD_CREATE_MESSAGE;
    public static String CMD_REVOKE_MESSAGE;
    public static String CMD_CLEAR_MESSAGE;
    public static String CMD_NOTICKET_MESSAGE;
    public static String CMD_NOTICKETS_MESSAGE;
    public static String CMD_NOPERM_MESSAGE;
    public static String CMD_MAXTICKETS_MESSAGE;
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
        PREFIX = Utils.cC(lang.getString("prefix") + " ");
        CREATE_CANCELLED = Utils.cC(lang.getString("create-cancelled"));
        REVOKE_CANCELLED = Utils.cC(lang.getString("revoke-cancelled"));
        TICKET_DELETE_CANCELLED = Utils.cC(lang.getString("delete-ticket-cancelled"));
        CMD_CREATE_MESSAGE = Utils.cC(lang.getString("cmd-create-message"));
        CMD_REVOKE_MESSAGE = Utils.cC(lang.getString("cmd-revoke-message"));
        CMD_CLEAR_MESSAGE = Utils.cC(lang.getString("cmd-clear-message"));
        CMD_NOPERM_MESSAGE = Utils.cC(lang.getString("cmd-noperm-message"));
        CMD_NOTICKET_MESSAGE = Utils.cC(lang.getString("cmd-noticket-message"));
        CMD_NOTICKETS_MESSAGE = Utils.cC(lang.getString("cmd-notickets-message"));
        CMD_MAXTICKETS_MESSAGE = Utils.cC(lang.getString("cmd-maxtickets-message"));
        CLICK_NOPERM_MESSAGE = Utils.cC(lang.getString("click-noperm-message"));
        CLICK_SOLVETICKET_MESSAGE = Utils.cC(lang.getString("click-solveticket-message"));
        CLICK_OPENTICKET_MESSAGE = Utils.cC(lang.getString("click-openticket-message"));
        CLICK_REMOVETICKET_MESSAGE = Utils.cC(lang.getString("click-removeticket-message"));
        CLICK_UPDATEDPRIORITY_MESSAGE= Utils.cC(lang.getString("click-updatedpriority-message"));
        CMD_INVALID_USAGE_MESSAGE = Utils.cC(lang.getString("cmd-invalid-usage-message"));
        CMD_INVALID_USAGE_MESSAGE_ADMIN = Utils.cC(lang.getString("cmd-invalid-usage-message-admin"));
        CMD_HELP_MESSAGE = Utils.cCList(lang.getStringList("cmd-help-message"));
    }
}

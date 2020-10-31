package com.Ankoki.helptickets.commands;

import com.Ankoki.helptickets.inventories.TicketInventory;
import com.Ankoki.helptickets.utils.Lang;
import com.Ankoki.helptickets.utils.Ticket;
import com.Ankoki.helptickets.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TicketCMD implements CommandExecutor {

    public static List<Ticket> tickets = new ArrayList<Ticket>();
    TicketInventory ti = new TicketInventory();


    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("HelpTickets: Executeable by players only.");
            return true;
        }
        Player p = (Player) sender;
        if (!(p.hasPermission("helptickets.use"))) {
            p.sendMessage(Lang.PREFIX + " " + Lang.CMD_NOPERM_MESSAGE);
            return true;
        }

        if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
            for (String helpLine : Lang.CMD_HELP_MESSAGE) {
                p.sendMessage(Utils.cC(helpLine));
            }
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("open") && args[1].equalsIgnoreCase("text")) {

            if (p.hasPermission("helptickets.open") || p.hasPermission("helptickets.*") || p.hasPermission("helptickets.admin")) {
                p.sendMessage(Utils.cC("&8[&2H&aT&8] &cWARNING, this list MAY be very long:"));
            }
            int i = 1;
            for (Ticket t: tickets) {
                Utils.formattedTicket(p, t, i, (i >= tickets.size()));
                i++;
            }
            p.sendMessage(Utils.cC("&8[&2H&aT&8] &cThis list was &7" + (i = i - 1) + " &cTickets long."));
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("clear")) {
            tickets.clear();
            p.sendMessage(Lang.CMD_CLEAR_MESSAGE);
            return true;
        }

        if (args.length > 1 && args[0].equalsIgnoreCase("create")) {
            tickets.add(new Ticket(p.getName(), args, false));
            p.sendMessage(Utils.cC(Lang.PREFIX + " " + Lang.CMD_CREATE_MESSAGE));
            return true;
        }

        if (args.length == 1 && args[0].equals("open")) {
            if (p.hasPermission("helptickets.open") || p.hasPermission("helptickets.*") || p.hasPermission("helptickets.admin")) {
                ti.openInventory(p);
            }
        }
        p.sendMessage(Lang.CMD_INVALID_USAGE_MESSAGE.replace("%u", "/tickets (open|revoke) [reason]"));
        return true;
    }
}

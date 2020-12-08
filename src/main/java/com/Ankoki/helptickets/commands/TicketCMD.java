package com.Ankoki.helptickets.commands;

import com.Ankoki.helptickets.api.events.TicketCreateEvent;
import com.Ankoki.helptickets.files.Config;
import com.Ankoki.helptickets.inventories.TicketInventory;
import com.Ankoki.helptickets.main.HelpTickets;
import com.Ankoki.helptickets.files.Lang;
import com.Ankoki.helptickets.utils.Ticket;
import com.Ankoki.helptickets.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TicketCMD implements CommandExecutor {

    public static List<Ticket> tickets = new ArrayList<Ticket>();
    public static HashMap<UUID, Integer> playersTicket = new HashMap<UUID, Integer>();


    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("HelpTickets: Executeable by players only.");
            return true;
        }
        Player p = (Player) sender;
        if (!(p.hasPermission("helptickets.use"))) {
            p.sendMessage(Lang.PREFIX + Lang.CMD_NOPERM_MESSAGE);
            return true;
        }

        if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
            for (String helpLine : Lang.CMD_HELP_MESSAGE) {
                p.sendMessage(Utils.cC(helpLine));
            }
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                tickets.clear();
                HelpTickets.ticketIDs.clear();
                p.sendMessage(Lang.PREFIX + Lang.CMD_CLEAR_MESSAGE);
                return true;
            }

            if (args[0].equalsIgnoreCase("open")) {
                if (p.hasPermission("helptickets.open") || p.hasPermission("helptickets.*") || p.hasPermission("helptickets.admin")) {
                    TicketInventory.openInventory(p, 0);
                } else {
                    p.sendMessage(Lang.PREFIX + Lang.CMD_NOPERM_MESSAGE);
                }
                return true;
            }
        } else {
            if (args[0].equalsIgnoreCase("create")) {
                /*if (!(playersTicket.get(p.getUniqueId()) == null) && playersTicket.get(p.getUniqueId()) >= Config.MAX_TICKETS) {
                    p.sendMessage(Lang.PREFIX + Lang.CMD_MAXTICKETS_MESSAGE);
                    return true;
                }*/
                String reason = "";
                int i = 0;
                for (String str : args) {
                    if (i == 0) {
                        reason = "";
                    } else {
                        reason += " " + str;
                    }
                    i++;
                }
                Ticket ticket = new Ticket(p.getName(), p.getUniqueId(), reason, false);
                TicketCreateEvent event = new TicketCreateEvent(p, ticket);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    p.sendMessage(Lang.PREFIX + Lang.CREATE_CANCELLED);
                    return true;
                }
                int num = playersTicket.get(p.getUniqueId()) == null ? 0 : playersTicket.get(p.getUniqueId());
                num++;
                tickets.add(ticket);
                if (playersTicket.get(p.getUniqueId()) != null) {
                    playersTicket.remove(p.getUniqueId());
                }
                playersTicket.put(p.getUniqueId(), num);
                p.sendMessage(Utils.cC(Lang.PREFIX + Lang.CMD_CREATE_MESSAGE));
                return true;
            }
        }
        p.sendMessage(Lang.PREFIX + Lang.CMD_INVALID_USAGE_MESSAGE.replace("%u", "tickets (create|open|clear) [<ticket reason>]"));
        return true;
    }
}

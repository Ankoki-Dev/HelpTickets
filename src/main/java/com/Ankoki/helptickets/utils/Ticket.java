package com.Ankoki.helptickets.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Ticket {

    String reporterName;
    String[] helpReason;
    boolean ticketSolved;

    public Ticket(String playerName, String[] reasonOfReport, boolean ifTicketSolved) {
        this.reporterName = playerName;
        this.helpReason = reasonOfReport;
        this.ticketSolved = ifTicketSolved;
    }

    public String getName() {
        return reporterName;
    }

    public String getReason() {
        String str = "";
        int i = 0;
        for (String s : helpReason) {
            if (i == 0) {
                str = "";
            } else {
                str += " " + s;
            }
            i++;
        }
        return str;
    }

    public boolean isSolved() {
        return ticketSolved;
    }

    public String getSolvedYN() {
        return ticketSolved ? Utils.cC("&aYes") : Utils.cC("&cNo");
    }

    public void setSolved(boolean bool) {
        ticketSolved = bool;
    }

    public boolean reporterOnline() {
        return Bukkit.getPlayer(reporterName) != null;
    }

    public Player getReporter() {
        return Bukkit.getPlayer(reporterName);
    }
}

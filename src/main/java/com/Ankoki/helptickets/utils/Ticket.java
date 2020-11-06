package com.Ankoki.helptickets.utils;

import com.Ankoki.helptickets.main.HelpTickets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Ticket {

    String reporterName;
    String helpReason;
    boolean ticketSolved;
    Priority ticketPriority;
    UUID ticketID;

    public Ticket(String playerName, String reasonOfReport, boolean ifTicketSolved) {
        this.reporterName = playerName;
        this.helpReason = reasonOfReport;
        this.ticketSolved = ifTicketSolved;
        this.ticketPriority = Priority.UNDETERMINED;
        this.ticketID = UUID.randomUUID();
        HelpTickets.ticketIDs.put(ticketID, this);
    }

    public String getName() {
        return reporterName;
    }

    public String getReason() {
        return helpReason;
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

    public void setPriority(Priority priority) {
        ticketPriority = priority;
    }

    public Priority getPriority() {
        return ticketPriority;
    }

    public UUID getTicketID() {
        return ticketID;
    }
}

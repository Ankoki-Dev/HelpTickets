package com.Ankoki.helptickets.api;

import com.Ankoki.helptickets.main.HelpTickets;
import com.Ankoki.helptickets.utils.Ticket;

import java.util.UUID;

public class HTAPI {

    public Ticket getTicketByID(UUID uuid) {
        return HelpTickets.ticketIDs.get(uuid);
    }

    public Ticket createTicket(String playerName, UUID playerUUID, String reasonOfReport, boolean isTicketSolved) {
        return new Ticket(playerName, playerUUID, reasonOfReport, isTicketSolved);
    }
}

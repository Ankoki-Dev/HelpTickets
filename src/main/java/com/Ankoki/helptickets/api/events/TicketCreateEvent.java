package com.Ankoki.helptickets.api.events;

import com.Ankoki.helptickets.utils.Ticket;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TicketCreateEvent extends Event implements Cancellable {

    private Player player;
    private Ticket ticket;
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private boolean isCancelled;

    public TicketCreateEvent(Player player, Ticket ticket) {
        this.player = player;
        this.ticket = ticket;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean b) {
        isCancelled = b;
    }

    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Player getPlayer() {
        return player;
    }
}

package cinema;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Purchase {
    private final UUID token = UUID.randomUUID();
    private Seat ticket;
    static private ConcurrentLinkedQueue<Purchase> purchased_seats =new ConcurrentLinkedQueue<>();

    public Seat getTicket() {
        return ticket;
    }

    public UUID getToken() {
        return token;
    }

    public void setTicket(Seat ticket) {
        this.ticket = ticket;
    }

    public static ConcurrentLinkedQueue<Purchase> getPurchased_seats() {
        return purchased_seats;
    }

    public static void setPurchased_seats(ConcurrentLinkedQueue<Purchase> purchased_seats) {
        Purchase.purchased_seats = purchased_seats;
    }
}

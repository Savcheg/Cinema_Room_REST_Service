package cinema;

import java.util.concurrent.ConcurrentLinkedQueue;

public class CinemaRoom {
    private int total_rows;
    private int total_columns;
    private ConcurrentLinkedQueue<Seat> available_seats =new ConcurrentLinkedQueue<>();

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

    public  ConcurrentLinkedQueue<Seat> getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(ConcurrentLinkedQueue<Seat> available_seats) {
        this.available_seats = available_seats;
    }

    public CinemaRoom(int total_rows, int total_columns) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        for(int i = 1; i<= total_rows; i++){
            for(int j=1;j<=total_columns;j++){
                Seat seat = new Seat(i,j);
                available_seats.add(seat);
            }
        }
    }
}

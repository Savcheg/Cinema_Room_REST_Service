package cinema;

import exceptions.TicketAlreadyPurchasedException;
import exceptions.WrongNumberException;
import exceptions.WrongPasswordException;
import exceptions.WrongTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;


@RestController
public class CinemaManager {
    CinemaRoom first = new CinemaRoom(9, 9);

    @GetMapping("/seats")
    public CinemaRoom getAvailableSeats() {
        return first;
    }

    @PostMapping("/purchase")
    public Purchase PostPurchase(@RequestBody Map<String, Integer> seat) {
        if (seat.get("row") > first.getTotal_rows() || seat.get("column") > first.getTotal_columns() || seat.get("column") < 0 || seat.get("row") < 0) {
            throw new WrongNumberException("\"error\": \"The number of a row or a column is out of bounds!\"");
        }

        Iterator<Seat> iterator = first.getAvailable_seats().iterator();
        while (iterator.hasNext()) {
            Seat curr = iterator.next();
            if (curr.getRow() == seat.get("row") && curr.getColumn() == seat.get("column")) {
                first.getAvailable_seats().remove(curr);
                Purchase purchase = new Purchase();
                purchase.setTicket(curr);
                Purchase.getPurchased_seats().add(purchase);
                return purchase;
            }
        }
        throw new TicketAlreadyPurchasedException("\"The ticket has been already purchased!\"");
    }

    @PostMapping("/return")
    public Map<String, Seat> refund(@RequestBody Map<String, String> tok) {
        UUID token = UUID.fromString(tok.get("token"));
        for (Purchase pur : Purchase.getPurchased_seats()) {
            if (pur.getToken().equals(token)) {
                first.getAvailable_seats().add(pur.getTicket());
                Purchase.getPurchased_seats().remove(pur);
                return Map.of("returned_ticket", pur.getTicket());
            }
        }
        throw new WrongTokenException();
    }

    @PostMapping("/stats")
    public Map<String, Integer> getStats(@RequestParam(required = false) String password) {

        if (password !=null && password.equals("super_secret")) {
            int sum = 0;
            for (Purchase pur : Purchase.getPurchased_seats()) {
                sum += pur.getTicket().getPrice();
            }
            return Map.of("current_income", sum, "number_of_available_seats", first.getAvailable_seats().size(), "number_of_purchased_tickets", Purchase.getPurchased_seats().size());
        } else {
            throw new WrongPasswordException();
        }
    }

    @ExceptionHandler(WrongTokenException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> wrongToken() {
        return Map.of("error", "Wrong token!");
    }

    @ExceptionHandler(TicketAlreadyPurchasedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> ticketAlreadyPurchasedException() {
        return Map.of("error", "The ticket has been already purchased!");
    }

    @ExceptionHandler(WrongNumberException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleException() {
        return Map.of("error", "The number of a row or a column is out of bounds!");
    }

    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Map<String, String> wrongPassword() {
        return Map.of("error", "The password is wrong!");
    }


}

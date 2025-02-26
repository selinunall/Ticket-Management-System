public class Ticket {
    //ticket attributes
    private int ticketId;
    private double ticketPrice;
    private int eventId;
    private String userEmail;

    public Ticket(int ticketId, double ticketPrice, int eventId, String userEmail) {
        this.ticketId = ticketId;
        this.ticketPrice = ticketPrice;
        this.eventId = eventId;
        this.userEmail = userEmail;
    }
    //methods for ticket
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}

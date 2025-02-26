import java.util.Date;

public class Event {
    //event attributes
    private String eventName;
    private int noOfTickets;
    private Date eventDate;
    private String eventTime;
    private String location;
    private double price;
    private int eventID;

    public Event(String eventName, int noOfTickets, Date eventDate, String eventTime, String location,double price,int eventID) {
        this.eventName = eventName;
        this.noOfTickets = noOfTickets;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.location=location;
        this.price=price;
        this.eventID=eventID;
    }
    //methods for event

    public int getEventID() {return eventID;}

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
}

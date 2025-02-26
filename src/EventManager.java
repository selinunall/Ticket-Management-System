//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class EventManager implements User {
    //mysql information
    private static final String DB_URL = "jdbc:mysql://localhost:3306/eventDeeneme1";
    private static final String USER = "root";
    private static final String PASS = "Blabla2002_";
    //manager attributes
    private String name;
    private String surname;
    private String e_mail;
    private String password;

    public EventManager() {
        this.name = name;
        this.surname = surname;
        this.e_mail = e_mail;
        this.password = password;;
    }
    // Overridden method to display all tickets.
    @Override
    public void DisplayAllTicket() {
        //it should be empty
    }
    //There are get methods

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return e_mail;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public void setEmail(String email) {
        this.e_mail=email;
    }

    @Override
    public void setSurname(String surname) {
        this.surname=surname;
    }

    @Override
    public void setPassword(String password) {
        this.password=password;
    }



    @Override
    public void resetPassword(String email) {
        //email gönder
/*
        String to = "alıcı@example.com"; // Alıcı e-posta adresi
        String from = "gönderici@example.com"; // Gönderen e-posta adresi
        String host = "smtp.example.com"; // E-posta sunucusu (SMTP)

        Properties properties = EventManagementSystem.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);
        try {
            // MimeMessage nesnesi oluşturma
            MimeMessage message = new MimeMessage(session);

            // E-posta başlığı, alıcı ve gönderen ayarları
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Konu: E-posta gönderme denemesi"); // E-posta konusu

            // E-posta içeriği
            message.setText("Merhaba, bu bir Java ile gönderilen e-postadır.");

            // E-posta gönderme işlemi
            Transport.send(message);
            //System.out.println("E-posta başarıyla gönderildi.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }*/
        System.out.println("Email sent.");
    }
    // Method to add an event.
    public void addEvent() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Scanner scanner = new Scanner(System.in); //manager enters event details
            System.out.println("Enter Event Name:");
            String eventName = scanner.nextLine();
            // Remaining event details are similarly obtained from the manager's input.

            // SQL query to insert event details into the database.

            // This section retrieves the auto-generated event ID and generates tickets for the event.
            System.out.println("Enter Event Time:");
            String eventTime = scanner.nextLine();
            System.out.println("Enter Event Location:");
            String eventLocation = scanner.nextLine();
            System.out.println("Enter Event Category:");
            String eventCategory = scanner.nextLine();
            System.out.println("Enter Event Date: ");
            String eventDate = scanner.nextLine();
            System.out.println("Enter Number Of Ticket: ");
            int numberOfTicket = scanner.nextInt();

            String eventInsertQuery = "INSERT INTO event " +
                    "(eventName, eventTime, eventLocation, eventCategory, eventDate, numberOfTicket) VALUES (?, ?, ?, ?, ?, ?)"; //adding event to table
            PreparedStatement eventStatement = conn.prepareStatement(eventInsertQuery, Statement.RETURN_GENERATED_KEYS);
            eventStatement.setString(1, eventName);
            eventStatement.setString(2, eventTime);
            eventStatement.setString(3, eventLocation);
            eventStatement.setString(4, eventCategory);
            eventStatement.setString(5, eventDate);
            eventStatement.setInt(6, numberOfTicket);

            int rowsInserted = eventStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Manager added event successfully.");

                // Getting auto-generated event ID
                ResultSet generatedKeys = eventStatement.getGeneratedKeys();
                int eventId = -1;
                if (generatedKeys.next()) {
                    eventId = generatedKeys.getInt(1);
                }

                // generating event's tickets
                String ticketInsertQuery = "INSERT INTO ticket ( ticketPrice, eventId, userEmail) VALUES ( ?, ?, ?)";
                PreparedStatement ticketStatement = conn.prepareStatement(ticketInsertQuery);
                System.out.println("Enter ticket price: "); //manager enters ticket price
                int ticketPrice = scanner.nextInt();


                for (int i = 0; i < numberOfTicket; i++) {
                    ticketStatement.setInt(1, ticketPrice);
                    ticketStatement.setInt(2, eventId);
                    ticketStatement.setString(3, ""); //userEmail is initially empty on ticket table
                    ticketStatement.executeUpdate();
                }

                System.out.println("Tickets created successfully.");
            } else {
                System.out.println("Failed to add event.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a user.
    public void deleteUser() {
        // This method allows the manager to delete a user from the system based on email.
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter mail"); // manager enters the email of the user to be deleted
        String deleteEmail = scanner.nextLine();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "DELETE FROM user WHERE userEmail = ?"; //deleting user from user table
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, deleteEmail);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("Failed to delete user. User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Method to delete an event.
    public void deleteEvent() {
        // This method allows the manager to delete an event from the system based on event ID.
        displayAllEvent();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Event ID to delete:"); //manager enters id of the event to be deleted
            int eventId = scanner.nextInt();
            scanner.nextLine();

            String query = "DELETE FROM event WHERE eventId = ?"; //deleting event from event table
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, eventId);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Event deleted successfully.");
                deleteTicket(eventId); // Pass eventId to deleteTicket method
            } else {
                System.out.println("No event found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete tickets associated with an event.
    public void deleteTicket(int eventId){
        // This method deletes tickets associated with a specified event ID from the database.
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            String query = "DELETE FROM ticket WHERE eventId = ?"; //deleting ticket from ticket table
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, eventId);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("All tickets for "+eventId+" deleted successfully.");
            } else {
                System.out.println("No event found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Method to display all events.
    public void displayAllEvent(){
        // This method retrieves and displays all events from the database.
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM event"; //all events on the table
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //display events
                int eventId = resultSet.getInt("eventId");
                String name = resultSet.getString("eventName");
                String time = resultSet.getString("eventTime");
                String location = resultSet.getString("eventLocation");
                String category = resultSet.getString("eventCategory");
                String date = resultSet.getString("eventDate");
                System.out.println("Event ID: " + eventId + ", Event Name: " + name + ", Time: " + time + ", Location: " + location + ", Category: " + category + ", Date: " + date);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

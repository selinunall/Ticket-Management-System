
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;



public class RegularUser implements User {
    //Mysql information
    private static final String DB_URL = "jdbc:mysql://localhost:3306/eventDeeneme1";
    private static final String USER = "root";
    private static final String PASS = "Blabla2002_";
    //RegularUser attributes
    private String name;
    private String surname;
    private String e_mail;
    private String password;
    private ArrayList<Ticket> myTickets = new ArrayList<>();
    private ArrayList<Ticket> ticketsIncart = new ArrayList<>();


    @Override
    public void DisplayAllTicket() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM ticket WHERE userEmail = ?";  //the tickects which has the user's email in its userEmail column
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, this.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //Retrieve event information to display
                int eventId = resultSet.getInt("eventId");
                int ticketId = resultSet.getInt("ticketId");
                String query2 = "SELECT * FROM event WHERE eventId = ?"; //the events which has the same eventId with the ticket
                PreparedStatement preparedStatement2 = conn.prepareStatement(query2);
                preparedStatement2.setInt(1, eventId);
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                while (resultSet2.next()) {
                    //getting event information to display
                    String name = resultSet2.getString("eventName");
                    String time = resultSet2.getString("eventTime");
                    String location = resultSet2.getString("eventLocation");
                    String category = resultSet2.getString("eventCategory");
                    String date = resultSet2.getString("eventDate");
                    System.out.println("Ticket ID: " + ticketId + ",Event Name: " + name + ", Time: " + time + ", Location: " + location + ", Category: " + category + ", Date: " + date);
                }
                resultSet2.close();
                preparedStatement2.close();
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        this.name = name;
    }

    @Override
    public void setEmail(String email) {
        this.e_mail = email;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public void resetPassword(String email) {
        // Şifre sıfırlama işlemi (resetEmail metoduyla gerçekleştirilmelidir)
/*
        String to = email; // Alıcı e-posta adresi
        String from = "dilavurl@gmail.com"; // Gönderen e-posta adresi
        String host = "smtp.office365.com"; // Gmail SMTP sunucusu

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("Dila Vural", "yxra deqj ipqb pfxh");
            }
        });
//seln01unnal@gmail.com
        try {
            // MimeMessage nesnesi oluşturma
            MimeMessage message = new MimeMessage(session);

            // E-posta başlığı, alıcı ve gönderen ayarları
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Şifre Sıfırlama Bildirimi"); // E-posta konusu

            // E-posta içeriği
            message.setText("Merhaba, şifreniz sıfırlandı.");

            // E-posta gönderme işlemi
            Transport.send(message);*/
        System.out.println("Email sent.");
            /*
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }*/
    }

    public void SearchAndGetTicket() {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            //we are showing to user existing event names
            //Right below we are showing to users exist category names.
            String query1 = "SELECT DISTINCT eventName FROM event"; // Use DISTINCT to get unique event names
            PreparedStatement preparedStatement1 = conn.prepareStatement(query1);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            System.out.println("Event Names:");
            while (resultSet1.next()) {
                String name = resultSet1.getString("eventName");
                System.out.println("'" + name + "'");
            }
            resultSet1.close();
            preparedStatement1.close();

            System.out.println("Please enter event name: ");
            String eventName = scanner.nextLine();
            String query = "SELECT * FROM event WHERE eventName = ?"; //search for event with the event name on event table
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, eventName);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> eventDetails = new ArrayList<>();
            boolean numberOfTicketCheck = true;
            while (resultSet.next()) {
                //getting event's information to display
                int eventId = resultSet.getInt("eventId");
                String name = resultSet.getString("eventName");
                String time = resultSet.getString("eventTime");
                String location = resultSet.getString("eventLocation");
                String category = resultSet.getString("eventCategory");
                String date = resultSet.getString("eventDate");
                int numberOfTicket = resultSet.getInt("numberOfTicket");
                if (numberOfTicket>0){
                    eventDetails.add("Event ID: " + eventId + ", Event Name: " + name + ", Time: " +
                            time + ", Location: " + location + ", Category: " + category + ", Date: " + date);
                    numberOfTicketCheck = false;
                }
            }
            // We control event detail is true or not
            if (numberOfTicketCheck){
                System.out.println("Number of ticket is equal or less then (0)!");
            }
            if (eventDetails.isEmpty()) {
                System.out.println("No event found with the name '" + eventName + "'.");
            } else {
                System.out.println("Event(s) found with the name '" + eventName + "':");
                for (String detail : eventDetails) {
                    System.out.println(detail);
                }
                System.out.println("Do you want to add the ticket in cart?");
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("yes")) {
                    System.out.println("Please enter Event ID ");
                    int eventIdInput = scanner.nextInt();

                    String ticketQuery = "SELECT * FROM ticket WHERE eventId = ? AND userEmail = '' ORDER BY ticketId ASC LIMIT 1"; //taking the ticket with the smallest ID that has not yet been purchased
                    PreparedStatement ticketStatement = conn.prepareStatement(ticketQuery);
                    ticketStatement.setInt(1, eventIdInput);
                    ResultSet ticketResultSet = ticketStatement.executeQuery();

                    List<Ticket> cart = new ArrayList<>(); //adding event to cart
                    int ticketId = 0;
                    boolean ticketPriceCheck = true;
                    while (ticketResultSet.next()) {
                        ticketId = ticketResultSet.getInt("ticketId");
                        double ticketPrice = ticketResultSet.getDouble("ticketPrice");
                        if (ticketPrice < 0){
                            ticketPriceCheck = false;
                        }
                        int eventId = ticketResultSet.getInt("eventId");
                        String userEmail = ticketResultSet.getString("userEmail");

                        // creating ticket object to add cart list
                        if (ticketPriceCheck){
                            Ticket ticket = new Ticket(ticketId, ticketPrice, eventId, userEmail);
                            cart.add(ticket);
                        }
                    }
                    if (ticketPriceCheck){
                        System.out.println("Ticket added to cart successfully.");
                        pay(cart);
                        fullUserEmail(ticketId);
                    }
                    else {
                        System.out.println("A systemic error has occurred, " +
                                "showing a ticket price less than 0. " +
                                "\nIt will be rectified as soon as possible. " +
                                "\nPlease try again later.");
                    }
                } else if (answer.equalsIgnoreCase("no")) {
                    // If user does not want to add ticket to cart System.out.println("No ticket added to cart.");
                } else {
                    System.out.println("Invalid response.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //stub function
    public void pay(List<Ticket> tickets) {
        int totalPrice = 0;
        for (Ticket ticket : tickets) {
            totalPrice += ticket.getTicketPrice(); //calculating total price of tickets in cart
        }
        System.out.println("Total price is" + totalPrice);
        System.out.println("Payment is done :)");

    }

    public void fullUserEmail(int ticketId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "UPDATE ticket SET userEmail = ? WHERE ticketId = ?"; //Adding the e-mail of the user who bought the ticket to the ticket table
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, this.getEmail());
            preparedStatement.setInt(2, ticketId);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("User email added in ticket.");

                String updateEventQuery = "UPDATE event SET numberOfTicket = numberOfTicket - 1 WHERE eventId = (SELECT eventId FROM ticket WHERE ticketId = ?)";
                PreparedStatement updateEventStatement = conn.prepareStatement(updateEventQuery);
                updateEventStatement.setInt(1, ticketId);

                // Runs the event update query
                int affectedRowsEvent = updateEventStatement.executeUpdate();

                if (affectedRowsEvent > 0) {
                    System.out.println("Event tickets updated successfully.");
                } else {
                    System.out.println("Failed to update event tickets.");
                }
            } else {
                System.out.println("Failed to add process.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetEmail(String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "UPDATE user SET userEmail ='' WHERE userEmail = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, email);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Email reset successfully.");
            } else {
                System.out.println("No user found with the given email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refundTicket(int ticketId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Get the email information of the user who purchased the ticket
            String userEmailQuery = "SELECT userEmail FROM ticket WHERE ticketId = ?";
            try (PreparedStatement userEmailStatement = conn.prepareStatement(userEmailQuery)) {
                userEmailStatement.setInt(1, ticketId);
                try (ResultSet userEmailResult = userEmailStatement.executeQuery()) {
                    String userEmail = "";
                    if (userEmailResult.next()) {
                        userEmail = userEmailResult.getString("userEmail");
                    }

                    // Biletin satın alan kullanıcısının email bilgisini NULL olarak güncelle
                    String updateQuery = "UPDATE ticket SET userEmail = '' WHERE ticketId = ?";
                    try (PreparedStatement updateStatement = conn.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, ticketId);
                        int rowsUpdated = updateStatement.executeUpdate();

                        if (rowsUpdated > 0) {
                            System.out.println("Ticket successfully refunded.");

                            // Update the email information of the user who purchased the ticket to NULL
                        } else {
                            System.out.println("Failed to refund the ticket.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    //Method for ticket exchange
    public void changeTicket(int ticketId1, int newTicketId) {
        Scanner input = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String userEmail = "";

            // Get the email information of the user who purchased the ticket
            String userEmailQuery = "SELECT userEmail FROM ticket WHERE ticketId = ?";
            PreparedStatement userEmailStatement = conn.prepareStatement(userEmailQuery);
            userEmailStatement.setInt(1, ticketId1);
            ResultSet userEmailResult = userEmailStatement.executeQuery();

            if (userEmailResult.next()) {
                userEmail = userEmailResult.getString("userEmail");
            }

            // Update the email information of the user who purchased the ticket to NULL
            String updateQuery = "UPDATE ticket SET userEmail = NULL WHERE ticketId = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setInt(1, ticketId1);
            int rowsUpdated = updateStatement.executeUpdate();

            if (rowsUpdated > 0) {


                // Update user email for selected ticketId
                String updateUserEmailQuery = "UPDATE ticket SET userEmail = ? WHERE ticketId = ?";
                PreparedStatement updateUserEmailStatement = conn.prepareStatement(updateUserEmailQuery);
                updateUserEmailStatement.setString(1, userEmail);
                updateUserEmailStatement.setInt(2, newTicketId);
                int updatedRows = updateUserEmailStatement.executeUpdate();

                if (updatedRows > 0) {
                    System.out.println("User email successfully transferred to the new ticket.");
                } else {
                    System.out.println("Failed to transfer user email to the new ticket.");
                }
            } else {
                System.out.println("Failed to refund the ticket.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayAllEvents(){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM event";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int eventId = resultSet.getInt("eventId");
                String name = resultSet.getString("eventName");
                String time = resultSet.getString("eventTime");
                String location = resultSet.getString("eventLocation");
                String category = resultSet.getString("eventCategory");
                String date = resultSet.getString("eventDate");
                int numberOfTicket = resultSet.getInt("numberOfTicket");

                System.out.println("Event ID: " + eventId + ", Event Name: " + name + ", Time: " + time +
                        ", Location: " + location + ", Category: " + category + ", Date: " + date +
                        ", Number of Tickets: " + numberOfTicket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getTicketsByEventId(int eventId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM ticket WHERE eventId = ? AND userEmail IS NULL";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, eventId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int ticketId = resultSet.getInt("ticketId");
                double ticketPrice = resultSet.getDouble("ticketPrice");
                String userEmail = resultSet.getString("userEmail");

                System.out.println("Ticket ID: " + ticketId + ", Ticket Price: " + ticketPrice +
                        ", User Email: " + userEmail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void SearchAndFilterCategory() {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            //Right below we are showing to users exist category names.
            String query = "SELECT DISTINCT eventCategory FROM event"; // Tekrar eden kategorileri almak için DISTINCT kullanıyoruz
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Category Names:");
            while (resultSet.next()) {
                String category = resultSet.getString("eventCategory");
                System.out.println("'"+category+"'");
            }
            resultSet.close();
            preparedStatement.close();

            System.out.println("Please enter category name: ");
            String eventCategory = scanner.nextLine();

            String query2 = "SELECT * FROM event WHERE eventCategory = ?"; //search for event with the event category on event table
            PreparedStatement preparedStatement1 = conn.prepareStatement(query2);
            preparedStatement1.setString(1, eventCategory);

            ResultSet resultSet1 = preparedStatement1.executeQuery();
            List<String> eventDetails = new ArrayList<>();
            while (resultSet1.next()) {
                //getting event's category to display
                int eventId = resultSet1.getInt("eventId");
                String name = resultSet1.getString("eventName");
                String time = resultSet1.getString("eventTime");
                String location = resultSet1.getString("eventLocation");
                String category = resultSet1.getString("eventCategory");
                String date = resultSet1.getString("eventDate");

                eventDetails.add("Event ID: " + eventId + ", Event Name: " + name + ", Time: " + time + ", Location: " + location + ", Category: " + category + ", Date: " + date);
            }
            //Event detail is empty or not we check event details

            if (eventDetails.isEmpty()) {
                System.out.println("No event found with the category '" + eventCategory + "'.");
            } else {
                System.out.println("Event(s) found with the category '" + eventCategory + "':");
                for (String detail : eventDetails) {
                    System.out.println(detail);
                }
                //Add the ticket in cart process
                System.out.println("Do you want to add the ticket in cart?");
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("yes")) {
                    System.out.println("Please enter Event ID ");
                    int eventIdInput = scanner.nextInt();

                    String ticketQuery = "SELECT * FROM ticket WHERE eventId = ? AND userEmail = '' ORDER BY ticketId ASC LIMIT 1"; //taking the ticket with the smallest ID that has not yet been purchased
                    PreparedStatement ticketStatement = conn.prepareStatement(ticketQuery);
                    ticketStatement.setInt(1, eventIdInput);
                    ResultSet ticketResultSet = ticketStatement.executeQuery();

                    List<Ticket> cart = new ArrayList<>(); //adding event to cart
                    int ticketId = 0;
                    while (ticketResultSet.next()) {
                        ticketId = ticketResultSet.getInt("ticketId");
                        double ticketPrice = ticketResultSet.getDouble("ticketPrice");
                        int eventId = ticketResultSet.getInt("eventId");
                        String userEmail = ticketResultSet.getString("userEmail");

                        // creating ticket object to add cart list
                        Ticket ticket = new Ticket(ticketId, ticketPrice, eventId, userEmail);
                        cart.add(ticket);
                    }
                    System.out.println("Ticket added to cart successfully.");
                    pay(cart);
                    fullUserEmail(ticketId);
                } else if (answer.equalsIgnoreCase("no")) {
                    // if user do not want adding ticket to cart
                    System.out.println("No ticket added to cart.");
                } else {
                    System.out.println("Invalid response.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


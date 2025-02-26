import java.sql.*;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class EventManagementSystem {
    // Constants for the database connection strings
    private static final String DB_URL = "jdbc:mysql://localhost:3306/eventDeeneme1";
    private static final String USER = "root";
    private static final String PASS = "Blabla2002_";
    // An instance of RegularUser to hold user session data.
    private static final RegularUser user = new RegularUser();
    // Timer to handle automatic logout due to inactivity.
    private static Timer logoutTimer;
    // Logout timeout set to 20 minutes (in milliseconds).
    private static final long TIMEOUT = 20 * 60 * 1000; // 20 minutes in milliseconds
    private static Locale currentLocale = Locale.ENGLISH;

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static void setCurrentLocale(Locale currentLocale) {
        EventManagementSystem.currentLocale = currentLocale;
    }

    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", "smtp.example.com");
        return properties;
    }

    public static void createUserDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String sql = "CREATE DATABASE IF NOT EXISTS eventsystem";
        String query = "CREATE TABLE IF NOT EXISTS user ("
                + "userName VARCHAR(255) NOT NULL,"
                + "userSurname VARCHAR(255) NOT NULL,"
                + "userPassword VARCHAR(255) NOT NULL,"
                + "userEmail VARCHAR(255) NOT NULL"
                + ")";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            stmt.executeUpdate(query);

            System.out.println(getMessage("user table created successfully."));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTicketDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String sql = "CREATE DATABASE IF NOT EXISTS eventsystem";
        String query = "CREATE TABLE IF NOT EXISTS ticket ("
                + "ticketId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
                + "ticketPrice DOUBLE NOT NULL,"
                + "eventId INT NOT NULL,"
                + "userEmail VARCHAR(255)"
                + ")";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            stmt.executeUpdate(query);

            System.out.println(getMessage("ticket table created successfully."));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createEventDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String sql = "CREATE DATABASE IF NOT EXISTS eventsystem";
        String query = "CREATE TABLE IF NOT EXISTS event ("
                + "eventId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
                + "eventName VARCHAR(255) NOT NULL,"
                + "eventTime VARCHAR(255) NOT NULL,"
                + "eventLocation VARCHAR(255) NOT NULL,"
                + "eventCategory VARCHAR(255) NOT NULL," + "eventDate VARCHAR(255) NOT NULL," +"numberOfTicket INT NOT NULL"
                + ")";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            stmt.executeUpdate(query);

            System.out.println(getMessage("event table created successfully."));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createEventManagerDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String sql = "CREATE DATABASE IF NOT EXISTS eventsystem";
        String query = "CREATE TABLE IF NOT EXISTS eventManager ("
                + "eventManagerId INT PRIMARY KEY,"
                + "eventManagerPassword VARCHAR(255) NOT NULL"
                + ")";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            stmt.executeUpdate(query);

            System.out.println(getMessage("eventManager table created successfully."));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean loginAsUser() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            Scanner scanner = new Scanner(System.in);
            System.out.println(getMessage("Enter email: "));
            String userEmail = scanner.nextLine();
            System.out.println(getMessage("Enter password:"));
            String password = scanner.nextLine();
            String query = "SELECT * FROM user WHERE userEmail = ? AND userPassword = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, userEmail);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println(getMessage("Login successful as user."));
                user.setEmail(userEmail);
                user.setPassword(password);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean loginAsEventManager() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            Scanner scanner = new Scanner(System.in);
            System.out.println(getMessage("Enter Manager ID: "));
            int managerId = scanner.nextInt();
            scanner.nextLine();
            System.out.println(getMessage("Enter Manager password:"));
            String password = scanner.nextLine();

            String query = "SELECT * FROM eventManager WHERE eventManagerId = ? AND eventManagerPassword = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, managerId);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println(getMessage("Login successful as event manager."));
                return true;
            }
            System.out.println(getMessage("Login failed. Invalid username or password."));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static void registerAsUser() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(getMessage("Enter username:"));
            String username = scanner.nextLine();
            System.out.println(getMessage("Enter userSurname:"));
            String userSurname = scanner.nextLine();
            System.out.println(getMessage("Enter password:"));
            String password = scanner.nextLine();
            System.out.println(getMessage("Enter email:"));
            String email = scanner.nextLine();
            if (!isValidEmail(email)) {
                System.out.println(getMessage("Invalid email format."));
                return;
            }

            String checkQuery = "SELECT COUNT(*) FROM user WHERE userEmail = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkQuery);
            checkStatement.setString(1, email);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                System.out.println(getMessage("User with this email already exists."));
                return;
            }

            String query = "INSERT INTO user (userName, userSurname, userPassword, userEmail) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, userSurname);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(getMessage("User registered successfully."));
            } else {
                System.out.println(getMessage("Failed to register user."));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void registerAsEventManager() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(getMessage("Enter Manager ID:"));
            int managerId = scanner.nextInt();
            scanner.nextLine();
            System.out.println(getMessage("Enter Manager password:"));
            String managerSurname = scanner.nextLine();

            String checkQuery = "SELECT * FROM eventManager WHERE eventManagerId = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkQuery);
            checkStatement.setInt(1, managerId);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println(getMessage("This ID is already registered."));
                return;
            }

            String query = "INSERT INTO eventManager (eventManagerId, eventManagerPassword) VALUES (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, managerId);
            preparedStatement.setString(2, managerSurname);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(getMessage("Manager registered successfully."));
            } else {
                System.out.println(getMessage("Failed to register manager."));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        logoutTimer = new Timer();
        logoutTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(getMessage("User has been logged out due to inactivity."));
                System.exit(0);
            }
        }, TIMEOUT);
        createUserDatabase();
        createTicketDatabase();
        createEventDatabase();
        createEventManagerDatabase();
        Scanner input = new Scanner(System.in);

        System.out.println("Select language / Dil seçiniz: ");
        System.out.println("1. English");
        System.out.println("2. Türkçe");
        int languageChoice = input.nextInt();
        if (languageChoice == 2) {
            currentLocale = new Locale("tr", "TR");
        } else {
            currentLocale = Locale.ENGLISH;
        }

        while (true) {
            resetLogoutTimer();
            System.out.println(getMessage("Press 1 to login as user."));
            System.out.println(getMessage("Press 2 to login as event manager."));
            System.out.println(getMessage("Press 3 to register."));
            System.out.println(getMessage("Press 4 to exit."));
            int choice = input.nextInt();

            if (choice == 1) {
                if (loginAsUser()) {
                    boolean userMenuLoop = true;
                    while (userMenuLoop) {
                        System.out.println(getMessage("1-)Reset Password"));
                        System.out.println(getMessage("2-)Search and Add Ticket"));
                        System.out.println(getMessage("3-)Display My Tickets"));
                        System.out.println(getMessage("4-)Change Ticket"));
                        System.out.println(getMessage("5-)Refund Ticket"));
                        System.out.println(getMessage("6-)Exit"));

                        int userChoice = input.nextInt();
                        if (userChoice == 1) {
                            user.resetPassword(user.getEmail());
                        } else if (userChoice == 2) {
                            System.out.println(getMessage("Do you want to search by event name or category name:(event/category)"));
                            input.nextLine();
                            String userSearchSelect = input.nextLine();
                            while (true) {
                                if (userSearchSelect.equals("event")) {
                                    user.SearchAndGetTicket();
                                    break;
                                } else if (userSearchSelect.equals("category")) {
                                    user.SearchAndFilterCategory();
                                    break;
                                } else {
                                    System.out.println(getMessage("Enter only event or category!"));
                                    System.out.println(getMessage("Do you want to search by event name or category name:(event/category)"));
                                    userSearchSelect = input.nextLine();
                                }
                            }
                        } else if (userChoice == 3) {
                            user.DisplayAllTicket();
                        } else if (userChoice == 4) {
                            System.out.println(getMessage("Here are your tickets"));
                            user.DisplayAllTicket();
                            System.out.println(getMessage("Enter ID of the ticket:"));
                            int changeTicketID = input.nextInt();
                            System.out.println(getMessage("All events: "));
                            user.displayAllEvents();
                            System.out.println(getMessage("Enter ID of the new event:"));
                            int newEventID = input.nextInt();
                            user.getTicketsByEventId(newEventID);
                            System.out.println(getMessage("Enter new ticket ID: "));
                            int newTicketID = input.nextInt();
                            user.changeTicket(changeTicketID, newTicketID);
                        } else if (userChoice == 5) {
                            System.out.println(getMessage("Enter ID of the ticket:"));
                            int refundTicketID = input.nextInt();
                            user.refundTicket(refundTicketID);
                        } else if (userChoice == 6) {
                            userMenuLoop = false;
                        } else {
                            System.out.println(getMessage("Invalid choice."));
                        }
                    }
                }
            } else if (choice == 2) {
                if (loginAsEventManager()) {
                    boolean managerMenuLoop = true;
                    while (managerMenuLoop) {
                        EventManager eventManager = new EventManager();
                        System.out.println(getMessage("1-)Reset Password"));
                        System.out.println(getMessage("2-)Add Event"));
                        System.out.println(getMessage("3-)Delete Event"));
                        System.out.println(getMessage("4-)Delete User"));
                        System.out.println(getMessage("5-)Exit"));
                        System.out.println(getMessage("please enter choice"));
                        int managerChoice = input.nextInt();

                        if (managerChoice == 1) {
                            System.out.println(getMessage("Enter the email"));
                            String email = input.nextLine();
                            eventManager.resetPassword(email);
                        } else if (managerChoice == 2) {
                            eventManager.addEvent();
                        } else if (managerChoice == 3) {
                            eventManager.deleteEvent();
                        } else if (managerChoice == 4) {
                            eventManager.deleteUser();
                        } else if (managerChoice == 5) {
                            managerMenuLoop = false;
                        }
                    }
                }
            } else if (choice == 3) {
                System.out.println(getMessage("Press 1 to register as user."));
                System.out.println(getMessage("Press 2 to register as event manager."));
                int choiceForRegister = input.nextInt();
                if (choiceForRegister == 1) {
                    registerAsUser();
                } else if (choiceForRegister == 2) {
                    registerAsEventManager();
                } else {
                    System.out.println(getMessage("Invalid choice."));
                }
            } else if (choice == 4) {
                System.exit(0);
            } else {
                System.out.println(getMessage("Invalid choice."));
            }
        }
    }

    private static void resetLogoutTimer() {
        if (logoutTimer != null) {
            logoutTimer.cancel();
        }
        logoutTimer = new Timer();
        logoutTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(getMessage("User has been logged out due to inactivity."));
                System.exit(0);
            }
        }, TIMEOUT);
    }

    public static String getMessage(String message) {
        if (currentLocale.equals(new Locale("tr", "TR"))) {
            switch (message) {
                case "user table created successfully.":
                    return "Kullanıcı tablosu başarıyla oluşturuldu.";
                case "ticket table created successfully.":
                    return "Bilet tablosu başarıyla oluşturuldu.";
                case "event table created successfully.":
                    return "Etkinlik tablosu başarıyla oluşturuldu.";
                case "eventManager table created successfully.":
                    return "Etkinlik Yöneticisi tablosu başarıyla oluşturuldu.";
                case "Enter email: ":
                    return "E-posta giriniz: ";
                case "Enter password:":
                    return "Şifre giriniz:";
                case "Login successful as user.":
                    return "Kullanıcı olarak giriş başarılı.";
                case "Enter Manager ID: ":
                    return "Yönetici ID'si giriniz: ";
                case "Enter Manager password:":;
                    return "Yönetici şifresini giriniz:";
                case "Login successful as event manager.":
                    return "Etkinlik yöneticisi olarak giriş başarılı.";
                case "Login failed. Invalid username or password.":
                    return "Giriş başarısız. Geçersiz kullanıcı adı veya şifre.";
                case "Enter username:":
                    return "Kullanıcı adı giriniz:";
                case "Enter userSurname:":
                    return "Kullanıcı soyadını giriniz:";
                case "Invalid email format.":
                    return "Geçersiz e-posta formatı.";
                case "User with this email already exists.":
                    return "Bu e-postayla kayıtlı kullanıcı zaten var.";
                case "User registered successfully.":
                    return "Kullanıcı başarıyla kaydedildi.";
                case "Failed to register user.":
                    return "Kullanıcı kaydedilemedi.";
                case "Enter Manager ID:":
                    return "Yönetici ID'si giriniz:";

                case "This ID is already registered.":
                    return "Bu ID zaten kayıtlı.";
                case "Manager registered successfully.":
                    return "Yönetici başarıyla kaydedildi.";
                case "Failed to register manager.":
                    return "Yönetici kaydedilemedi.";
                case "User has been logged out due to inactivity.":
                    return "Hareketsizlik nedeniyle kullanıcı oturumu kapatıldı.";
                case "Press 1 to login as user.":
                    return "Kullanıcı olarak giriş yapmak için 1'e basın.";
                case "Press 2 to login as event manager.":
                    return "Etkinlik yöneticisi olarak giriş yapmak için 2'ye basın.";
                case "Press 3 to register.":
                    return "Kayıt olmak için 3'e basın.";
                case "Press 4 to exit.":
                    return "Çıkış yapmak için 4'e basın.";
                case "1-)Reset Password":
                    return "1-)Şifreyi Sıfırla";
                case "2-)Search and Add Ticket":
                    return "2-)Bilet Ara ve Ekle";
                case "3-)Display My Tickets":
                    return "3-)Biletlerimi Göster";
                case "4-)Change Ticket":
                    return "4-)Bileti Değiştir";
                case "5-)Refund Ticket":
                    return "5-)Bileti İade Et";
                case "6-)Exit":
                    return "6-)Çıkış";
                case "Do you want to search by event name or category name:(event/category)":
                    return "Etkinlik adına veya kategori adına göre arama yapmak ister misiniz:(etkinlik/kategori)";
                case "Enter only event or category!":
                    return "Sadece etkinlik veya kategori girin!";
                case "Here are your tickets":
                    return "İşte biletleriniz";
                case "Enter ID of the ticket:":
                    return "Bilet ID'sini girin:";
                case "All events: ":
                    return "Tüm etkinlikler: ";
                case "Enter ID of the new event:":
                    return "Yeni etkinliğin ID'sini girin:";
                case "Enter new ticket ID: ":
                    return "Yeni bilet ID'sini girin: ";
                case "please enter choice":
                    return "lütfen seçim yapın";
                case "Enter the email":
                    return "E-posta girin";
                case "Invalid choice.":
                    return "Geçersiz seçim.";
                case "Press 1 to register as user.":
                    return "Kullanıcı olarak kayıt olmak için 1'e basın.";
                case "Press 2 to register as event manager.":
                    return "Etkinlik yöneticisi olarak kayıt olmak için 2'ye basın.";
                default:
                    return message;
            }
        } else {
            return message;
        }
    }
}

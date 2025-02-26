public interface User {
        //common methods for RegularUser and EventManager classes

        void DisplayAllTicket();
        String getName();
        String getEmail();
        String getSurname();
        String getPassword();
        void setName(String name);
        void setEmail(String email);
        void setSurname(String surname);
        void setPassword(String password);
        void resetPassword(String email);


}

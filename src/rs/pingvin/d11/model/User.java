package rs.pingvin.d11.model;

import java.util.Objects;

public class User {

    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + username + " " + password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof User))
            return false;

        User user = (User) obj;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}

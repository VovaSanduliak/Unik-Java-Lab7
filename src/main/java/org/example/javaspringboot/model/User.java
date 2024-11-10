package org.example.javaspringboot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "\"user\"")
public class User implements Comparable<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private int id;

    @Getter
    private String userName;

    @Getter
    private String email;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_house",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "house_id")
    )
    private final List<House> houses;

    public User() {
        houses = new ArrayList<>();
    }

    @JsonCreator
    public User(@JsonProperty("userName") String userName, @JsonProperty("email") String email, @JsonProperty("password") String password) {
        setUserName(userName);
        setEmail(email);
        setPassword(password);
        this.houses = new ArrayList<>();
    }

    public void setUserName(String userName) {
        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.userName = userName;
    }

    public void setEmail(String newEmail) {
        if (newEmail == null || newEmail.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.email = newEmail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = newPassword; // Consider hashing before setting the password
    }

    public List<House> getHouses() {
        return Collections.unmodifiableList(this.houses);
    }

    public void addHouse(House house) {
        this.houses.add(house);
    }

    public void addHouses(List<House> houses) {
        houses.forEach(this::addHouse);
    }

    public boolean removeHouse(House house) {
        return this.houses.remove(house);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName='" + userName + "', email='" + email + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, email);
    }

    @Override
    public int compareTo(User u) {
        if (u == null) {
            return 1;
        }
        return this.getUserName().compareTo(u.getUserName());
    }
}

package BananaBrain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Table(name = "roles")
@Entity
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    private int id;
    @Setter
    @Getter
    @Column(nullable = false)
    private String role;

    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<MyAppUser> users = new HashSet<>();

    // Constructor
    public Roles() {}

    public Roles(String role) {
        this.role = role;
    }

    // Getter for users
    public Set<MyAppUser> getUsers() {
        return users;
    }

    public void setUsers(Set<MyAppUser> users) {
        this.users = users;
    }
}

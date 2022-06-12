package ru.maralays.mfa.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users_role")
@AllArgsConstructor
@NoArgsConstructor
public class UsersRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="users_id")
    @JsonIgnore
    private Users users;
    @ManyToOne
    @JoinColumn(name = "roles_id")
    private Role roles;


    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UsersRoles{" +
                "roles=" + roles +
                '}';
    }

    public Long getId() {
        return id;
    }
}

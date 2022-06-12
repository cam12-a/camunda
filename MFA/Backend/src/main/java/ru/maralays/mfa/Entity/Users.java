package ru.maralays.mfa.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Set;

/**
 *
 * @author Camara Alseny
 * @version 1.0
 * @since 27/04/2022
 * this is a user template class
 * use this class to map user's data
 *
 *
 */


@Entity
@Table(name = "Users")
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor

public class Users    {
    public void setId(Long id) {
        this.id = id;
    }
    //implements UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    @JsonIgnore
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "name")
    private  String name;



    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<QRCode> qrCode;

    @OneToMany(mappedBy = "userToken", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UsersTokens> tokens;

    public Set<UsersRoles> getUsers() {
        return users;
    }

    public void setUsers(Set<UsersRoles> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }







    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, orphanRemoval = true,
            cascade = CascadeType.ALL)
    @JsonBackReference(value = "users")
    @JsonIgnore
    private Set<UsersRoles> users;

    public String getIdFirebaseMessagingCloud() {
        return idFirebaseMessagingCloud;
    }

    public void setIdFirebaseMessagingCloud(String idFirebaseMessagingCloud) {
        this.idFirebaseMessagingCloud = idFirebaseMessagingCloud;
    }

    public Users(){

    }

    public Users(String username, String email, String password, String firstname, String lastname, String name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.name = name;
    }

    @Transient
    private String oldUsername;
    @Transient
    private String oldPassword;
    @Transient
    private String idFirebaseMessagingCloud;


    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", name='" + name + '\'' +
                ", oldUsername='" + oldUsername + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ",idFirebaseMessagingCloud "+ idFirebaseMessagingCloud+ '\''+
                '}';
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword=oldPassword;
    }


    public String getOldUsername() {
        return oldUsername;
    }


    public void setOldUsername(String oldUsername) {
        this.oldUsername=oldUsername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





}

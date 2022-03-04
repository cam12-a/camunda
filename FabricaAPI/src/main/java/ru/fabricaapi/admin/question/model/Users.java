package ru.fabricaapi.admin.question.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

//@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id_user")
//@EqualsAndHashCode(exclude = "nameAttributeInThisClassWithOneToMany")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_user;
    @Column(name = "username",unique = true)
    @JsonProperty
    private String username;
    @Column(name = "first_name")
    @JsonProperty
    private String firstName;
    @Column(name = "last_name")
    @JsonProperty
    private String lastName;
    @Column(name = "pwd")
    @JsonProperty
    private String pwd;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles;

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<UsersSurvey> getSurveyPassedByUser() {
        return surveyPassedByUser;
    }

    public void setSurveyPassedByUser(Set<UsersSurvey> surveyPassedByUser) {
        this.surveyPassedByUser = surveyPassedByUser;
    }

    public PasswordToken getPasswordToken() {
        return passwordToken;
    }

    public void setPasswordToken(PasswordToken passwordToken) {
        this.passwordToken = passwordToken;
    }


    /*
    @ManyToMany
    @JoinTable(
            name = "users_survey",
            joinColumns=@JoinColumn(name = "user_id"),
            inverseJoinColumns=@JoinColumn(name = "survey_id")
    )
    Set<SurveyTemplate> userSurvey;
*/

    @OneToMany(mappedBy = "usersList",cascade = CascadeType.ALL)
    @JsonBackReference(value = "userList")
    private Set<UsersSurvey> surveyPassedByUser;


    @OneToOne(mappedBy = "usersPasswordToken")
    private PasswordToken passwordToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return this.pwd;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

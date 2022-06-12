package ru.maralays.mfa.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Component
@AllArgsConstructor

public class Role  {
    //implements GrantedAuthority
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Column(name = "roleName",unique = true)
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoles(Set<UsersRoles> roles) {
        this.roles = roles;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Role(){}

    @OneToMany(mappedBy = "roles", fetch = FetchType.LAZY, orphanRemoval = true,
            cascade = CascadeType.ALL)
    @JsonBackReference(value = "roles")
    @JsonIgnore
    private Set<UsersRoles> roles;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    /*@Override
    public String getAuthority() {
        return this.roleName;
    }


    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST})
    @JsonBackReference(value = "roles")
    @JsonIgnore
    private Set<Users> users =new HashSet<>();

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }
*/

}

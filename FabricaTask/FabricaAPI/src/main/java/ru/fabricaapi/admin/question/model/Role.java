package ru.fabricaapi.admin.question.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "role")
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "role_name",unique = true)
    private String roleName;
    public Role(int id){this.id=id;}
    public Role(int id,String roleName){this.id=id;this.roleName=roleName;}
    public Role(String roleName){this.roleName=roleName;}

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<Users> users ;

    @Override
    public String getAuthority() {
        return this.roleName;
    }
}

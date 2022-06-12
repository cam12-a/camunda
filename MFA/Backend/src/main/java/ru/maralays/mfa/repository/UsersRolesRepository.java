package ru.maralays.mfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maralays.mfa.Entity.UsersRoles;
@Repository
public interface UsersRolesRepository  extends JpaRepository<UsersRoles, Long> {

    UsersRoles findByUsersAndRoles(Long user_id, Long role_id);
}

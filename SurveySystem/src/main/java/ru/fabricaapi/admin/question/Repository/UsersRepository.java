package ru.fabricaapi.admin.question.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fabricaapi.admin.question.model.Users;

public interface UsersRepository extends JpaRepository<Users,Integer> {
}

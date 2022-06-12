package ru.maralays.mfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maralays.mfa.Entity.SessionDetails;

public interface SessionDetailsRepository extends JpaRepository<SessionDetails, Long> {

   // SessionDetails findByUserSessionId(String username);

}

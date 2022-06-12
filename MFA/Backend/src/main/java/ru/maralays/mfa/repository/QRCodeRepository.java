package ru.maralays.mfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maralays.mfa.Entity.QRCode;
import ru.maralays.mfa.Entity.Users;

import java.util.List;

@Repository
public interface QRCodeRepository extends JpaRepository<QRCode, Long> {
    QRCode findByUsers(Users users);
    List<QRCode> findByUsersOrderByDateSendDesc(Users users);

}

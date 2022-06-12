package ru.maralays.mfa.service.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.maralays.mfa.Entity.QRCode;
import ru.maralays.mfa.Entity.Users;
import ru.maralays.mfa.error.APIException;
import ru.maralays.mfa.repository.QRCodeRepository;

@Service
public class QRCodeDAO {

    @Autowired
    private QRCodeRepository qrCodeRepository;
    @Autowired
    APIException apiException;

    @Autowired
    private UsersDAO usersDAO;

    public QRCode saveQRCode(QRCode qrCode) throws Throwable {
        Users users=usersDAO.findUsers(qrCode.getUsers().getUsername());
        if(users!=null){
            qrCode.setUsers(users);
            return qrCodeRepository.save(qrCode);
        }

        throw apiException.AccessForbidden();
    }

    public QRCode getQRCodeByUsername(Users users) throws Throwable {
        QRCode qrCode= qrCodeRepository.findByUsers(users);
        if(qrCode!=null){
            return  qrCode;
        }
        throw apiException.NotFoundException("QRCODE is not generate for the specific user"+ HttpStatus.NOT_FOUND.toString());
    }

    public QRCode getLastInseredQRCode(Users users) {
        return qrCodeRepository.findByUsersOrderByDateSendDesc(users).get(0);
    }
}

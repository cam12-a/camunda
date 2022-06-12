package ru.maralays.mfa.error;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class APIException extends APITemplateException {


    @Override
    public Throwable CredentialException() {
        return new Throwable("Invalid credential" + HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public Throwable CredentialException(String message) {
        return new Throwable( message + HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public Throwable NotFoundException() {
        return new Throwable("Такого пользователя не существует" + HttpStatus.NOT_FOUND);
    }

    @Override
    public Throwable NotFoundException(String message) {
        return new Throwable(message + HttpStatus.NOT_FOUND);
    }

    @Override
    public Throwable AccessForbidden(String message) {
        return new Throwable(message + HttpStatus.FORBIDDEN);
    }

    @Override
    public Throwable AccessForbidden() {
        return new Throwable("Вы не имеете доступ к данной системе, лучность проверена но неверна"+ HttpStatus.FORBIDDEN);
    }


}

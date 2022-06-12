package ru.maralays.mfa.error;

public abstract class APITemplateException extends Exception{
    public abstract Throwable CredentialException();
    public abstract Throwable CredentialException(String message);
    public abstract  Throwable NotFoundException();
    public abstract  Throwable NotFoundException(String message);
    public abstract Throwable AccessForbidden(String message);
    public abstract Throwable AccessForbidden();

}

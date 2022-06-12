package ru.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chat.entity.Messages;
import ru.chat.entity.Users;

import java.util.List;

public interface MessageRepo extends JpaRepository<Messages, Long> {

    Messages findByMessageReceiverUsersAndMessageSenderUsersAndDateSend(Users userReceiver, Users userSender, String dateSend);
    List<Messages> findByMessageReceiverUsers(Users userReceiver);
    List<Messages> findByMessageSenderUsers(Users userSender);
    List<Messages> findByMessageSenderUsersAndMessageReceiverUsers(Users userSender, Users userReceiver);
}

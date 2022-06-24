package ru.chat.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chat.entity.Messages;
import ru.chat.entity.Users;
import ru.chat.repository.MessageRepo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class MessageDAO {

    @Autowired
    MessageRepo messageRepo;

    @Autowired
    UsersDAO usersDAO;

    public Messages createMessages(Messages messages){
        return messageRepo.save(messages);
    }


    public void deleteMessages(Messages messages){
        messageRepo.deleteById(messages.getId());
    }

    public Messages editMessages(Messages messages){
        Messages tempMessages = messageRepo.findByMessageReceiverUsersAndMessageSenderUsersAndDateSend(messages.getMessageReceiverUsers(),messages.getMessageSenderUsers(),messages.getDateSend());
        tempMessages.setMessageText(messages.getMessageText());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        tempMessages.setDateSend(formatter.format(new Date()));
        return messageRepo.save(tempMessages);

    }

    public Messages getMessageByDateSendUserSenderAndUserReceiver(Messages messages){
        return messageRepo.findByMessageReceiverUsersAndMessageSenderUsersAndDateSend(messages.getMessageReceiverUsers(),messages.getMessageSenderUsers(),messages.getDateSend());
    }

    public List<Messages> getReceiveMessageFromUser(Users userSend, Users userReceive) {
        return messageRepo.findByMessageSenderUsersAndMessageReceiverUsers(userSend,userReceive);
    }

    public List<Messages> getSentMessage(Users user){
        return  messageRepo.findByMessageSenderUsers(user);
    }

    public List<Messages> getReceiveMessage(String  username){
        Users user=usersDAO.findByUsername(username);
        return messageRepo.findByMessageReceiverUsers(user);
    }
}

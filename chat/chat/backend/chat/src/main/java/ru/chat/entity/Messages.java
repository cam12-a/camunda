package ru.chat.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "messages")

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "message_text", length = 65555)
    private String messageText;

    public Long getId() {
        return id;
    }

    @Column(name = "date_send")
    private String dateSend;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users messageReceiverUsers;

    @ManyToOne
    @JoinColumn(name = "users_id_sender")
    private Users messageSenderUsers;


    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getDateSend() {
        return dateSend;
    }

    public void setDateSend(String dateSend) {
        this.dateSend = dateSend;
    }

    public Users getMessageReceiverUsers() {
        return messageReceiverUsers;
    }

    public void setMessageReceiverUsers(Users messageReceiverUsers) {
        this.messageReceiverUsers = messageReceiverUsers;
    }

    public Users getMessageSenderUsers() {
        return messageSenderUsers;
    }

    public void setMessageSenderUsers(Users messageSenderUsers) {
        this.messageSenderUsers = messageSenderUsers;
    }


    @Override
    public String toString() {
        return "Messages{" +
                "id=" + id +
                ", messageText='" + messageText + '\'' +
                ", dateSend='" + dateSend + '\'' +
                ", messageReceiverUsers=" + messageReceiverUsers +
                ", messageSenderUsers=" + messageSenderUsers +
                '}';
    }
}

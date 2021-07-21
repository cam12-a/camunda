package ru.service;

import org.springframework.stereotype.Service;
import ru.models.AboutProcesses;


@Service("sendMessage")
public class sendMessage extends AboutProcesses {
    public String sendMsg(){
        this.setMessageWhileRejected("Ваша заявка была отклонена");
        System.out.println(this.getMessageWhileRejected());
        return this.getMessageWhileRejected();
    }

}

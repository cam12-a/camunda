package ru.service;


import org.springframework.stereotype.Service;


@Service("Status")

public class Status  {
    public String setStatus(String status){
        if(status.equals("cancelled")){
            return "cancelled";
        }
        if(status.equals("called_back"))
            return "called_back";
        else
            return "approved";
    }

}

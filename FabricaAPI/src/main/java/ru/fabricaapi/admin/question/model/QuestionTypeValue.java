package ru.fabricaapi.admin.question.model;



import org.springframework.stereotype.Service;


public enum QuestionTypeValue {

    ONE("1"),
    TWO("2"),
    NONE("0");

    public  String label;


    private  QuestionTypeValue(String label){
        this.label = label;
    }



}

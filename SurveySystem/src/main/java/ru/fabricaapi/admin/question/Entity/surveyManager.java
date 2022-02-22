package ru.fabricaapi.admin.question.Entity;

public interface surveyManager {
    void saveItem();
    int editItem();
    int deleteItem();
    int deleteItem(int id);
    void cloneObject();
}

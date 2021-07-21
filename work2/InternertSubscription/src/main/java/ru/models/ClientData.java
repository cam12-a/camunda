package ru.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientData  {

    private String first_name;
    private String last_name;
    private String country;
    public int age=0;
    private String tarif;



/*
    public String getFirst_name() {
        return first_name;
    }

    public int getAge() {
        return age;
    }

    public String getCountry() {
        return country;
    }

    public String getLast_name() {
        return last_name;
    }



    public String getTarif() {
        return tarif;
    }

    public void setAge(int age) {
        this.age = age;
    }



    public void setCountry(String country) {
        this.country = country;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }



    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    @Override
    public String toString() {
        return "ClientData{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", country='" + country + '\'' +
                ", age=" + age +
                ", tarif='" + tarif + '\'' +
                '}';
    }

*/

}

package test.generateData;

import camundajar.impl.scala.util.control.Exception;

import java.util.*;

public class GenerateDataForTest {
    private  static   List<String> candidateUser=new ArrayList<String>(2);
    private static List<String> HR=new ArrayList<>(2);
    private static Map<String,String> vacationType=new HashMap<String,String>();
    int days;
    int month;

    public Date generateDate(){
        int days=generateRandomDaysNumber();
        int month=generateRandomMonthNumber();
        if(((month+1)%2!=1 || (month+1)!=8 || (month+1)!=2) && days==31)
            days=30;
        if((month+1)==2 && days>28)
            days=27;
        Calendar calendar=new GregorianCalendar(2021,generateRandomMonthNumber(),generateRandomDaysNumber());
        return calendar.getTime();
    }

    public int generateRandomDaysNumber()
    {
       return (int) (1+Math.random()*31);
    }

    public int generateRandomMonthNumber()
    {
        return (int) (Math.random()*12);
    }

    public void setCandidateUser(){
        candidateUser.add("petrov_initiator2");
        candidateUser.add("petrov_initiator");
    }
    public String whoExecute(){
        return candidateUser.get((int) Math.random()*1);
    }
    public void Hr(){
        HR.add("amin_hr2");
        HR.add("micha_hr1");
    }
    public String getHR()
    {
        return  HR.get((int) Math.random()*1);
    }
    public void setVacationType()
    {
        vacationType.put("everyyear","Ежегодный оплачиваемый отпуск");
        vacationType.put("vacationWithoutSalary","отпуск без сохранения ЗП");
        vacationType.put("vacationForMothers","отпуск по уходу за  ребенком");
    }
    public Map<String, String> getMap() {
        return vacationType;
    }
    public Map<String, Object> processTestData(String key)
    {
        Map<String,Object> testData=new HashMap<String,Object>();
        testData.put("vacationStartDate",this.generateDate());
        testData.put("vacationEndDate",this.generateDate());
        testData.put("vacationType",key);
        return  testData;
    }


}

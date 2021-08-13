package ru.Services;


import camundajar.impl.scala.App;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.Modules.ApplicationData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static groovy.util.GroovyTestCase.assertEquals;

@Data
@NoArgsConstructor

@Service("ValidateApplicationService")
public class ValidateApplicationService {


    public boolean validateApplicationData (List<String> variableOfProcessInExecution, List<String> processVariable)
    {
        for (int i = 0; i < variableOfProcessInExecution.size(); i++){
                    for (int j = 0; j < processVariable.size(); j++) {
                        if (processVariable.get(j).equals(variableOfProcessInExecution.get(i)))
                            return true;
                    }
            }

        return false;

    }

    public int minimumVacationPeriod(String vacationType, String vacationStartDate, String vacationEndDate)  {
            int days=this.getAmountDays(vacationStartDate,vacationEndDate);
            if((vacationType.equals("everyyear") && days>14) || vacationType.equals("vacationWithoutSalary") && days>3)
                return days;
            if(days<0)
                return -1;
            return 0;
    }

    public int getAmountDays(String vacationStartDate, String vacationEndDate)
    {
        int days=0;
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy",Locale.ENGLISH);
        DateFormat formatter1 = new SimpleDateFormat("dd.MM.yyyy");
        try{
            Date vacationStart= formatter1.parse(formatter1.format(formatter.parse(vacationStartDate)));
            Date vacationEnd=formatter1.parse(formatter1.format(formatter.parse(vacationEndDate)));
            long milliseconds=vacationEnd.getTime()-vacationStart.getTime();
           // System.out.println(formatter1.format(formatter.parse(vacationEndDate)));
            days=(int) (milliseconds / (24 * 60 * 60 * 1000));

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return days;
    }

    public int checkAmountDays(List<String> processInstances)
    {
        int countDays=0;

        for(String pr:processInstances)
        {
           try {
               String vacationStart = pr.split(",")[2].split("=")[1].substring(0, pr.split(",")[2].split("=")[1].length() - 1);
               String vacationEnd = pr.split(",")[1].split("=")[1];
               countDays += this.getAmountDays(vacationStart, vacationEnd);
           }catch (Exception ex)
           {
               ex.getMessage();
           }
        }

        return countDays;
    }

}

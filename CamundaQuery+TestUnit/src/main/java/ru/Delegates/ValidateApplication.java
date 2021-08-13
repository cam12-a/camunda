package ru.Delegates;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import ru.Modules.ApplicationData;
import ru.Services.ProcessInfo;
import ru.Services.ValidateApplicationService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named("ValidateApplication")
public class ValidateApplication implements JavaDelegate{

    @Inject
    private ValidateApplication validateApplication;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        ApplicationData applicationData = new ApplicationData();
        applicationData.setStatus(delegateExecution.getVariable("status").toString());

        ValidateApplicationService validateApplicationService = new ValidateApplicationService();
        ProcessInfo processInfo = new ProcessInfo(delegateExecution.getProcessEngine().getName());

        String vacationStartDate = delegateExecution.getVariable("vacationStartDate").toString();
        String vacationEndDate = delegateExecution.getVariable("vacationEndDate").toString();

        String vacationType = delegateExecution.getVariable("vacationType").toString();
        System.out.println(vacationType);
        Map<String, Object> processVariables = new HashMap<>();
        processVariables.put("vacationStartDate", vacationStartDate);
        processVariables.put("vacationEndDate", vacationEndDate);
        processVariables.put("vacationType", vacationType);
        List<String> variableOfProcessInExecution = new ArrayList<>();
        variableOfProcessInExecution.add(processVariables.toString());
        // System.out.println(variableOfProcessInExecution);

        List<String> processVariable = processInfo.getProcessVariables(processInfo.getUserID(), "RequestVacationSchedule");


        if (validateApplicationService.validateApplicationData(variableOfProcessInExecution, processVariable)) {
                delegateExecution.setVariable("status", "error");
                delegateExecution.setVariable("error", "Уже существует заявка за \n" +
                        "указанный период отпуска. Пожалуйста, исправьте сроки.");
        }

        int totalDays = validateApplicationService.checkAmountDays(processVariable) + validateApplicationService.checkAmountDays(variableOfProcessInExecution);

        if (totalDays > 28) {

            delegateExecution.setVariable("status", "error");
            delegateExecution.setVariable("error", "Превышен лимит отпуска для \n" + processInfo.getUserID() +
                    ". Осталось распределить " + (28 - validateApplicationService.checkAmountDays(processVariable)) + " в 28 дней");
        }


        if (validateApplicationService.minimumVacationPeriod(vacationType, vacationStartDate, vacationEndDate) > 14 && vacationType.equals("everyyear")) {
            delegateExecution.setVariable("status", "error");
            delegateExecution.setVariable("error", "Минимальная продолжительность отпуска \n" +
                    "этого типа 14 дней");
        }

        if (validateApplicationService.minimumVacationPeriod(vacationType, vacationStartDate, vacationEndDate) > 3 && vacationType.equals("vacationWithoutSalary")) {
            delegateExecution.setVariable("status", "error");
            delegateExecution.setVariable("error", "Максимальная продолжительность отпуска этого типа 3 \n" +
                    "дня");
        }

        if (validateApplicationService.minimumVacationPeriod(vacationType, vacationStartDate, vacationEndDate) == -1) {
            delegateExecution.setVariable("status", "error");
            delegateExecution.setVariable("error", "Дата окончания не может быть раньше даты начала");
        }





    }



}

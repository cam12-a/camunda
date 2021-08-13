package test;


import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.junit5.test.ProcessEngineExtension;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.Delegates.GenerateGUID;
import ru.Delegates.ValidateApplication;
import ru.Services.ValidateApplicationService;
import test.generateData.GenerateDataForTest;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestCreatedApplication {

    @Rule
    public ProcessEngineRule processEngineRule = new ProcessEngineRule();
    public ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
    public ValidateApplicationService validateApplicationService=new ValidateApplicationService();
    public   GenerateDataForTest generateDataForTest=new GenerateDataForTest();
    public Date vacationStartDate;
    public Date vacationEndDate;
    @Mock
    GenerateGUID generateGUID;
    @Mock
    ValidateApplication validateApplication;

    @Before
    public void setup() {
        GenerateGUID generateGUID=new GenerateGUID();
        ValidateApplication validateApplication=new ValidateApplication();
        Mocks.register("GenerateGUID", generateGUID);
        Mocks.register("ValidateApplication", validateApplication);
    }

    @Test
    @Deployment(resources="RequestVacationSchedule.bpmn")
    public void createApplicationHappyPath() {

        ProcessInstance processInstance =this.initialize();

        generateDataForTest.Hr();
        complete(task(),withVariables(
                "vacationStartDate",vacationStartDate,
                "vacationType","everyyear",
                "vacationEndDate",vacationEndDate,
                "statusManager","accepted"
        ));
        assertThat(processInstance)
                .hasPassed("confirmation")
                .isWaitingAtExactly("HR")
                .isNotWaitingAt("notify")
                .task().hasCandidateUser(generateDataForTest.getHR());

        complete(task(),withVariables(
                "vacationStartDate",this.vacationStartDate,
                "vacationType","everyyear",
                "vacationEndDate",this.vacationEndDate,
                "statusManager","accepted"
        ));

        assertThat(processInstance)
                .hasPassed("HR")
                .isEnded();


    }

    @Test
    @Deployment(resources="RequestVacationSchedule.bpmn")
    public void applicationRejected()
    {
        ProcessInstance processInstance =this.initialize();
        complete(task(),withVariables(
                "vacationStartDate",vacationStartDate,
                "vacationType","everyyear",
                "vacationEndDate",vacationEndDate,
                "statusManager","refused"
        ));
        generateDataForTest.Hr();

        assertThat(processInstance)
                .hasPassed("confirmation")
                .isNotWaitingAt("HR")
                .isWaitingAtExactly("notify")
                .task().hasCandidateUser("alseny_initiator1");

        complete(task(),withVariables(
                "vacationStartDate",vacationStartDate,
                "vacationType","everyyear",
                "vacationEndDate",vacationEndDate,
                "statusManager","refused"
        ));

        assertThat(processInstance)
                .hasPassed("notify")
                .isEnded();


    }


    public ProcessInstance initialize()
    {

        this.correctDate();
        Map<String, Object> variables= generateDataForTest.processTestData("everyyear");
        System.out.println(variables.toString());
        ProcessInstance processInstance =runtimeService().startProcessInstanceByKey("RequestVacationSchedule",variables);
        assertThat(processInstance)
                .isActive()
                .hasPassed("GenerateGUI")
                .isWaitingAtExactly("createAPP")
                .task()
                .hasCandidateUser("alseny_initiator1");

        String vacationType="everyyear";
        complete(task(),withVariables(
                "vacationStartDate",this.vacationStartDate,
                "vacationType","everyyear",
                "vacationEndDate",this.vacationEndDate
        ));

        assertThat(processInstance)
                .hasPassed("createAPP")
                .hasPassedInOrder("validateAPPData")
                .isWaitingAtExactly("confirmation")
                .task();


        return processInstance;
    }

    public void correctDate()
    {
        List<Date> date=new ArrayList<Date>();
        Date vacationStartDate=generateDataForTest.generateDate();
        Date vacationEndDate=generateDataForTest.generateDate();
        if(vacationEndDate.before(vacationStartDate))
        {
            Date temp=vacationStartDate;
            vacationStartDate=vacationEndDate;
            vacationEndDate=vacationStartDate;
        }
        while(validateApplicationService.getAmountDays(vacationStartDate.toString(),vacationEndDate.toString())>28)
        {
            vacationStartDate=generateDataForTest.generateDate();
            vacationEndDate=generateDataForTest.generateDate();
            if(vacationEndDate.before(vacationStartDate))
            {
                Date temp=vacationEndDate;
                vacationEndDate=vacationStartDate;
                vacationStartDate=temp;
            }
        }
        date.add(vacationStartDate);
        date.add(vacationEndDate);
        this.vacationStartDate=vacationStartDate;
        this.vacationEndDate=vacationEndDate;

    }


}

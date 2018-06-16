package za.co.tman.systemgw.cucumber.stepdefs;

import za.co.tman.systemgw.SystemgatewayApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = SystemgatewayApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}

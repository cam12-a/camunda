package ru.processEngine;

import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.spring.boot.starter.configuration.CamundaAuthorizationConfiguration;
import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration;
import org.camunda.bpm.spring.boot.starter.property.AuthorizationProperty;

public class AuthorizationConfiguration extends AbstractCamundaConfiguration implements CamundaAuthorizationConfiguration {

    @Override
    public void preInit(final SpringProcessEngineConfiguration configuration) {
        final AuthorizationProperty authorization = camundaBpmProperties.getAuthorization();
        configuration.setAuthorizationEnabled(authorization.isEnabled());
        configuration.setAuthorizationEnabledForCustomCode(authorization.isEnabledForCustomCode());
        configuration.setAuthorizationCheckRevokes(authorization.getAuthorizationCheckRevokes());

        configuration.setUserResourceWhitelistPattern(".+");
        configuration.setTenantCheckEnabled(authorization.isTenantCheckEnabled());
    }
}

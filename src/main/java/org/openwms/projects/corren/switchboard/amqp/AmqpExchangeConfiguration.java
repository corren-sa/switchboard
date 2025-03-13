package org.openwms.projects.corren.switchboard.amqp;

import org.openwms.core.SpringProfiles;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(SpringProfiles.ASYNCHRONOUS_PROFILE)
public class AmqpExchangeConfiguration {

}
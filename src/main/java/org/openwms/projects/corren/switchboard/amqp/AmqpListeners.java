package org.openwms.projects.corren.switchboard.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.openwms.core.SpringProfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@Profile(SpringProfiles.ASYNCHRONOUS_PROFILE)
@RequiredArgsConstructor
public class AmqpListeners {

	private static final Logger LOGGER = LoggerFactory.getLogger(AmqpListeners.class);
	private final ObjectMapper objectMapper;

}
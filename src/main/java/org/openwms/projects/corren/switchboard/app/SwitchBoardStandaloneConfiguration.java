package org.openwms.projects.corren.switchboard.app;

import org.openwms.core.SpringProfiles;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(SpringProfiles.DISTRIBUTED)
@EnableDiscoveryClient
public class SwitchBoardStandaloneConfiguration {
}
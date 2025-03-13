package org.openwms.projects.corren.switchboard.app;

import feign.auth.BasicAuthRequestInterceptor;
import org.openwms.core.SpringProfiles;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(SpringProfiles.DISTRIBUTED)
@AutoConfigureOrder(0)
@EnableFeignClients
public class SwitchBoardFeignConfiguration {

  @Bean
  public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
    return new BasicAuthRequestInterceptor("user", "sa");
  }
}

/*
* Copyright 2005-2023 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.openwms.projects.corren.switchboard.app;

import org.ameba.amqp.RabbitTemplateConfigurable;
import org.openwms.core.SpringProfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import static org.ameba.LoggingCategories.BOOT;

/**
* A EmAsyncConfiguration.
*/
@Profile(SpringProfiles.ASYNCHRONOUS_PROFILE)
@Configuration
@RefreshScope
@EnableRabbit
class SwitchBoardAsyncConfiguration {

 private static final Logger BOOT_LOGGER = LoggerFactory.getLogger(BOOT);

 @ConditionalOnExpression("'${owms.switchboard.serialization}'=='json'")
 @Bean
 MessageConverter messageConverter() {
  BOOT_LOGGER.info("Using JSON serialization over AMQP");
  return new Jackson2JsonMessageConverter();
 }

 @ConditionalOnExpression("'${owms.switchboard.serialization}'=='barray'")
 @Bean
 MessageConverter serializerMessageConverter() {
  BOOT_LOGGER.info("Using byte array serialization over AMQP");
  return new SerializerMessageConverter();
 }

 @Primary
 @Bean(name = "amqpTemplate")
 public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
    ObjectProvider<MessageConverter> messageConverter,
    @Autowired(required = false) RabbitTemplateConfigurable rabbitTemplateConfigurable) {
  RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
  ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
  backOffPolicy.setMultiplier(2);
  backOffPolicy.setMaxInterval(15000);
  backOffPolicy.setInitialInterval(500);
  RetryTemplate retryTemplate = new RetryTemplate();
  retryTemplate.setBackOffPolicy(backOffPolicy);
  rabbitTemplate.setRetryTemplate(retryTemplate);
  rabbitTemplate.setMessageConverter(messageConverter.getIfUnique());
  if (rabbitTemplateConfigurable != null) {
   rabbitTemplateConfigurable.configure(rabbitTemplate);
  }
  return rabbitTemplate;
 }
}
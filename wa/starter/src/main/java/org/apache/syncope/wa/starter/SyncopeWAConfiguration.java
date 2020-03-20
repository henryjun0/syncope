/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.syncope.wa.starter;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import org.apereo.cas.services.DefaultRegisteredServiceEntityMapper;
import org.apereo.cas.services.RegisteredServiceEntityMapper;
import org.apache.syncope.common.keymaster.client.api.model.NetworkService;
import org.apache.syncope.common.keymaster.client.api.startstop.KeymasterStart;
import org.apache.syncope.common.keymaster.client.api.startstop.KeymasterStop;
import org.apereo.cas.services.RegisteredService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration(proxyBeanMethods = false)
public class SyncopeWAConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ClassLoaderTemplateResolver syncopeTemplateResolver() {
        ClassLoaderTemplateResolver syncopeTemplateResolver = new ClassLoaderTemplateResolver();
        syncopeTemplateResolver.setPrefix("syncope/templates/");
        syncopeTemplateResolver.setSuffix(".html");
        syncopeTemplateResolver.setTemplateMode(TemplateMode.HTML);
        syncopeTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        syncopeTemplateResolver.setOrder(0);
        syncopeTemplateResolver.setCheckExistence(true);

        return syncopeTemplateResolver;
    }

    @Bean
    @ConditionalOnProperty(name = "cas.serviceRegistry.rest.url")
    public RegisteredServiceEntityMapper<RegisteredService, Serializable> registeredServiceEntityMapper() {
        return new DefaultRegisteredServiceEntityMapper();
    }

    @Bean
    public KeymasterStart keymasterStart() {
        return new KeymasterStart(NetworkService.Type.WA);
    }

    @Bean
    public KeymasterStop keymasterStop() {
        return new KeymasterStop(NetworkService.Type.WA);
    }
}

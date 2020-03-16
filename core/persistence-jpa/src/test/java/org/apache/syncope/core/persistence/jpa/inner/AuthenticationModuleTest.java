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
package org.apache.syncope.core.persistence.jpa.inner;

import org.apache.syncope.common.lib.authentication.module.AuthenticationModuleConf;
import org.apache.syncope.common.lib.authentication.module.GoogleMfaAuthenticationModuleConf;
import org.apache.syncope.common.lib.authentication.module.JaasAuthenticationModuleConf;
import org.apache.syncope.common.lib.authentication.module.LDAPAuthenticationModuleConf;
import org.apache.syncope.common.lib.authentication.module.StaticAuthenticationModuleConf;
import org.apache.syncope.common.lib.types.AMImplementationType;
import org.apache.syncope.common.lib.types.ImplementationEngine;
import org.apache.syncope.core.persistence.api.dao.ImplementationDAO;
import org.apache.syncope.core.persistence.api.dao.authentication.AuthenticationModuleDAO;
import org.apache.syncope.core.persistence.api.entity.Implementation;
import org.apache.syncope.core.persistence.api.entity.authentication.AuthenticationModule;
import org.apache.syncope.core.persistence.jpa.AbstractTest;
import org.apache.syncope.core.provisioning.api.serialization.POJOHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Transactional("Master")
public class AuthenticationModuleTest extends AbstractTest {

    @Autowired
    private AuthenticationModuleDAO authenticationModuleDAO;

    @Autowired
    private ImplementationDAO implementationDAO;

    @Test
    public void find() {
        AuthenticationModule module = authenticationModuleDAO.find(
                "be456831-593d-4003-b273-4c3fb61700df");
        assertNotNull(module);

        module = authenticationModuleDAO.find(UUID.randomUUID().toString());
        assertNull(module);
    }

    @Test
    public void findAll() {
        List<AuthenticationModule> modules = authenticationModuleDAO.findAll();
        assertNotNull(modules);
        assertEquals(1, modules.size());
    }

    @Test
    public void saveWithPredefinedModule() {
        StaticAuthenticationModuleConf conf =
                new StaticAuthenticationModuleConf(Map.of("user", UUID.randomUUID().toString()));

        Implementation config = getImplementation(conf);

        config = implementationDAO.save(config);

        assertNotNull(config);
        assertNotNull(config.getKey());

        AuthenticationModule module = entityFactory.newEntity(AuthenticationModule.class);
        module.setName("AuthenticationModuleTest");
        module.add(config);
        authenticationModuleDAO.save(module);

        assertNotNull(module);
        assertNotNull(module.getKey());

        assertNotNull(authenticationModuleDAO.find(module.getKey()));
    }

    @Test
    public void saveWithJaasModule() {
        JaasAuthenticationModuleConf conf = new JaasAuthenticationModuleConf();
        conf.setKerberosKdcSystemProperty("sample-value");
        conf.setKerberosRealmSystemProperty("sample-value");
        conf.setLoginConfigType("JavaLoginConfig");
        conf.setRealm("SYNCOPE");
        conf.setLoginConfigurationFile("/opt/jaas/login.conf");
        Implementation config = getImplementation(conf);

        config = implementationDAO.save(config);

        assertNotNull(config);
        assertNotNull(config.getKey());

        AuthenticationModule module = entityFactory.newEntity(AuthenticationModule.class);
        module.setName("AuthenticationModuleTest");
        module.add(config);
        authenticationModuleDAO.save(module);

        assertNotNull(module);
        assertNotNull(module.getKey());

        assertNotNull(authenticationModuleDAO.find(module.getKey()));
    }

    @Test
    public void saveWithLdapModule() {
        LDAPAuthenticationModuleConf conf = new LDAPAuthenticationModuleConf();
        conf.getAttributes().addAll(List.of("cn", "uid"));
        conf.setBaseDn("dc=example,dc=org");
        conf.setSearchFilter("cn={user}");
        conf.setSubtreeSearch(true);
        conf.setLdapUrl("ldap://localhost:1389");
        conf.setUserIdAttribute("uid");
        conf.setBaseDn("cn=Directory Manager,dc=example,dc=org");
        conf.setBindCredential("Password");
        Implementation config = getImplementation(conf);

        config = implementationDAO.save(config);

        assertNotNull(config);
        assertNotNull(config.getKey());

        AuthenticationModule module = entityFactory.newEntity(AuthenticationModule.class);
        module.setName("AuthenticationModuleTest");
        module.add(config);
        authenticationModuleDAO.save(module);

        assertNotNull(module);
        assertNotNull(module.getKey());

        assertNotNull(authenticationModuleDAO.find(module.getKey()));
    }

    @Test
    public void saveWithGoogleAuthenticatorModule() {
        GoogleMfaAuthenticationModuleConf conf =
                new GoogleMfaAuthenticationModuleConf();
        conf.setCodeDigits(6);
        conf.setIssuer("SyncopeTest");
        conf.setLabel("Syncope");
        conf.setTimeStepSize(30);
        conf.setWindowSize(3);
        Implementation config = getImplementation(conf);

        config = implementationDAO.save(config);

        assertNotNull(config);
        assertNotNull(config.getKey());

        AuthenticationModule module = entityFactory.newEntity(AuthenticationModule.class);
        module.setName("AuthenticationModuleTest");
        module.add(config);
        authenticationModuleDAO.save(module);

        assertNotNull(module);
        assertNotNull(module.getKey());

        assertNotNull(authenticationModuleDAO.find(module.getKey()));
    }

    private Implementation getImplementation(final AuthenticationModuleConf conf) {
        Implementation config = entityFactory.newEntity(Implementation.class);
        config.setKey(UUID.randomUUID().toString());
        config.setEngine(ImplementationEngine.JAVA);
        config.setType(AMImplementationType.AUTH_MODULE_CONFIGURATIONS);
        config.setBody(POJOHelper.serialize(conf));
        return config;
    }

    @Test
    public void delete() {
        AuthenticationModule athAuthenticationModule = authenticationModuleDAO.find(
                "be456831-593d-4003-b273-4c3fb61700df");
        assertNotNull(athAuthenticationModule);

        authenticationModuleDAO.delete("be456831-593d-4003-b273-4c3fb61700df");

        athAuthenticationModule = authenticationModuleDAO.find("be456831-593d-4003-b273-4c3fb61700df");
        assertNull(athAuthenticationModule);
    }

}

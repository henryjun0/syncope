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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.UUID;

import org.apache.syncope.core.persistence.api.dao.authentication.AuthenticationModuleDAO;
import org.apache.syncope.core.persistence.api.entity.authentication.AuthenticationModule;
import org.apache.syncope.core.persistence.jpa.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional("Master")
public class AuthenticationModuleTest extends AbstractTest {

    @Autowired
    private AuthenticationModuleDAO authenticationModuleDAO;

    @Test
    public void find() {
        AuthenticationModule athAuthenticationModule = authenticationModuleDAO.find(
                "be456831-593d-4003-b273-4c3fb61700df");
        assertNotNull(athAuthenticationModule);

        athAuthenticationModule = authenticationModuleDAO.find(UUID.randomUUID().toString());
        assertNull(athAuthenticationModule);
    }

    @Test
    public void findAll() {
        List<AuthenticationModule> athAuthenticationModules = authenticationModuleDAO.findAll();
        assertNotNull(athAuthenticationModules);
        assertEquals(1, athAuthenticationModules.size());
    }

    @Test
    public void save() {

        int beforeCount = authenticationModuleDAO.findAll().size();
        AuthenticationModule authenticationModule = entityFactory.newEntity(AuthenticationModule.class);
        authenticationModule.setName("AuthenticationModuleTest");
        authenticationModuleDAO.save(authenticationModule);

        assertNotNull(authenticationModule);
        assertNotNull(authenticationModule.getKey());

        int afterCount = authenticationModuleDAO.findAll().size();
        assertEquals(afterCount, beforeCount + 1);
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

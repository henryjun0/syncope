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

import org.apache.syncope.core.persistence.api.dao.authentication.AuthenticationPolicyDAO;
import org.apache.syncope.core.persistence.api.entity.policy.AuthenticationPolicy;
import org.apache.syncope.core.persistence.jpa.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Transactional("Master")
public class AuthenticationPolicyTest extends AbstractTest {

    @Autowired
    private AuthenticationPolicyDAO authenticationPolicyDAO;

    @Test
    public void find() {
        AuthenticationPolicy authenticationPolicy = authenticationPolicyDAO.find("b912a0d4-a890-416f-9ab8-84ab077eb028");
        assertNotNull(authenticationPolicy);
        authenticationPolicy = authenticationPolicyDAO.find(UUID.randomUUID().toString());
        assertNull(authenticationPolicy);
    }

    @Test
    public void findAll() {
        List<AuthenticationPolicy> authenticationPolicies = authenticationPolicyDAO.findAll();
        assertNotNull(authenticationPolicies);
        assertEquals(1, authenticationPolicies.size());
    }

    @Test
    public void save() {

        int beforeCount = authenticationPolicyDAO.findAll().size();
        AuthenticationPolicy authenticationPolicy = entityFactory.newEntity(AuthenticationPolicy.class);
        authenticationPolicy.setName("AuthenticationPolicyTest");
        authenticationPolicy.setDescription("This is a sample authentication policy");
        authenticationPolicyDAO.save(authenticationPolicy);

        assertNotNull(authenticationPolicy);
        assertNotNull(authenticationPolicy.getKey());

        int afterCount = authenticationPolicyDAO.findAll().size();
        assertEquals(afterCount, beforeCount + 1);
    }

    @Test
    public void delete() {
        AuthenticationPolicy authenticationPolicy = authenticationPolicyDAO.find("b912a0d4-a890-416f-9ab8-84ab077eb028");
        assertNotNull(authenticationPolicy);
        authenticationPolicyDAO.delete("b912a0d4-a890-416f-9ab8-84ab077eb028");
        authenticationPolicy = authenticationPolicyDAO.find("b912a0d4-a890-416f-9ab8-84ab077eb028");
        assertNull(authenticationPolicy);
    }

}

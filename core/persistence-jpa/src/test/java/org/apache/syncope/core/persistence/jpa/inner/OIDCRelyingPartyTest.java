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

import org.apache.syncope.core.persistence.api.dao.authentication.OIDCRelyingPartyDAO;
import org.apache.syncope.core.persistence.api.entity.authentication.OIDCRelyingParty;
import org.apache.syncope.core.persistence.api.entity.policy.AccessPolicy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.syncope.common.lib.types.OIDCSubjectType;
import org.apache.syncope.core.persistence.api.entity.policy.AuthPolicy;

@Transactional("Master")
public class OIDCRelyingPartyTest extends AbstractClientAppTest {

    @Autowired
    private OIDCRelyingPartyDAO oidcRelyingPartyDAO;

    @Test
    public void find() {
        int beforeCount = oidcRelyingPartyDAO.findAll().size();

        OIDCRelyingParty rp = entityFactory.newEntity(OIDCRelyingParty.class);
        rp.setName("OIDC");
        rp.setDescription("This is a sample OIDC RP");
        rp.setClientId("clientid");
        rp.setClientSecret("secret");
        rp.setSubjectType(OIDCSubjectType.PUBLIC);
        rp.getSupportedGrantTypes().add("something");
        rp.getSupportedResponseTypes().add("something");

        AccessPolicy accessPolicy = buildAndSaveAccessPolicy();
        rp.setAccessPolicy(accessPolicy);

        AuthPolicy authPolicy = buildAndSaveAuthPolicy();
        rp.setAuthPolicy(authPolicy);

        oidcRelyingPartyDAO.save(rp);

        assertNotNull(rp);
        assertNotNull(rp.getKey());

        int afterCount = oidcRelyingPartyDAO.findAll().size();
        assertEquals(afterCount, beforeCount + 1);

        rp = oidcRelyingPartyDAO.findByClientId("clientid");
        assertNotNull(rp);
        assertNotNull(rp.getAuthPolicy());

        rp = oidcRelyingPartyDAO.findByName("OIDC");
        assertNotNull(rp);

        oidcRelyingPartyDAO.deleteByClientId("clientid");
        assertNull(oidcRelyingPartyDAO.findByName("OIDC"));
    }

}

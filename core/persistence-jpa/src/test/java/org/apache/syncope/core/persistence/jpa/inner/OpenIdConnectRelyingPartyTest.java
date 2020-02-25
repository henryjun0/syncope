/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.apache.syncope.core.persistence.jpa.inner;

import org.apache.syncope.common.lib.authentication.DefaultAuthenticationPolicyConf;
import org.apache.syncope.common.lib.types.AMImplementationType;
import org.apache.syncope.common.lib.types.ImplementationEngine;
import org.apache.syncope.core.persistence.api.dao.ImplementationDAO;
import org.apache.syncope.core.persistence.api.dao.authentication.AuthenticationPolicyDAO;
import org.apache.syncope.core.persistence.api.dao.authentication.OpenIdConnectRelyingPartyDAO;
import org.apache.syncope.core.persistence.api.entity.Implementation;
import org.apache.syncope.core.persistence.api.entity.authentication.OpenIdConnectRelyingParty;
import org.apache.syncope.core.persistence.api.entity.policy.AuthenticationPolicy;
import org.apache.syncope.core.persistence.jpa.AbstractTest;
import org.apache.syncope.core.provisioning.api.serialization.POJOHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional("Master")
public class OpenIdConnectRelyingPartyTest extends AbstractTest {

    @Autowired
    private AuthenticationPolicyDAO authenticationPolicyDAO;

    @Autowired
    private ImplementationDAO implementationDAO;

    @Autowired
    private OpenIdConnectRelyingPartyDAO openIdConnectRelyingPartyDAO;

    @Test
    public void find() {
        int beforeCount = openIdConnectRelyingPartyDAO.findAll().size();
        
        OpenIdConnectRelyingParty rp = entityFactory.newEntity(OpenIdConnectRelyingParty.class);
        rp.setName("OIDC");
        rp.setDescription("This is a sample OIDC RP");
        rp.setClientId("clientid");
        rp.setClientSecret("secret");

        AuthenticationPolicy policy = buildAndSaveAuthenticationPolicy();
        rp.setAuthenticationPolicy(policy);

        openIdConnectRelyingPartyDAO.save(rp);

        assertNotNull(rp);
        assertNotNull(rp.getKey());

        int afterCount = openIdConnectRelyingPartyDAO.findAll().size();
        assertEquals(afterCount, beforeCount + 1);

        rp = openIdConnectRelyingPartyDAO.findByClientId("clientid");
        assertNotNull(rp);
        assertNotNull(rp.getAuthenticationPolicy());
        
        rp = openIdConnectRelyingPartyDAO.findByName("OIDC");
        assertNotNull(rp);

        openIdConnectRelyingPartyDAO.deleteByClientId("clientid");
        assertNull(openIdConnectRelyingPartyDAO.findByName("OIDC"));
    }

    private AuthenticationPolicy buildAndSaveAuthenticationPolicy() {
        AuthenticationPolicy authenticationPolicy = entityFactory.newEntity(AuthenticationPolicy.class);
        authenticationPolicy.setName("AuthenticationPolicyTest");
        authenticationPolicy.setDescription("This is a sample authentication policy");

        DefaultAuthenticationPolicyConf conf = new DefaultAuthenticationPolicyConf();
        conf.setAuthenticationModules(List.of("LdapAuthentication1", "DatabaseAuthentication2"));

        Implementation type = entityFactory.newEntity(Implementation.class);
        type.setKey("AuthPolicyConfKey");
        type.setEngine(ImplementationEngine.JAVA);
        type.setType(AMImplementationType.AUTH_POLICY_CONFIGURATIONS);
        type.setBody(POJOHelper.serialize(conf));
        type = implementationDAO.save(type);

        authenticationPolicy.addConfiguration(type);
        return authenticationPolicyDAO.save(authenticationPolicy);
    }
}

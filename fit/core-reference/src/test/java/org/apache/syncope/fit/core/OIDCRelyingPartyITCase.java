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
package org.apache.syncope.fit.core;

import org.apache.commons.lang3.StringUtils;
import org.apache.syncope.common.lib.to.AuthenticationPolicyTO;
import org.apache.syncope.common.lib.to.client.OIDCRelyingPartyTO;
import org.apache.syncope.common.lib.types.PolicyType;
import org.apache.syncope.fit.AbstractITCase;
import org.junit.jupiter.api.Test;
import org.apache.syncope.common.lib.to.AccessPolicyTO;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.syncope.common.lib.SyncopeClientException;

public class OIDCRelyingPartyITCase extends AbstractITCase {

    @Test
    public void create() {
        createOpenIdConnectRelyingParty(buildRelyingParty());
    }

    @Test
    public void read() {
        OIDCRelyingPartyTO rpTO = buildRelyingParty();
        rpTO = createOpenIdConnectRelyingParty(rpTO);

        OIDCRelyingPartyTO found = openIdConnectRelyingPartyService.read(rpTO.getKey());
        assertNotNull(found);
        assertFalse(StringUtils.isBlank(found.getClientId()));
        assertTrue(StringUtils.isBlank(found.getClientSecret()));
        assertNotNull(found.getAccessPolicy());
        assertNotNull(found.getAuthenticationPolicy());
    }

    @Test
    public void update() {
        OIDCRelyingPartyTO rpTO = buildRelyingParty();
        rpTO = createOpenIdConnectRelyingParty(rpTO);

        AccessPolicyTO accessPolicyTO = new AccessPolicyTO();
        accessPolicyTO.setKey("NewAccessPolicyTest_" + getUUIDString());
        accessPolicyTO.setDescription("New Access policy");
        accessPolicyTO = createPolicy(PolicyType.ACCESS, accessPolicyTO);
        assertNotNull(accessPolicyTO);

        rpTO.setClientId("newClientId");
        rpTO.setAccessPolicy(accessPolicyTO);

        openIdConnectRelyingPartyService.update(rpTO);
        OIDCRelyingPartyTO updated = openIdConnectRelyingPartyService.read(rpTO.getKey());

        assertNotNull(updated);
        assertEquals("newClientId", updated.getClientId());
        assertNotNull(rpTO.getAccessPolicy());
        assertEquals("New Access policy", rpTO.getAccessPolicy().getDescription());
    }

    @Test
    public void delete() {
        OIDCRelyingPartyTO rpTO = buildRelyingParty();
        rpTO = createOpenIdConnectRelyingParty(rpTO);

        openIdConnectRelyingPartyService.delete(rpTO.getKey());

        try {
            openIdConnectRelyingPartyService.read(rpTO.getKey());
            fail("This should not happen");
        } catch (SyncopeClientException e) {
            assertNotNull(e);
        }
    }

    private OIDCRelyingPartyTO buildRelyingParty() {
        AuthenticationPolicyTO authPolicyTO = new AuthenticationPolicyTO();
        authPolicyTO.setKey("AuthPolicyTest_" + getUUIDString());
        authPolicyTO.setDescription("Authentication Policy");
        authPolicyTO = createPolicy(PolicyType.AUTHENTICATION, authPolicyTO);
        assertNotNull(authPolicyTO);

        AccessPolicyTO accessPolicyTO = new AccessPolicyTO();
        accessPolicyTO.setKey("AccessPolicyTest_" + getUUIDString());
        accessPolicyTO.setDescription("Access policy");
        accessPolicyTO = createPolicy(PolicyType.ACCESS, accessPolicyTO);
        assertNotNull(accessPolicyTO);

        OIDCRelyingPartyTO rpTO = new OIDCRelyingPartyTO();
        rpTO.setName("ExampleRP_" + getUUIDString());
        rpTO.setDescription("Example OIDC RP application");
        rpTO.setClientId("clientId_" + getUUIDString());
        rpTO.setClientSecret(StringUtils.EMPTY);
        rpTO.setAuthenticationPolicy(authPolicyTO);
        rpTO.setAccessPolicy(accessPolicyTO);

        return rpTO;
    }

}

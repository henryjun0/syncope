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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.apache.commons.lang3.StringUtils;
import org.apache.syncope.common.lib.SyncopeClientException;
import org.apache.syncope.common.lib.to.AccessPolicyTO;
import org.apache.syncope.common.lib.to.AuthenticationPolicyTO;
import org.apache.syncope.common.lib.to.client.SAML2ServiceProviderTO;
import org.apache.syncope.common.lib.types.PolicyType;
import org.apache.syncope.fit.AbstractITCase;
import org.junit.jupiter.api.Test;

public class SAML2ServiceProviderITCase extends AbstractITCase {

    @Test
    public void create() {
        createSAML2SP(buildSAML2SP());
    }

    @Test
    public void read() {
        SAML2ServiceProviderTO samlSpTO = buildSAML2SP();
        samlSpTO = createSAML2SP(samlSpTO);

        SAML2ServiceProviderTO found = saml2ServiceProviderService.read(samlSpTO.getKey());
        assertNotNull(found);
        assertFalse(StringUtils.isBlank(found.getEntityId()));
        assertFalse(StringUtils.isBlank(found.getMetadataLocation()));
        assertNotNull(found.getAccessPolicy());
        assertNotNull(found.getAuthenticationPolicy());
    }

    @Test
    public void update() {
        SAML2ServiceProviderTO samlSpTO = buildSAML2SP();
        samlSpTO = createSAML2SP(samlSpTO);

        AccessPolicyTO accessPolicyTO = new AccessPolicyTO();
        accessPolicyTO.setKey("NewAccessPolicyTest_" + getUUIDString());
        accessPolicyTO.setDescription("New Access policy");
        accessPolicyTO = createPolicy(PolicyType.ACCESS, accessPolicyTO);
        assertNotNull(accessPolicyTO);

        samlSpTO.setEntityId("newEntityId");
        samlSpTO.setAccessPolicy(accessPolicyTO);

        saml2ServiceProviderService.update(samlSpTO);
        SAML2ServiceProviderTO updated = saml2ServiceProviderService.read(samlSpTO.getKey());

        assertNotNull(updated);
        assertEquals("newEntityId", updated.getEntityId());
        assertNotNull(updated.getAccessPolicy());
        assertEquals("New Access policy", updated.getAccessPolicy().getDescription());
    }

    @Test
    public void delete() {
        SAML2ServiceProviderTO samlSpTO = buildSAML2SP();
        samlSpTO = createSAML2SP(samlSpTO);

        saml2ServiceProviderService.delete(samlSpTO.getKey());

        try {
            saml2ServiceProviderService.read(samlSpTO.getKey());
            fail("This should not happen");
        } catch (SyncopeClientException e) {
            assertNotNull(e);
        }
    }

    private SAML2ServiceProviderTO buildSAML2SP() {
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

        SAML2ServiceProviderTO saml2spto = new SAML2ServiceProviderTO();
        saml2spto.setName("ExampleSAML2SP_" + getUUIDString());
        saml2spto.setDescription("Example SAML 2.0 service provider");
        saml2spto.setEntityId("SAML2SPEntityId_" + getUUIDString());
        saml2spto.setMetadataLocation("file:./test");
        saml2spto.setAuthenticationPolicy(authPolicyTO);
        saml2spto.setAccessPolicy(accessPolicyTO);

        return saml2spto;
    }

}

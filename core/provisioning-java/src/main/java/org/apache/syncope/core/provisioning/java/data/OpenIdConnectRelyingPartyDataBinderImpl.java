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
package org.apache.syncope.core.provisioning.java.data;

import org.apache.syncope.common.lib.SyncopeClientException;
import org.apache.syncope.common.lib.policy.AccessPolicyTO;
import org.apache.syncope.common.lib.policy.AuthenticationPolicyTO;
import org.apache.syncope.common.lib.to.OpenIdConnectRelyingPartyTO;
import org.apache.syncope.common.lib.types.ClientExceptionType;
import org.apache.syncope.core.persistence.api.dao.PolicyDAO;
import org.apache.syncope.core.persistence.api.dao.authentication.OpenIdConnectRelyingPartyDAO;
import org.apache.syncope.core.persistence.api.entity.EntityFactory;
import org.apache.syncope.core.persistence.api.entity.authentication.OpenIdConnectRelyingParty;
import org.apache.syncope.core.persistence.api.entity.policy.AccessPolicy;
import org.apache.syncope.core.persistence.api.entity.policy.AuthenticationPolicy;
import org.apache.syncope.core.provisioning.api.data.OpenIdConnectRelyingPartyDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OpenIdConnectRelyingPartyDataBinderImpl implements OpenIdConnectRelyingPartyDataBinder {

    @Autowired
    private OpenIdConnectRelyingPartyDAO openIdConnectRelyingPartyDAO;

    @Autowired
    private EntityFactory entityFactory;

    @Autowired
    private PolicyDAO policyDAO;

    @Override
    public OpenIdConnectRelyingParty create(final OpenIdConnectRelyingPartyTO applicationTO) {
        return update(entityFactory.newEntity(OpenIdConnectRelyingParty.class), applicationTO);
    }

    @Override
    public OpenIdConnectRelyingParty update(
            final OpenIdConnectRelyingParty toBeUpdated,
            final OpenIdConnectRelyingPartyTO applicationTO) {

        OpenIdConnectRelyingParty application = openIdConnectRelyingPartyDAO.save(toBeUpdated);

        application.setDescription(applicationTO.getDescription());
        application.setName(applicationTO.getName());
        application.setClientSecret(applicationTO.getClientSecret());
        application.setClientId(applicationTO.getClientId());
        application.setRedirectUris(applicationTO.getRedirectUris());

        AuthenticationPolicy authenticationPolicy = policyDAO.
                find(applicationTO.getAuthenticationPolicy().getKey());
        if (authenticationPolicy == null) {
            SyncopeClientException sce = SyncopeClientException.build(ClientExceptionType.InvalidEntity);
            sce.getElements().add("Unable to locate authentication policy "
                    + applicationTO.getAuthenticationPolicy().getKey());
            throw sce;
        }
        application.setAuthenticationPolicy(authenticationPolicy);

        AccessPolicy accessPolicy = policyDAO.find(applicationTO.getAccessPolicy().getKey());
        application.setAccessPolicy(accessPolicy);

        return application;
    }

    @Override
    public OpenIdConnectRelyingPartyTO getClientApplicationTO(final OpenIdConnectRelyingParty rp) {
        OpenIdConnectRelyingPartyTO applicationTO = new OpenIdConnectRelyingPartyTO();

        applicationTO.setKey(rp.getKey());
        applicationTO.setDescription(rp.getDescription());
        applicationTO.setClientId(rp.getClientId());
        applicationTO.setClientSecret(rp.getClientSecret());
        applicationTO.setRedirectUris(rp.getRedirectUris());
        applicationTO.setName(rp.getName());

        AuthenticationPolicyTO authenticationPolicyTO = new AuthenticationPolicyTO();
        authenticationPolicyTO.setDescription(rp.getAuthenticationPolicy().getDescription());
        authenticationPolicyTO.setKey(rp.getAuthenticationPolicy().getKey());
        applicationTO.setAuthenticationPolicy(authenticationPolicyTO);

        if (rp.getAccessPolicy() != null) {
            AccessPolicyTO accessPolicyTO = new AccessPolicyTO();
            accessPolicyTO.setDescription(rp.getAccessPolicy().getDescription());
            accessPolicyTO.setKey(rp.getAccessPolicy().getKey());
            applicationTO.setAccessPolicy(accessPolicyTO);
        }
        return applicationTO;
    }
}

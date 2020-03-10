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
import org.apache.syncope.common.lib.to.client.SAML2ServiceProviderTO;
import org.apache.syncope.common.lib.types.ClientExceptionType;
import org.apache.syncope.core.persistence.api.dao.PolicyDAO;
import org.apache.syncope.core.persistence.api.dao.authentication.SAML2ServiceProviderDAO;
import org.apache.syncope.core.persistence.api.entity.EntityFactory;
import org.apache.syncope.core.persistence.api.entity.authentication.SAML2ServiceProvider;
import org.apache.syncope.core.persistence.api.entity.policy.AccessPolicy;
import org.apache.syncope.core.persistence.api.entity.policy.AttrReleasePolicy;
import org.apache.syncope.core.persistence.api.entity.policy.AuthenticationPolicy;
import org.apache.syncope.core.persistence.api.entity.policy.Policy;
import org.apache.syncope.core.provisioning.api.data.SAML2ServiceProviderDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SAML2ServiceProviderDataBinderImpl implements SAML2ServiceProviderDataBinder {

    @Autowired
    private SAML2ServiceProviderDAO saml2ServiceProviderDAO;

    @Autowired
    private EntityFactory entityFactory;

    @Autowired
    private PolicyDAO policyDAO;

    @Override
    public SAML2ServiceProvider create(final SAML2ServiceProviderTO applicationTO) {
        return update(entityFactory.newEntity(SAML2ServiceProvider.class), applicationTO);
    }

    @Override
    public SAML2ServiceProvider update(
            final SAML2ServiceProvider toBeUpdated,
            final SAML2ServiceProviderTO applicationTO) {

        SAML2ServiceProvider application = saml2ServiceProviderDAO.save(toBeUpdated);

        application.setDescription(applicationTO.getDescription());
        application.setName(applicationTO.getName());
        application.setEntityId(applicationTO.getEntityId());
        application.setMetadataLocation(applicationTO.getMetadataLocation());

        if (applicationTO.getAuthenticationPolicy() == null) {
            application.setAuthenticationPolicy(null);
        } else {
            Policy policy = policyDAO.find(applicationTO.getAuthenticationPolicy());
            if (policy instanceof AuthenticationPolicy) {
                application.setAuthenticationPolicy((AuthenticationPolicy) policy);
            } else {
                SyncopeClientException sce = SyncopeClientException.build(ClientExceptionType.InvalidPolicy);
                sce.getElements().add("Expected " + AuthenticationPolicy.class.getSimpleName()
                        + ", found " + policy.getClass().getSimpleName());
                throw sce;
            }
        }

        if (applicationTO.getAccessPolicy() == null) {
            application.setAccessPolicy(null);
        } else {
            Policy policy = policyDAO.find(applicationTO.getAccessPolicy());
            if (policy instanceof AccessPolicy) {
                application.setAccessPolicy((AccessPolicy) policy);
            } else {
                SyncopeClientException sce = SyncopeClientException.build(ClientExceptionType.InvalidPolicy);
                sce.getElements().add("Expected " + AccessPolicy.class.getSimpleName()
                        + ", found " + policy.getClass().getSimpleName());
                throw sce;
            }
        }

        if (applicationTO.getAttrReleasePolicy() == null) {
            application.setAttrReleasePolicy(null);
        } else {
            Policy policy = policyDAO.find(applicationTO.getAttrReleasePolicy());
            if (policy instanceof AttrReleasePolicy) {
                application.setAttrReleasePolicy((AttrReleasePolicy) policy);
            } else {
                SyncopeClientException sce = SyncopeClientException.build(ClientExceptionType.InvalidPolicy);
                sce.getElements().add("Expected " + AttrReleasePolicy.class.getSimpleName()
                        + ", found " + policy.getClass().getSimpleName());
                throw sce;
            }
        }
        return application;
    }

    @Override
    public SAML2ServiceProviderTO getClientApplicationTO(final SAML2ServiceProvider sp) {
        SAML2ServiceProviderTO applicationTO = new SAML2ServiceProviderTO();

        applicationTO.setKey(sp.getKey());
        applicationTO.setDescription(sp.getDescription());
        applicationTO.setEntityId(sp.getEntityId());
        applicationTO.setMetadataLocation(sp.getMetadataLocation());
        applicationTO.setName(sp.getName());

        applicationTO.setAuthenticationPolicy(sp.getAuthenticationPolicy() == null
                ? null : sp.getAuthenticationPolicy().getKey());
        applicationTO.setAccessPolicy(sp.getAccessPolicy() == null
                ? null : sp.getAccessPolicy().getKey());
        applicationTO.setAttrReleasePolicy(sp.getAttrReleasePolicy() == null
                ? null : sp.getAttrReleasePolicy().getKey());

        return applicationTO;
    }
}

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
import org.apache.syncope.core.persistence.api.entity.policy.Policy;
import org.apache.syncope.core.provisioning.api.data.SAML2ServiceProviderDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.syncope.core.persistence.api.entity.policy.AuthPolicy;

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
        application.setMetadataSignatureLocation(applicationTO.getMetadataLocation());
        application.setSignAssertions(applicationTO.isSignAssertions());
        application.setSignResponses(applicationTO.isSignResponses());
        application.setEncryptionOptional(applicationTO.isEncryptionOptional());
        application.setEncryptAssertions(applicationTO.isEncryptAssertions());
        application.setRequiredAuthenticationContextClass(applicationTO.getRequiredAuthenticationContextClass());
        application.setRequiredNameIdFormat(applicationTO.getRequiredNameIdFormat());
        application.setSkewAllowance(applicationTO.getSkewAllowance());
        application.setNameIdQualifier(applicationTO.getNameIdQualifier());
        application.setAssertionAudiences(applicationTO.getAssertionAudiences());
        application.setServiceProviderNameIdQualifier(applicationTO.getServiceProviderNameIdQualifier());

        if (applicationTO.getAuthPolicy() == null) {
            application.setAuthPolicy(null);
        } else {
            Policy policy = policyDAO.find(applicationTO.getAuthPolicy());
            if (policy instanceof AuthPolicy) {
                application.setAuthPolicy((AuthPolicy) policy);
            } else {
                SyncopeClientException sce = SyncopeClientException.build(ClientExceptionType.InvalidPolicy);
                sce.getElements().add("Expected " + AuthPolicy.class.getSimpleName()
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

        applicationTO.setName(sp.getName());
        applicationTO.setKey(sp.getKey());
        applicationTO.setDescription(sp.getDescription());
        applicationTO.setEntityId(sp.getEntityId());
        applicationTO.setMetadataLocation(sp.getMetadataLocation());
        applicationTO.setMetadataSignatureLocation(sp.getMetadataLocation());
        applicationTO.setSignAssertions(sp.isSignAssertions());
        applicationTO.setSignResponses(sp.isSignResponses());
        applicationTO.setEncryptionOptional(sp.isEncryptionOptional());
        applicationTO.setEncryptAssertions(sp.isEncryptAssertions());
        applicationTO.setRequiredAuthenticationContextClass(sp.getRequiredAuthenticationContextClass());
        applicationTO.setRequiredNameIdFormat(sp.getRequiredNameIdFormat());
        applicationTO.setSkewAllowance(sp.getSkewAllowance());
        applicationTO.setNameIdQualifier(sp.getNameIdQualifier());
        applicationTO.setAssertionAudiences(sp.getAssertionAudiences());
        applicationTO.setServiceProviderNameIdQualifier(sp.getServiceProviderNameIdQualifier());

        applicationTO.setAuthPolicy(sp.getAuthPolicy() == null
                ? null : sp.getAuthPolicy().getKey());
        applicationTO.setAccessPolicy(sp.getAccessPolicy() == null
                ? null : sp.getAccessPolicy().getKey());
        applicationTO.setAttrReleasePolicy(sp.getAttrReleasePolicy() == null
                ? null : sp.getAttrReleasePolicy().getKey());

        return applicationTO;
    }
}

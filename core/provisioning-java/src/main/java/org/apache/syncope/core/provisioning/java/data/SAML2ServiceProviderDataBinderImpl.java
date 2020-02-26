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

import org.apache.syncope.common.lib.to.SAML2ServiceProviderTO;
import org.apache.syncope.core.persistence.api.dao.authentication.AuthenticationPolicyDAO;
import org.apache.syncope.core.persistence.api.dao.authentication.SAML2ServiceProviderDAO;
import org.apache.syncope.core.persistence.api.entity.EntityFactory;
import org.apache.syncope.core.persistence.api.entity.authentication.SAML2ServiceProvider;
import org.apache.syncope.core.persistence.api.entity.policy.AuthenticationPolicy;
import org.apache.syncope.core.provisioning.api.data.SAML2ServiceProviderDataBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SAML2ServiceProviderDataBinderImpl implements SAML2ServiceProviderDataBinder {
    private static final Logger LOG = LoggerFactory.getLogger(SAML2ServiceProviderDataBinderImpl.class);

    @Autowired
    private SAML2ServiceProviderDAO saml2ServiceProviderDAO;

    @Autowired
    private EntityFactory entityFactory;

    @Autowired
    private AuthenticationPolicyDAO authenticationPolicyDAO;

    @Override
    public SAML2ServiceProvider create(final SAML2ServiceProviderTO applicationTO) {
        return update(entityFactory.newEntity(SAML2ServiceProvider.class), applicationTO);
    }

    @Override
    public SAML2ServiceProvider update(final SAML2ServiceProvider toBeUpdated,
                                       final SAML2ServiceProviderTO applicationTO) {
        SAML2ServiceProvider application = saml2ServiceProviderDAO.save(toBeUpdated);

        application.setDescription(applicationTO.getDescription());
        application.setName(applicationTO.getName());
        application.setEntityId(applicationTO.getEntityId());
        application.setMetadataLocation(applicationTO.getMetadataLocation());

        AuthenticationPolicy authenticationPolicy = authenticationPolicyDAO.
            find(applicationTO.getAuthenticationPolicy().getKey());
        application.setAuthenticationPolicy(authenticationPolicy);

        return application;
    }

    @Override
    public SAML2ServiceProviderTO getClientApplicationTO(final SAML2ServiceProvider serviceProvider) {
        SAML2ServiceProviderTO applicationTO = new SAML2ServiceProviderTO();

        applicationTO.setKey(serviceProvider.getKey());
        applicationTO.setDescription(serviceProvider.getDescription());
        applicationTO.setEntityId(serviceProvider.getEntityId());
        applicationTO.setMetadataLocation(serviceProvider.getMetadataLocation());
        applicationTO.setName(serviceProvider.getName());

        return applicationTO;
    }
}

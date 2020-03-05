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
package org.apache.syncope.core.logic.saml;

import org.apache.syncope.common.lib.to.client.SAML2SPTO;
import org.apache.syncope.common.lib.types.IdRepoEntitlement;
import org.apache.syncope.core.logic.AbstractClientAppLogic;
import org.apache.syncope.core.persistence.api.dao.NotFoundException;
import org.apache.syncope.core.persistence.api.dao.authentication.SAML2SPDAO;
import org.apache.syncope.core.persistence.api.entity.authentication.SAML2SP;
import org.apache.syncope.core.provisioning.api.data.SAML2SPDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.syncope.common.lib.types.AMEntitlement;

@Component
public class SAML2SPLogic extends AbstractClientAppLogic<SAML2SPTO> {

    @Autowired
    private SAML2SPDAO saml2ServiceProviderDAO;

    @Autowired
    private SAML2SPDataBinder binder;

    @Override
    @PreAuthorize("hasRole('" + AMEntitlement.SAML2_SERVICE_PROVIDER_DELETE + "')")
    public SAML2SPTO delete(final String key) {
        SAML2SP application = saml2ServiceProviderDAO.find(key);
        if (application == null) {
            LOG.error("Could not find application '" + key + '\'');

            throw new NotFoundException(key);
        }

        SAML2SPTO deleted = binder.getClientApplicationTO(application);
        saml2ServiceProviderDAO.delete(key);
        return deleted;
    }

    @Override
    @PreAuthorize("hasRole('" + AMEntitlement.SAML2_SERVICE_PROVIDER_LIST + "')")
    @Transactional(readOnly = true)
    public List<SAML2SPTO> list() {
        return saml2ServiceProviderDAO.findAll().stream()
                .map(binder::getClientApplicationTO).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('" + AMEntitlement.SAML2_SERVICE_PROVIDER_READ + "')")
    @Transactional(readOnly = true)
    @Override
    public SAML2SPTO read(final String key) {
        SAML2SP application = saml2ServiceProviderDAO.find(key);
        if (application == null) {
            LOG.error("Could not find application '" + key + '\'');

            throw new NotFoundException(key);
        }

        return binder.getClientApplicationTO(application);
    }

    @Override
    @PreAuthorize("hasRole('" + AMEntitlement.SAML2_SERVICE_PROVIDER_CREATE + "')")
    public SAML2SPTO create(final SAML2SPTO applicationTO) {
        return binder.getClientApplicationTO(saml2ServiceProviderDAO.save(binder.create(applicationTO)));
    }

    @Override
    @PreAuthorize("hasRole('" + IdRepoEntitlement.APPLICATION_UPDATE + "')")
    public SAML2SPTO update(final SAML2SPTO applicationTO) {
        SAML2SP application = saml2ServiceProviderDAO.find(applicationTO.getKey());
        if (application == null) {
            LOG.error("Could not find application '" + applicationTO.getKey() + '\'');
            throw new NotFoundException(applicationTO.getKey());
        }

        return binder.getClientApplicationTO(saml2ServiceProviderDAO.save(binder.update(application, applicationTO)));
    }
}

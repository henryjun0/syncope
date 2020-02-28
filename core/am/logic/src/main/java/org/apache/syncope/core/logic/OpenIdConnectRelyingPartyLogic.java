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

package org.apache.syncope.core.logic;

import org.apache.syncope.common.lib.to.OpenIdConnectRelyingPartyTO;
import org.apache.syncope.common.lib.types.IdRepoEntitlement;
import org.apache.syncope.core.persistence.api.dao.NotFoundException;
import org.apache.syncope.core.persistence.api.dao.authentication.OpenIdConnectRelyingPartyDAO;
import org.apache.syncope.core.persistence.api.entity.authentication.OpenIdConnectRelyingParty;
import org.apache.syncope.core.provisioning.api.data.OpenIdConnectRelyingPartyDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.syncope.common.lib.types.AMEntitlement;

@Component
public class OpenIdConnectRelyingPartyLogic extends AbstractClientApplicationLogic<OpenIdConnectRelyingPartyTO> {
    @Autowired
    private OpenIdConnectRelyingPartyDAO openIdConnectRelyingPartyDAO;

    @Autowired
    private OpenIdConnectRelyingPartyDataBinder binder;

    @PreAuthorize("hasRole('" + AMEntitlement.OIDC_RELYING_PARTY_READ + "')")
    @Transactional(readOnly = true)
    @Override
    public OpenIdConnectRelyingPartyTO read(final String key) {
        OpenIdConnectRelyingParty application = openIdConnectRelyingPartyDAO.find(key);
        if (application == null) {
            LOG.error("Could not find application '" + key + '\'');

            throw new NotFoundException(key);
        }

        return binder.getClientApplicationTO(application);
    }

    @PreAuthorize("hasRole('" + AMEntitlement.OIDC_RELYING_PARTY_LIST + "')")
    @Transactional(readOnly = true)
    @Override
    public List<OpenIdConnectRelyingPartyTO> list() {
        return openIdConnectRelyingPartyDAO.findAll()
            .stream().map(binder::getClientApplicationTO).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('" + AMEntitlement.OIDC_RELYING_PARTY_CREATE + "')")
    @Override
    public OpenIdConnectRelyingPartyTO create(final OpenIdConnectRelyingPartyTO applicationTO) {
        return binder.getClientApplicationTO(openIdConnectRelyingPartyDAO.save(binder.create(applicationTO)));
    }

    @Override
    @PreAuthorize("hasRole('" + IdRepoEntitlement.APPLICATION_UPDATE + "')")
    public OpenIdConnectRelyingPartyTO update(final OpenIdConnectRelyingPartyTO applicationTO) {
        OpenIdConnectRelyingParty application = openIdConnectRelyingPartyDAO.find(applicationTO.getKey());
        if (application == null) {
            LOG.error("Could not find application '" + applicationTO.getKey() + '\'');
            throw new NotFoundException(applicationTO.getKey());
        }
        return binder.getClientApplicationTO(
            openIdConnectRelyingPartyDAO.save(binder.update(application, applicationTO)));
    }

    @Override
    @PreAuthorize("hasRole('" + AMEntitlement.OIDC_RELYING_PARTY_DELETE + "')")
    public OpenIdConnectRelyingPartyTO delete(final String key) {
        OpenIdConnectRelyingParty application = openIdConnectRelyingPartyDAO.find(key);
        if (application == null) {
            LOG.error("Could not find application '" + key + '\'');

            throw new NotFoundException(key);
        }

        OpenIdConnectRelyingPartyTO deleted = binder.getClientApplicationTO(application);
        openIdConnectRelyingPartyDAO.delete(key);
        return deleted;
    }
    
}

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

import org.apache.syncope.common.lib.to.client.OIDCRelyingPartyTO;
import org.apache.syncope.common.lib.types.IdRepoEntitlement;
import org.apache.syncope.core.persistence.api.dao.NotFoundException;
import org.apache.syncope.core.persistence.api.dao.authentication.OIDCRelyingPartyDAO;
import org.apache.syncope.core.persistence.api.entity.authentication.OIDCRelyingParty;
import org.apache.syncope.core.provisioning.api.data.OIDCRelyingPartyDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.syncope.common.lib.types.AMEntitlement;

@Component
public class OIDCRelyingPartyLogic extends AbstractClientAppLogic<OIDCRelyingPartyTO> {

    @Autowired
    private OIDCRelyingPartyDAO oidcRelyingPartyDAO;

    @Autowired
    private OIDCRelyingPartyDataBinder binder;

    @PreAuthorize("hasRole('" + AMEntitlement.OIDC_RELYING_PARTY_READ + "')")
    @Transactional(readOnly = true)
    @Override
    public OIDCRelyingPartyTO read(final String key) {
        OIDCRelyingParty application = oidcRelyingPartyDAO.find(key);
        if (application == null) {
            LOG.error("Could not find application '" + key + '\'');

            throw new NotFoundException(key);
        }

        return binder.getClientApplicationTO(application);
    }

    @PreAuthorize("hasRole('" + AMEntitlement.OIDC_RELYING_PARTY_LIST + "')")
    @Transactional(readOnly = true)
    @Override
    public List<OIDCRelyingPartyTO> list() {
        return oidcRelyingPartyDAO.findAll()
                .stream().map(binder::getClientApplicationTO).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('" + AMEntitlement.OIDC_RELYING_PARTY_CREATE + "')")
    @Override
    public OIDCRelyingPartyTO create(final OIDCRelyingPartyTO applicationTO) {
        return binder.getClientApplicationTO(oidcRelyingPartyDAO.save(binder.create(applicationTO)));
    }

    @Override
    @PreAuthorize("hasRole('" + IdRepoEntitlement.APPLICATION_UPDATE + "')")
    public OIDCRelyingPartyTO update(final OIDCRelyingPartyTO applicationTO) {
        OIDCRelyingParty application = oidcRelyingPartyDAO.find(applicationTO.getKey());
        if (application == null) {
            LOG.error("Could not find application '" + applicationTO.getKey() + '\'');
            throw new NotFoundException(applicationTO.getKey());
        }
        return binder.getClientApplicationTO(
                oidcRelyingPartyDAO.save(binder.update(application, applicationTO)));
    }

    @Override
    @PreAuthorize("hasRole('" + AMEntitlement.OIDC_RELYING_PARTY_DELETE + "')")
    public OIDCRelyingPartyTO delete(final String key) {
        OIDCRelyingParty application = oidcRelyingPartyDAO.find(key);
        if (application == null) {
            LOG.error("Could not find application '" + key + '\'');

            throw new NotFoundException(key);
        }

        OIDCRelyingPartyTO deleted = binder.getClientApplicationTO(application);
        oidcRelyingPartyDAO.delete(key);
        return deleted;
    }

}

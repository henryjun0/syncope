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
package org.apache.syncope.core.rest.cxf.service;

import org.apache.syncope.common.lib.to.client.ClientAppTO;
import org.apache.syncope.common.lib.to.client.OIDCRelyingPartyTO;
import org.apache.syncope.common.lib.to.client.SAML2ServiceProviderTO;
import org.apache.syncope.common.rest.api.RESTHeaders;
import org.apache.syncope.common.rest.api.service.ClientAppService;
import org.apache.syncope.core.logic.AbstractClientAppLogic;
import org.apache.syncope.core.logic.oidc.OIDCRelyingPartyLogic;
import org.apache.syncope.core.logic.saml.SAML2ServiceProviderLogic;
import org.apache.syncope.core.persistence.api.dao.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientAppServiceImpl extends AbstractServiceImpl implements ClientAppService {

    @Autowired
    private SAML2ServiceProviderLogic saml2Logic;

    @Autowired
    private OIDCRelyingPartyLogic oidcLogic;

    private AbstractClientAppLogic<ClientAppTO> getLogicFor(final ClientAppTO clientApp) {
        if (clientApp instanceof SAML2ServiceProviderTO) {
            return (AbstractClientAppLogic) this.saml2Logic;
        }
        if (clientApp instanceof OIDCRelyingPartyTO) {
            return (AbstractClientAppLogic) this.oidcLogic;
        }
        throw new IllegalArgumentException("Unable to determine type for " + clientApp.getName());
    }

    @Override
    public List<ClientAppTO> list() {
        List<ClientAppTO> applications = new ArrayList<>(saml2Logic.list());
        applications.addAll(oidcLogic.list());
        return applications;
    }

    @Override
    public ClientAppTO read(final String key) {
        try {
            return this.saml2Logic.read(key);
        } catch (NotFoundException e) {
            return this.oidcLogic.read(key);
        }
    }

    @Override
    public Response create(final ClientAppTO applicationTO) {
        ClientAppTO created = getLogicFor(applicationTO).create(applicationTO);
        URI location = uriInfo.getAbsolutePathBuilder().path(created.getKey()).build();
        return Response.created(location).
            header(RESTHeaders.RESOURCE_KEY, created.getKey()).
            build();
    }

    @Override
    public void update(final ClientAppTO applicationTO) {
        getLogicFor(applicationTO).update(applicationTO);
    }

    @Override
    public void delete(final String key) {
        ClientAppTO app = read(key);
        getLogicFor(app).delete(key);
    }

}

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

import org.apache.syncope.common.rest.api.service.ClientAppService;
import org.apache.syncope.common.lib.to.client.ClientAppTO;
import org.apache.syncope.common.rest.api.RESTHeaders;
import org.apache.syncope.core.logic.AbstractClientAppLogic;

import javax.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

public abstract class AbstractClientAppServiceImpl<T extends ClientAppTO> extends AbstractServiceImpl
        implements ClientAppService<T> {

    protected abstract AbstractClientAppLogic<T> getLogic();

    @Override
    public List<T> list() {
        return getLogic().list();
    }

    @Override
    public T read(final String key) {
        return getLogic().read(key);
    }

    @Override
    public Response create(final T applicationTO) {
        T created = getLogic().create(applicationTO);
        URI location = uriInfo.getAbsolutePathBuilder().path(created.getKey()).build();
        return Response.created(location).
                header(RESTHeaders.RESOURCE_KEY, created.getKey()).
                build();
    }

    @Override
    public void update(final T applicationTO) {
        getLogic().update(applicationTO);
    }

    @Override
    public void delete(final String key) {
        getLogic().delete(key);
    }

}

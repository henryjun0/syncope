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
package org.apache.syncope.client.console.rest;

import org.apache.syncope.common.lib.to.client.OIDCRelyingPartyTO;
import org.apache.syncope.common.rest.api.service.oidc.OIDCRelyingPartyService;

import java.util.List;

/**
 * Console client for invoking Rest Client Application's services.
 */
public class OIDCRelyingPartyRestClient extends BaseRestClient {

    private static final long serialVersionUID = -3161863874876938094L;

    public static void delete(final String key) {
        getService(OIDCRelyingPartyService.class).delete(key);
    }

    public static OIDCRelyingPartyTO read(final String key) {
        return getService(OIDCRelyingPartyService.class).read(key);
    }

    public static void update(final OIDCRelyingPartyTO applicationTO) {
        getService(OIDCRelyingPartyService.class).update(applicationTO);
    }

    public static void create(final OIDCRelyingPartyTO applicationTO) {
        getService(OIDCRelyingPartyService.class).create(applicationTO);
    }

    public static List<OIDCRelyingPartyTO> list() {
        return getService(OIDCRelyingPartyService.class).list();
    }

}

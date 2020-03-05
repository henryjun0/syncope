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

package org.apache.syncope.core.rest.cxf.service;

import org.apache.syncope.common.rest.api.service.OIDCRelyingPartyService;
import org.apache.syncope.common.lib.to.client.OIDCRelyingPartyTO;
import org.apache.syncope.core.logic.AbstractClientAppLogic;
import org.apache.syncope.core.logic.OIDCRelyingPartyLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OIDCRelyingPartyServiceImpl
    extends AbstractClientAppServiceImpl<OIDCRelyingPartyTO>
    implements OIDCRelyingPartyService {

    @Autowired
    private OIDCRelyingPartyLogic logic;

    @Override
    protected AbstractClientAppLogic getLogic() {
        return this.logic;
    }
}

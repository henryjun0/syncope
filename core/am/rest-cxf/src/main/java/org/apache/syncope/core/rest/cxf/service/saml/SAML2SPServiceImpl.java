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

package org.apache.syncope.core.rest.cxf.service.saml;

import org.apache.syncope.common.rest.api.service.saml.SAML2SPService;
import org.apache.syncope.common.lib.to.client.SAML2SPTO;
import org.apache.syncope.core.logic.AbstractClientAppLogic;
import org.apache.syncope.core.logic.saml.SAML2SPLogic;
import org.apache.syncope.core.rest.cxf.service.AbstractClientAppServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SAML2SPServiceImpl
    extends AbstractClientAppServiceImpl<SAML2SPTO>
    implements SAML2SPService {
    
    @Autowired
    private SAML2SPLogic logic;

    @Override
    protected AbstractClientAppLogic getLogic() {
        return this.logic;
    }
}

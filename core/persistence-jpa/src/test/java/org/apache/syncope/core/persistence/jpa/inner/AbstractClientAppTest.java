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
package org.apache.syncope.core.persistence.jpa.inner;

import java.util.List;
import java.util.Map;
import org.apache.syncope.common.lib.access.DefaultAccessPolicyConf;
import org.apache.syncope.common.lib.authentication.policy.DefaultAuthenticationPolicyConf;
import org.apache.syncope.common.lib.types.AMImplementationType;
import org.apache.syncope.common.lib.types.ImplementationEngine;
import org.apache.syncope.core.persistence.api.dao.ImplementationDAO;
import org.apache.syncope.core.persistence.api.entity.Implementation;
import org.apache.syncope.core.persistence.api.entity.policy.AccessPolicy;
import org.apache.syncope.core.persistence.api.entity.policy.AuthenticationPolicy;
import org.apache.syncope.core.persistence.api.dao.PolicyDAO;
import org.apache.syncope.core.persistence.jpa.AbstractTest;
import org.apache.syncope.core.provisioning.api.serialization.POJOHelper;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractClientAppTest extends AbstractTest {

    @Autowired
    protected PolicyDAO policyDAO;

    @Autowired
    protected ImplementationDAO implementationDAO;

    protected AccessPolicy buildAndSaveAccessPolicy() {
        AccessPolicy accessPolicy = entityFactory.newEntity(AccessPolicy.class);
        accessPolicy.setName("AccessPolicyTest");
        accessPolicy.setDescription("This is a sample access policy");

        DefaultAccessPolicyConf conf = new DefaultAccessPolicyConf();
        conf.setEnabled(true);
        conf.setName("Example Access Policy for an application");
        conf.getRequiredAttributes().putAll(Map.of("attribute1", List.of("value1", "value2")));
        conf.setSsoEnabled(false);

        Implementation type = entityFactory.newEntity(Implementation.class);
        type.setKey("AccessPolicyConfKey");
        type.setEngine(ImplementationEngine.JAVA);
        type.setType(AMImplementationType.ACCESS_POLICY_CONFIGURATIONS);
        type.setBody(POJOHelper.serialize(conf));
        type = implementationDAO.save(type);

        accessPolicy.addConfiguration(type);
        return policyDAO.save(accessPolicy);

    }

    protected AuthenticationPolicy buildAndSaveAuthenticationPolicy() {
        AuthenticationPolicy authenticationPolicy = entityFactory.newEntity(AuthenticationPolicy.class);
        authenticationPolicy.setName("AuthenticationPolicyTest");
        authenticationPolicy.setDescription("This is a sample authentication policy");

        DefaultAuthenticationPolicyConf conf = new DefaultAuthenticationPolicyConf();
        conf.getAuthenticationModules().addAll(List.of("LdapAuthentication1", "DatabaseAuthentication2"));

        Implementation type = entityFactory.newEntity(Implementation.class);
        type.setKey("AuthPolicyConfKey");
        type.setEngine(ImplementationEngine.JAVA);
        type.setType(AMImplementationType.AUTH_POLICY_CONFIGURATIONS);
        type.setBody(POJOHelper.serialize(conf));
        type = implementationDAO.save(type);

        authenticationPolicy.addConfiguration(type);
        return policyDAO.save(authenticationPolicy);
    }

}

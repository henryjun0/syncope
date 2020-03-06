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

import org.apache.syncope.common.lib.attrs.AllowedAttrReleasePolicyConf;
import org.apache.syncope.common.lib.types.AMImplementationType;
import org.apache.syncope.common.lib.types.ImplementationEngine;
import org.apache.syncope.core.persistence.api.dao.ImplementationDAO;
import org.apache.syncope.core.persistence.api.dao.authentication.AttrReleasePolicyDAO;
import org.apache.syncope.core.persistence.api.entity.Implementation;
import org.apache.syncope.core.persistence.api.entity.policy.AttrReleasePolicy;
import org.apache.syncope.core.persistence.jpa.AbstractTest;
import org.apache.syncope.core.provisioning.api.serialization.POJOHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Transactional("Master")
public class AttrReleasePolicyTest extends AbstractTest {

    @Autowired
    private AttrReleasePolicyDAO attrReleasePolicyDAO;

    @Autowired
    private ImplementationDAO implementationDAO;

    @Test
    public void find() {
        AttrReleasePolicy policy = attrReleasePolicyDAO.find("019935c7-deb3-40b3-8a9a-683037e523a2");
        assertNull(policy);
        policy = attrReleasePolicyDAO.find("319935c7-deb3-40b3-8a9a-683037e523a2");
        assertNotNull(policy);
        policy = attrReleasePolicyDAO.find(UUID.randomUUID().toString());
        assertNull(policy);
    }

    @Test
    public void findAll() {
        List<AttrReleasePolicy> policies = attrReleasePolicyDAO.findAll();
        assertNotNull(policies);
        assertEquals(2, policies.size());
    }

    @Test
    public void save() {
        int beforeCount = attrReleasePolicyDAO.findAll().size();
        AttrReleasePolicy policy = entityFactory.newEntity(AttrReleasePolicy.class);
        policy.setName("AttrReleasePolicyAllowEverything");
        policy.setDescription("This is a sample attr release policy that releases everything");

        AllowedAttrReleasePolicyConf conf = new AllowedAttrReleasePolicyConf();
        conf.setAllowedAttributes(List.of("*"));
        conf.setName("AttrReleasePolicyAllowEverything");

        Implementation type = entityFactory.newEntity(Implementation.class);
        type.setKey("AttrReleasePolicyAllowEverything");
        type.setEngine(ImplementationEngine.JAVA);
        type.setType(AMImplementationType.ATTR_RELEASE_POLICY_CONFIGURATIONS);
        type.setBody(POJOHelper.serialize(conf));
        type = implementationDAO.save(type);

        policy.addConfiguration(type);
        attrReleasePolicyDAO.save(policy);

        assertNotNull(policy);
        assertNotNull(policy.getKey());

        int afterCount = attrReleasePolicyDAO.findAll().size();
        assertEquals(afterCount, beforeCount + 1);
    }

    @Test
    public void delete() {
        AttrReleasePolicy policy = attrReleasePolicyDAO.find("319935c7-deb3-40b3-8a9a-683037e523a2");
        assertNotNull(policy);
        attrReleasePolicyDAO.delete("319935c7-deb3-40b3-8a9a-683037e523a2");
        policy = attrReleasePolicyDAO.find("319935c7-deb3-40b3-8a9a-683037e523a2");
        assertNull(policy);
    }
}

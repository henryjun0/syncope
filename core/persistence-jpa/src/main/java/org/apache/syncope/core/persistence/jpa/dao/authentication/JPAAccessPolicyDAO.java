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
package org.apache.syncope.core.persistence.jpa.dao.authentication;

import org.apache.syncope.core.persistence.api.dao.authentication.AccessPolicyDAO;
import org.apache.syncope.core.persistence.api.entity.policy.AccessPolicy;
import org.apache.syncope.core.persistence.jpa.dao.AbstractDAO;
import org.apache.syncope.core.persistence.jpa.entity.policy.JPAAccessPolicy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;

import java.util.List;

@Repository
public class JPAAccessPolicyDAO extends AbstractDAO<AccessPolicy> implements AccessPolicyDAO {

    @Override
    public AccessPolicy find(final String key) {
        return entityManager().find(JPAAccessPolicy.class, key);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccessPolicy> findAll() {
        TypedQuery<AccessPolicy> query = entityManager().createQuery(
            "SELECT e FROM " + JPAAccessPolicy.class.getSimpleName() + " e", AccessPolicy.class);

        return query.getResultList();
    }

    @Override
    public AccessPolicy save(final AccessPolicy policy) {
        return entityManager().merge(policy);
    }

    @Override
    public void delete(final String key) {
        AccessPolicy policy = find(key);
        if (policy == null) {
            return;
        }

        delete(policy);
    }

    @Override
    public void delete(final AccessPolicy policy) {
        entityManager().remove(policy);
    }

}

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

import org.apache.syncope.core.persistence.api.dao.authentication.AuthenticationPolicyDAO;
import org.apache.syncope.core.persistence.api.entity.policy.AuthenticationPolicy;
import org.apache.syncope.core.persistence.jpa.dao.AbstractDAO;
import org.apache.syncope.core.persistence.jpa.entity.policy.JPAAuthenticationPolicy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;

import java.util.List;

@Repository
public class JPAAuthenticationPolicyDAO extends AbstractDAO<AuthenticationPolicy> implements AuthenticationPolicyDAO {

    @Override
    public AuthenticationPolicy find(final String key) {
        return entityManager().find(JPAAuthenticationPolicy.class, key);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AuthenticationPolicy> findAll() {
        TypedQuery<AuthenticationPolicy> query = entityManager().createQuery(
            "SELECT e FROM " + JPAAuthenticationPolicy.class.getSimpleName() + " e", AuthenticationPolicy.class);

        return query.getResultList();
    }

    @Override
    public AuthenticationPolicy save(final AuthenticationPolicy policy) {
        return entityManager().merge(policy);
    }

    @Override
    public void delete(final String key) {
        AuthenticationPolicy policy = find(key);
        if (policy == null) {
            return;
        }

        delete(policy);
    }

    @Override
    public void delete(final AuthenticationPolicy policy) {
        entityManager().remove(policy);
    }

}

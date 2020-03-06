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

import org.apache.syncope.core.persistence.api.dao.authentication.AttrReleasePolicyDAO;
import org.apache.syncope.core.persistence.api.entity.policy.AttrReleasePolicy;
import org.apache.syncope.core.persistence.jpa.dao.AbstractDAO;
import org.apache.syncope.core.persistence.jpa.entity.policy.JPAAttrReleasePolicy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;

import java.util.List;

@Repository
public class JPAAttrReleasePolicyDAO extends AbstractDAO<AttrReleasePolicy> implements AttrReleasePolicyDAO {

    @Override
    public AttrReleasePolicy find(final String key) {
        return entityManager().find(JPAAttrReleasePolicy.class, key);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AttrReleasePolicy> findAll() {
        TypedQuery<AttrReleasePolicy> query = entityManager().createQuery(
            "SELECT e FROM " + JPAAttrReleasePolicy.class.getSimpleName() + " e", AttrReleasePolicy.class);

        return query.getResultList();
    }

    @Override
    public AttrReleasePolicy save(final AttrReleasePolicy policy) {
        return entityManager().merge(policy);
    }

    @Override
    public void delete(final String key) {
        AttrReleasePolicy policy = find(key);
        if (policy == null) {
            return;
        }

        delete(policy);
    }

    @Override
    public void delete(final AttrReleasePolicy policy) {
        entityManager().remove(policy);
    }

}

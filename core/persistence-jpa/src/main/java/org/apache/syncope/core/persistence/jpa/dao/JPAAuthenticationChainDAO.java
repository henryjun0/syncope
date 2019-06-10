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
package org.apache.syncope.core.persistence.jpa.dao;

import java.util.List;
import javax.persistence.TypedQuery;
import org.apache.syncope.core.persistence.api.dao.AuthenticationChainDAO;
import org.apache.syncope.core.persistence.api.entity.Implementation;
import org.apache.syncope.core.persistence.api.entity.authentication.AuthenticationChain;
import org.apache.syncope.core.persistence.jpa.entity.authentication.JPAAuthenticationChain;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JPAAuthenticationChainDAO extends AbstractDAO<AuthenticationChain> implements AuthenticationChainDAO {

    @Override
    public AuthenticationChain find(final String key) {
        return entityManager().find(JPAAuthenticationChain.class, key);
    }

    @Override
    public List<AuthenticationChain> findByRule(final Implementation configuration) {
        TypedQuery<AuthenticationChain> query = entityManager().createQuery(
                "SELECT e FROM " + JPAAuthenticationChain.class.getSimpleName() + " e "
                + "WHERE :rule MEMBER OF e.rules", AuthenticationChain.class);
        query.setParameter("rule", configuration);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<AuthenticationChain> findAll() {
        TypedQuery<AuthenticationChain> query = entityManager().createQuery(
                "SELECT e FROM " + JPAAuthenticationChain.class.getSimpleName() + " e", AuthenticationChain.class);

        return query.getResultList();
    }

    @Override
    public AuthenticationChain save(final AuthenticationChain authenticationChain) {
        return entityManager().merge(authenticationChain);
    }

    @Override
    public void delete(final String key) {
        AuthenticationChain authenticationChain = find(key);
        if (authenticationChain == null) {
            return;
        }

        delete(authenticationChain);
    }

    @Override
    public void delete(final AuthenticationChain authenticationChain) {
        entityManager().remove(authenticationChain);
    }

}

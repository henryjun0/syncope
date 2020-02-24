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

import org.apache.syncope.core.persistence.api.dao.authentication.OpenIdConnectRelyingPartyDAO;
import org.apache.syncope.core.persistence.api.entity.authentication.OpenIdConnectRelyingParty;
import org.apache.syncope.core.persistence.jpa.dao.AbstractDAO;
import org.apache.syncope.core.persistence.jpa.entity.authentication.JPAOpenIdConnectRelyingParty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import java.util.List;

@Repository
public class JPAOpenIdConnectRelyingPartyDAO extends AbstractDAO<OpenIdConnectRelyingParty>
    implements OpenIdConnectRelyingPartyDAO {

    @Override
    public OpenIdConnectRelyingParty find(final String key) {
        return entityManager().find(JPAOpenIdConnectRelyingParty.class, key);
    }

    @Override
    public OpenIdConnectRelyingParty findByName(final String name) {
        TypedQuery<OpenIdConnectRelyingParty> query = entityManager().createQuery(
            "SELECT e FROM " + JPAOpenIdConnectRelyingParty.class.getSimpleName() + " e WHERE e.name=:name", OpenIdConnectRelyingParty.class);
        query.setParameter("name", name);

        OpenIdConnectRelyingParty result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            LOG.debug("No OpenIdConnectRelyingParty found with name {}", name, e);
        }

        return result;
    }

    @Override
    public OpenIdConnectRelyingParty findByClientId(final String clientId) {
        TypedQuery<OpenIdConnectRelyingParty> query = entityManager().createQuery(
            "SELECT e FROM " + JPAOpenIdConnectRelyingParty.class.getSimpleName() + " e WHERE e.clientId=:clientId", OpenIdConnectRelyingParty.class);
        query.setParameter(clientId, clientId);

        OpenIdConnectRelyingParty result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            LOG.debug("No OpenIdConnectRelyingParty found with clientId {}", clientId, e);
        }

        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public List<OpenIdConnectRelyingParty> findAll() {
        TypedQuery<OpenIdConnectRelyingParty> query = entityManager().createQuery(
            "SELECT e FROM " + JPAOpenIdConnectRelyingParty.class.getSimpleName() + " e", OpenIdConnectRelyingParty.class);

        return query.getResultList();
    }

    @Override
    public OpenIdConnectRelyingParty save(final OpenIdConnectRelyingParty policy) {
        return entityManager().merge(policy);
    }

    @Override
    public void delete(final String key) {
        OpenIdConnectRelyingParty policy = find(key);
        if (policy == null) {
            return;
        }

        delete(policy);
    }

    @Override
    public void deleteByClientId(final String clientId) {
        OpenIdConnectRelyingParty policy = findByClientId(clientId);
        if (policy == null) {
            return;
        }
        delete(policy);
    }

    @Override
    public void delete(final OpenIdConnectRelyingParty policy) {
        entityManager().remove(policy);
    }
}

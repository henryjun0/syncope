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
package org.apache.syncope.core.persistence.jpa.dao.authentication;

import org.apache.syncope.core.persistence.api.dao.authentication.OIDCRelyingPartyDAO;
import org.apache.syncope.core.persistence.api.entity.authentication.OIDCRelyingParty;
import org.apache.syncope.core.persistence.jpa.dao.AbstractDAO;
import org.apache.syncope.core.persistence.jpa.entity.authentication.JPAOIDCRelyingParty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JPAOIDCRelyingPartyDAO extends AbstractDAO<OIDCRelyingParty>
        implements OIDCRelyingPartyDAO {

    @Override
    public OIDCRelyingParty find(final String key) {
        return entityManager().find(JPAOIDCRelyingParty.class, key);
    }

    @Override
    public OIDCRelyingParty findByName(final String name) {
        TypedQuery<OIDCRelyingParty> query = entityManager().createQuery(
                "SELECT e FROM " + JPAOIDCRelyingParty.class.getSimpleName()
                + " e WHERE e.name=:name", OIDCRelyingParty.class);
        query.setParameter("name", name);

        OIDCRelyingParty result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            LOG.debug("No OIDCRelyingParty found with name {}", name, e);
        }

        return result;
    }

    @Override
    public OIDCRelyingParty findByClientId(final String clientId) {
        TypedQuery<OIDCRelyingParty> query = entityManager().createQuery(
                "SELECT e FROM " + JPAOIDCRelyingParty.class.getSimpleName()
                + " e WHERE e.clientId=:clientId", OIDCRelyingParty.class);
        query.setParameter("clientId", clientId);

        OIDCRelyingParty result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            LOG.debug("No OIDCRelyingParty found with clientId {}", clientId, e);
        }

        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public List<OIDCRelyingParty> findAll() {
        TypedQuery<OIDCRelyingParty> query = entityManager().createQuery(
                "SELECT e FROM " + JPAOIDCRelyingParty.class.getSimpleName()
                + " e", OIDCRelyingParty.class);

        return query.getResultList();
    }

    @Override
    public OIDCRelyingParty save(final OIDCRelyingParty rp) {
        return entityManager().merge(rp);
    }

    @Override
    public void delete(final String key) {
        OIDCRelyingParty rpTO = find(key);
        if (rpTO == null) {
            return;
        }

        delete(rpTO);
    }

    @Override
    public void deleteByClientId(final String clientId) {
        OIDCRelyingParty rpTO = findByClientId(clientId);
        if (rpTO == null) {
            return;
        }
        delete(rpTO);
    }

    @Override
    public void delete(final OIDCRelyingParty rpTO) {
        entityManager().remove(rpTO);
    }
}

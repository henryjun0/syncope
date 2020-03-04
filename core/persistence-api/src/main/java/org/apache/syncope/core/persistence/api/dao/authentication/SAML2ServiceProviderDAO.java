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
package org.apache.syncope.core.persistence.api.dao.authentication;

import org.apache.syncope.core.persistence.api.dao.DAO;
import org.apache.syncope.core.persistence.api.entity.authentication.SAML2SP;

import java.util.List;

public interface SAML2ServiceProviderDAO extends DAO<SAML2SP> {

    SAML2SP find(String key);

    SAML2SP findByName(String name);

    SAML2SP findByEntityId(String clientId);

    List<SAML2SP> findAll();

    SAML2SP save(SAML2SP application);

    void delete(String key);

    void deleteByEntityId(String entityId);

    void delete(SAML2SP application);

}

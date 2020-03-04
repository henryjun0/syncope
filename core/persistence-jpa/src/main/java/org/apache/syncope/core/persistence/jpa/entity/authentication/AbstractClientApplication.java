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
package org.apache.syncope.core.persistence.jpa.entity.authentication;

import org.apache.syncope.core.persistence.api.entity.authentication.ClientApplication;
import org.apache.syncope.core.persistence.api.entity.policy.AuthenticationPolicy;
import org.apache.syncope.core.persistence.api.entity.policy.AccessPolicy;
import org.apache.syncope.core.persistence.jpa.entity.AbstractGeneratedKeyEntity;
import org.apache.syncope.core.persistence.jpa.entity.policy.JPAAuthenticationPolicy;
import org.apache.syncope.core.persistence.jpa.entity.policy.JPAAccessPolicy;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractClientApplication extends AbstractGeneratedKeyEntity implements ClientApplication {

    private static final long serialVersionUID = 7422422526695279794L;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private JPAAuthenticationPolicy authenticationPolicy;

    @ManyToOne(fetch = FetchType.EAGER)
    private JPAAccessPolicy accessPolicy;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public JPAAuthenticationPolicy getAuthenticationPolicy() {
        return authenticationPolicy;
    }

    @Override
    public void setAuthenticationPolicy(final AuthenticationPolicy authenticationPolicy) {
        checkType(authenticationPolicy, JPAAuthenticationPolicy.class);
        this.authenticationPolicy = (JPAAuthenticationPolicy) authenticationPolicy;
    }

    public JPAAccessPolicy getAccessPolicy() {
        return accessPolicy;
    }

    public void setAccessPolicy(final AccessPolicy accessPolicy) {
        checkType(accessPolicy, JPAAccessPolicy.class);
        this.accessPolicy = (JPAAccessPolicy) accessPolicy;
    }

}

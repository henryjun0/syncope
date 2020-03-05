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

package org.apache.syncope.common.lib.to.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "saml2ServiceProvider")
@XmlType
@Schema(allOf = { ClientAppTO.class })
public class SAML2ServiceProviderTO extends ClientAppTO {
    private static final long serialVersionUID = -6370888503924521351L;

    private String entityId;

    private String metadataLocation;

    @XmlTransient
    @JsonProperty("@class")
    @Schema(name = "@class", required = true, example = "org.apache.syncope.common.lib.to.SAML2ServiceProviderTO")
    @Override
    public String getDiscriminator() {
        return getClass().getName();
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(final String entityId) {
        this.entityId = entityId;
    }

    public String getMetadataLocation() {
        return metadataLocation;
    }

    public void setMetadataLocation(final String metadataLocation) {
        this.metadataLocation = metadataLocation;
    }


    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        SAML2ServiceProviderTO rhs = (SAML2ServiceProviderTO) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(this.entityId, rhs.entityId)
            .append(this.metadataLocation, rhs.metadataLocation)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(entityId)
            .append(metadataLocation)
            .toHashCode();
    }
}


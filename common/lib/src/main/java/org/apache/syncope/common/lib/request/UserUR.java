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
package org.apache.syncope.common.lib.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@XmlRootElement(name = "userUR")
@XmlType
@Schema(allOf = { AnyUR.class })
public class UserUR extends AnyUR {

    private static final long serialVersionUID = 2872795537911821448L;

    public static class Builder extends AnyUR.Builder<UserUR, Builder> {

        @Override
        protected UserUR newInstance() {
            return new UserUR();
        }

        public Builder username(final StringReplacePatchItem username) {
            getInstance().setUsername(username);
            return this;
        }

        public Builder password(final PasswordPatch password) {
            getInstance().setPassword(password);
            return this;
        }

        public Builder securityQuestion(final StringReplacePatchItem securityQuestion) {
            getInstance().setSecurityQuestion(securityQuestion);
            return this;
        }

        public Builder securityAnswer(final StringReplacePatchItem securityAnswer) {
            getInstance().setSecurityAnswer(securityAnswer);
            return this;
        }

        public Builder mustChangePassword(final BooleanReplacePatchItem mustChangePassword) {
            getInstance().setMustChangePassword(mustChangePassword);
            return this;
        }

        public Builder relationship(final RelationshipPatch relationship) {
            getInstance().getRelationships().add(relationship);
            return this;
        }

        public Builder relationships(final RelationshipPatch... relationships) {
            getInstance().getRelationships().addAll(Arrays.asList(relationships));
            return this;
        }

        public Builder relationships(final Collection<RelationshipPatch> relationships) {
            getInstance().getRelationships().addAll(relationships);
            return this;
        }

        public Builder membership(final MembershipPatch membership) {
            getInstance().getMemberships().add(membership);
            return this;
        }

        public Builder memberships(final MembershipPatch... memberships) {
            getInstance().getMemberships().addAll(Arrays.asList(memberships));
            return this;
        }

        public Builder memberships(final Collection<MembershipPatch> memberships) {
            getInstance().getMemberships().addAll(memberships);
            return this;
        }

        public Builder role(final StringPatchItem role) {
            getInstance().getRoles().add(role);
            return this;
        }

        public Builder roles(final StringPatchItem... roles) {
            getInstance().getRoles().addAll(Arrays.asList(roles));
            return this;
        }

        public Builder roles(final Collection<StringPatchItem> roles) {
            getInstance().getRoles().addAll(roles);
            return this;
        }
    }

    private StringReplacePatchItem username;

    private PasswordPatch password;

    private StringReplacePatchItem securityQuestion;

    private StringReplacePatchItem securityAnswer;

    private BooleanReplacePatchItem mustChangePassword;

    private final Set<RelationshipPatch> relationships = new HashSet<>();

    private final Set<MembershipPatch> memberships = new HashSet<>();

    private final Set<StringPatchItem> roles = new HashSet<>();

    @JsonProperty("@class")
    @Schema(name = "@class", required = true, example = "org.apache.syncope.common.lib.request.UserUR")
    @Override
    public String getDiscriminator() {
        return getClass().getName();
    }

    public StringReplacePatchItem getUsername() {
        return username;
    }

    public void setUsername(final StringReplacePatchItem username) {
        this.username = username;
    }

    public PasswordPatch getPassword() {
        return password;
    }

    public void setPassword(final PasswordPatch password) {
        this.password = password;
    }

    public StringReplacePatchItem getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(final StringReplacePatchItem securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public StringReplacePatchItem getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(final StringReplacePatchItem securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public BooleanReplacePatchItem getMustChangePassword() {
        return mustChangePassword;
    }

    public void setMustChangePassword(final BooleanReplacePatchItem mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }

    @XmlElementWrapper(name = "relationships")
    @XmlElement(name = "relationship")
    @JsonProperty("relationships")
    public Set<RelationshipPatch> getRelationships() {
        return relationships;
    }

    @XmlElementWrapper(name = "memberships")
    @XmlElement(name = "membership")
    @JsonProperty("memberships")
    public Set<MembershipPatch> getMemberships() {
        return memberships;
    }

    @XmlElementWrapper(name = "roles")
    @XmlElement(name = "role")
    @JsonProperty("roles")
    public Set<StringPatchItem> getRoles() {
        return roles;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty()
                && username == null && password == null && securityQuestion == null && securityAnswer == null
                && mustChangePassword == null && relationships.isEmpty() && memberships.isEmpty() && roles.isEmpty();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().
                appendSuper(super.hashCode()).
                append(username).
                append(password).
                append(securityQuestion).
                append(securityAnswer).
                append(mustChangePassword).
                append(relationships).
                append(memberships).
                append(roles).
                build();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserUR other = (UserUR) obj;
        return new EqualsBuilder().
                appendSuper(super.equals(obj)).
                append(username, other.username).
                append(securityQuestion, other.securityQuestion).
                append(securityAnswer, other.securityAnswer).
                append(mustChangePassword, other.mustChangePassword).
                append(relationships, other.relationships).
                append(memberships, other.memberships).
                append(roles, other.roles).
                build();
    }
}
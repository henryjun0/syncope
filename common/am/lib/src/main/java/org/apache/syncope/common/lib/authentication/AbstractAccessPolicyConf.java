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
package org.apache.syncope.common.lib.authentication;

import javax.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@XmlType
public abstract class AbstractAccessPolicyConf implements Serializable, AccessPolicyConf {

    private static final long serialVersionUID = 1153200197344709778L;

    private String name;

    private boolean enabled = true;

    private boolean singleSignOnEnabled = true;

    private Map<String, List<String>> requiredAttributes = new LinkedHashMap<>();

    public AbstractAccessPolicyConf() {
        setName(getClass().getName());
    }

    @Override
    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isSingleSignOnEnabled() {
        return singleSignOnEnabled;
    }

    public void setSingleSignOnEnabled(final boolean singleSignOnEnabled) {
        this.singleSignOnEnabled = singleSignOnEnabled;
    }

    @Override
    public Map<String, List<String>> getRequiredAttributes() {
        return requiredAttributes;
    }

    public void setRequiredAttributes(final Map<String, List<String>> requiredAttributes) {
        this.requiredAttributes = requiredAttributes;
    }
}

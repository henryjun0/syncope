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
package org.apache.syncope.common.lib.authentication.policy;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlRootElement(name = "DefaultAuthenticationPolicyConf")
@XmlType
public class DefaultAuthenticationPolicyConf extends AbstractAuthenticationPolicyConf
        implements AuthenticationPolicyConf {

    private static final long serialVersionUID = -2969836600059025380L;

    private final List<String> authenticationModules = new ArrayList<>();

    @XmlElementWrapper(name = "authenticationModules")
    @XmlElement(name = "authenticationModule")
    @JsonProperty("authenticationModules")
    public List<String> getAuthenticationModules() {
        return authenticationModules;
    }

}

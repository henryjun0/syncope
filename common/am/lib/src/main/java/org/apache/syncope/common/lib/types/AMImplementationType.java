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
package org.apache.syncope.common.lib.types;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

public final class AMImplementationType {
    
    public static final String AUTH_MODULE_CONFIGURATIONS = "AUTH_MODULE_CONFIGURATIONS";

    public static final String AUTH_POLICY_CONFIGURATIONS = "AUTH_POLICY_CONFIGURATIONS";

    public static final String ACCESS_POLICY_CONFIGURATIONS = "ACCESS_POLICY_CONFIGURATIONS";

    private AMImplementationType() {
        // private constructor for static utility class
    }

    private static final Map<String, String> VALUES = Map.ofEntries(
        Pair.of(AUTH_MODULE_CONFIGURATIONS, "org.apache.syncope.common.lib.authentication.AuthenticationPolicyConf"),
        Pair.of(AUTH_POLICY_CONFIGURATIONS, "org.apache.syncope.common.lib.authentication.AuthenticationModuleConf"),
        Pair.of(ACCESS_POLICY_CONFIGURATIONS, "org.apache.syncope.core.persistence.api.dao.AccessPolicyConf"));

    public static Map<String, String> values() {
        return VALUES;
    }

}

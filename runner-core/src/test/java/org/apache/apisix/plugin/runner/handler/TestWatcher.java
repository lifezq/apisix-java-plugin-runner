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

package org.apache.apisix.plugin.runner.handler;

import java.util.Map;

import org.apache.apisix.plugin.runner.A6Conf;
import org.apache.apisix.plugin.runner.A6ConfigWatcher;

class TestWatcher implements A6ConfigWatcher {
    private Map<String, String> config;
    private long token;

    public Map<String, String> getConfig() {
        return config;
    }

    public long getToken() {
        return token;
    }

    @Override
    public String name() {
        return "test";
    }

    @Override
    public void watch(long confToken, A6Conf a6Conf) {
        config = a6Conf.getConfig();
        token = confToken;
    }
}

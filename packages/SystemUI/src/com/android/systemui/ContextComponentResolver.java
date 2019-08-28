/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui;

import android.app.Service;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Used during Service and Activity instantiation to make them injectable.
 */
public class ContextComponentResolver implements ContextComponentHelper {
    private final Map<Class<?>, Provider<Service>> mServiceCreators;

    @Inject
    ContextComponentResolver(
            Map<Class<?>, Provider<Service>> serviceCreators) {
        mServiceCreators = serviceCreators;
    }

    /**
     * Looks up the Service class name to see if Dagger has an instance of it.
     */
    @Override
    public Service resolveService(String className) {
        return resolve(className, mServiceCreators);
    }

    private <T> T resolve(String className, Map<Class<?>, Provider<T>> creators) {
        for (Map.Entry<Class<?>, Provider<T>> p : creators.entrySet()) {
            if (p.getKey().getName().equals(className)) {
                return p.getValue().get();
            }
        }

        return null;
    }
}

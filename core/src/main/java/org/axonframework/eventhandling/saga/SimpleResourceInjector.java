/*
 * Copyright (c) 2010-2016. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.eventhandling.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * A resource injector that checks for {@see javax.inject.Inject} annotated fields and setter methods to inject
 * resources. If a field is annotated with {@see javax.inject.Inject}, a Resource of the type of that field is injected
 * into it, if present. If a method is annotated with {@see javax.inject.Inject}, the method is invoked with a Resource
 * of the type of the first parameter, if present.
 *
 * @author Allard Buijze
 * @since 1.1
 */
public class SimpleResourceInjector extends AbstractResourceInjector {

    private static final Logger logger = LoggerFactory.getLogger(SimpleResourceInjector.class);

    private static final String FULLY_QUALIFIED_CLASS_NAME_INJECT = "javax.inject.Inject";

    private final Iterable<?> resources;

    /**
     * Initializes the resource injector to inject to given {@code resources}.
     *
     * @param resources The resources to inject
     */
    public SimpleResourceInjector(Object... resources) {
        this(Arrays.asList(resources));
    }

    /**
     * Initializes the resource injector to inject to given {@code resources}.
     *
     * @param resources The resources to inject
     */
    public SimpleResourceInjector(Collection<?> resources) {
        this.resources = new ArrayList<>(resources);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <R> Optional<R> findResource(Class<R> requiredType) {
        return (Optional<R>) StreamSupport.stream(resources.spliterator(), false)
                .filter(requiredType::isInstance)
                .findFirst();
    }

}

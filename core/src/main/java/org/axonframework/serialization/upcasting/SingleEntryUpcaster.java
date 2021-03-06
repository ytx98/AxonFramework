/*
 * Copyright (c) 2010-2016. Axon Framework
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.serialization.upcasting;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Abstract implementation of an {@link Upcaster} that eases the common process of upcasting one intermediate
 * representation to another representation by applying a simple mapping function to the input stream of intermediate
 * representations.
 *
 * @author Rene de Waele
 */
public abstract class SingleEntryUpcaster<T> implements Upcaster<T> {

    @Override
    public Stream<T> upcast(Stream<T> intermediateRepresentations) {
        return intermediateRepresentations.map(entry -> {
            if (!canUpcast(entry)) {
                return entry;
            }
            return requireNonNull(doUpcast(entry), () -> "Result from #doUpcast() should not be null. To remove an " +
                    "intermediateRepresentation add a filter to the input stream.");
        });
    }

    protected abstract boolean canUpcast(T intermediateRepresentation);

    protected abstract T doUpcast(T intermediateRepresentation);
}

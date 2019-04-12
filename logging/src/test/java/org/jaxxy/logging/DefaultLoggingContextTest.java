/*
 * Copyright (c) 2018 The Jaxxy Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jaxxy.logging;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DefaultLoggingContextTest {

    @Mock
    private ContainerRequestContext requestContext;

    @Mock
    private ResourceInfo resourceInfo;
    private DefaultLoggingContext context;

    @BeforeEach
    public void setUp() {
        context = DefaultLoggingContext.builder()
                .requestContext(requestContext)
                .resourceInfo(resourceInfo)
                .serializer(String::valueOf)
                .build();
        MDC.clear();
    }

    @Test
    public void shouldReturnRequestContext() {
        assertThat(context.getRequestContext()).isEqualTo(requestContext);
    }

    @Test
    public void shouldReturnResourceInfo() {
        assertThat(context.getResourceInfo()).isSameAs(resourceInfo);
    }

    @Test
    public void putShouldAddToMdc() {
        context.put("foo", "bar");
        assertThat(MDC.get("foo")).isEqualTo("bar");
        context.cleanup();
        assertThat(MDC.get("foo")).isNull();
    }

    @Test
    public void putWithNullValueShouldDoNothing() {
        context.put("foo", null);
        assertThat(MDC.getCopyOfContextMap()).isNullOrEmpty();
    }
}
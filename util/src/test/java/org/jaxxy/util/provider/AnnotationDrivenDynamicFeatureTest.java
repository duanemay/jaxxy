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

package org.jaxxy.util.provider;

import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnnotationDrivenDynamicFeatureTest {

    @Mock
    private ResourceInfo resourceInfo;

    @Mock
    private FeatureContext featureContext;

    @Test
    public void methodWithAnnotationShouldBeProcessed() throws Exception {
        final NotAllowedFeature feature = new NotAllowedFeature();
        when(resourceInfo.getResourceMethod()).thenReturn(AnnotatedResource.class.getMethod("doEvil"));
        feature.configure(resourceInfo, featureContext);
        verify(resourceInfo).getResourceMethod();
        verify(featureContext).register(any(NotAllowedFilter.class));
        verifyNoMoreInteractions(resourceInfo, featureContext);
    }

    @Test
    public void methodWithoutAnnotationShouldNotBeProcessed() throws Exception {
        final NotAllowedFeature feature = new NotAllowedFeature();
        when(resourceInfo.getResourceMethod()).thenReturn(AnnotatedResource.class.getMethod("doGood"));
        feature.configure(resourceInfo, featureContext);
        verify(resourceInfo).getResourceMethod();
        verifyNoMoreInteractions(resourceInfo, featureContext);
    }
}
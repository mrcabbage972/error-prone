/*
 * Copyright 2013 The Error Prone Authors.
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

package com.google.errorprone.bugpatterns.inject.guice;

import com.google.errorprone.CompilationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author sgoldfeder@google.com (Steven Goldfeder)
 */
@RunWith(JUnit4.class)
public class InjectOnFinalFieldTest {
  private final CompilationTestHelper compilationHelper =
      CompilationTestHelper.newInstance(InjectOnFinalField.class, getClass());

  @Test
  public void positiveCase() {
    compilationHelper
        .addSourceLines(
            "InjectOnFinalFieldPositiveCases.java",
            """
            package com.google.errorprone.bugpatterns.inject.guice.testdata;

            import com.google.inject.Inject;
            import org.jspecify.annotations.Nullable;

            /**
             * @author sgoldfeder@google.com (Steven Goldfeder)
             */
            public class InjectOnFinalFieldPositiveCases {
              /** Class has a final injectable(com.google.inject.Inject) field. */
              public class TestClass1 {
                // BUG: Diagnostic contains: @Inject int a
                @Inject final int a = 0;

                @Inject
                // BUG: Diagnostic contains: public int b
                public final int b = 0;

                @Inject @Nullable
                // BUG: Diagnostic contains: Object c
                final Object c = null;
              }
            }\
            """)
        .doTest();
  }

  @Test
  public void negativeCase() {
    compilationHelper
        .addSourceLines(
            "InjectOnFinalFieldNegativeCases.java",
            """
            package com.google.errorprone.bugpatterns.inject.guice.testdata;

            import com.google.inject.Inject;

            /**
             * @author sgoldfeder@google.com (Steven Goldfeder)
             */
            public class InjectOnFinalFieldNegativeCases {

              /** Class has no final fields or @Inject annotations. */
              public class TestClass1 {}

              /** Class has a final field that is not injectable. */
              public class TestClass2 {
                public final int n = 0;
              }

              /** Class has an injectable(com.google.inject.Inject) field that is not final. */
              public class TestClass3 {
                @Inject public int n;
              }

              /** Class has an injectable(com.google.inject.Inject), final method. */
              public class TestClass4 {
                @Inject
                final void method() {}
              }
            }\
            """)
        .doTest();
  }
}

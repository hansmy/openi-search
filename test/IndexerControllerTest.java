/*******************************************************************************
 * Copyright (c) 2014 AmbieSense Limited.
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
 *******************************************************************************/
import controllers.routes;
import org.junit.*;

import play.mvc.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
 *
 * Simple (JUnit) tests that can call all parts of a Play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 *
 */
public class IndexerControllerTest {

   

    
    @Test
    public void indexingShouldContainTheCorrectString() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = callAction(routes.ref.IndexerController.startIndexing());
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo("text/html");
                assertThat(charset(result)).isEqualTo("utf-8");
                assertThat(contentAsString(result)).contains("Start Indexing");
            }
        });
    }
    

}

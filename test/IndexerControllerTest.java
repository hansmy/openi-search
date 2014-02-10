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

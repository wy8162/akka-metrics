package y.w.stream.common;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import org.junit.After;
import org.junit.Before;

public class AkkaSpec {

    protected ActorSystem system;
    protected Materializer materializer;

    @Before
    public void setup() {
        system = ActorSystem.create();
        materializer = Materializer.createMaterializer(system);
    }

    @After
    public void teardown() {
        system.terminate();
    }
}

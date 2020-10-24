package y.w.stream;

import akka.actor.ActorSystem;
import akka.stream.Materializer;

public class StreamMain {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("actorSystem");
        Materializer materializer = Materializer.createMaterializer(actorSystem);


    }
}

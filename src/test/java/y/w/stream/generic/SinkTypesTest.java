package y.w.stream.generic;

import akka.Done;
import akka.stream.javadsl.Sink;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import org.junit.Test;
import y.w.stream.common.AkkaSpec;

public class SinkTypesTest extends AkkaSpec {
    @Test
    public void sinkTest() {
        Sink<Object, CompletionStage<Done>> ignore = Sink.ignore();

        Sink<Integer, CompletionStage<Integer>> head = Sink.head();
        Sink<Integer, CompletionStage<Integer>> last = Sink.last();
        Sink<Integer, CompletionStage<Optional<Integer>>> headOption = Sink.headOption();
        Sink<Integer, CompletionStage<Optional<Integer>>> lastOption = Sink.lastOption();

        // Materialize sequence to list.
        Sink<Integer, CompletionStage<List<Integer>>> toList = Sink.seq();

        // Sink.fold
        // Sink.reduce

        // Send message to Actor
        // Sink<Integer, NotUsed> sendToActor = Sink.actorRef(myActor, Done.Instance)

        // Sink to file


    }
}

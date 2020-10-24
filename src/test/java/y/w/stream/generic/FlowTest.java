package y.w.stream.generic;

import akka.NotUsed;
import akka.japi.function.Function;
import akka.stream.ActorAttributes;
import akka.stream.Supervision;
import akka.stream.Supervision.Directive;
import akka.stream.javadsl.Flow;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.Test;

public class FlowTest {
    @Test
    public void flowTest() {
        Flow<String, Integer, NotUsed> mapFlow = Flow.of(String.class).map(String::length);

        Flow<String, Integer, NotUsed> mapAsync = Flow
            .of(String.class)
            .mapAsync(
                4, // Parallelism
                str -> CompletableFuture.supplyAsync(str::length)
            )
            // Return null if RuntimeException is thrown
            .recover(RuntimeException.class, () -> null);;

        Flow<String, String, NotUsed> flatten = Flow
            .of(String.class)
            .mapConcat(str -> Arrays.asList(str.split("\n")));

        Function<Throwable, Directive> decider = ex -> {
            if (ex instanceof RuntimeException) {
                return (Directive) Supervision.resume();
            } else {
                return (Directive) Supervision.stop();
            }
        };

        Flow<Integer, List<Integer>, NotUsed> groupOf10 = Flow
            .of(Integer.class)
            .grouped(10)
            .withAttributes(ActorAttributes.withSupervisionStrategy(decider));

        // Flow<String, List<String>, NotUsed> groupBy = Flow
        //    .of(String.class)
        //    .grouped(...);

        // Flow.of().fold()
        // Flow.of().scan()

        // Flow.of().filter()

        // Flow.of().collect()

        // Flow.of().takeWithin()
        // Flow.of().dropWithin()
        // Flow.of().groupedWithin()

        // Log
        Flow<Integer, Integer, NotUsed> log = Flow
            .of(Integer.class)
            .log("informationToBeLogged");

        // Flow.of().zipWithIndex()

        // flatMapConcat()

        // buffer()


    }
}

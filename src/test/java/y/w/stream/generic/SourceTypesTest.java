package y.w.stream.generic;

import akka.NotUsed;
import akka.actor.Cancellable;
import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.japi.Pair;
import akka.stream.Materializer;
import akka.stream.javadsl.Source;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.ClassRule;
import org.junit.Test;
import y.w.stream.common.AkkaSpec;

public class SourceTypesTest extends AkkaSpec {
    @ClassRule
    public static final TestKitJunitResource testKit = new TestKitJunitResource();

    @Test
    public void sourceTypesTest() {
        Source<String, NotUsed> empty = Source.empty();

        Source<String, NotUsed> single = Source.single("oneWork");

        Source<String, NotUsed> repeatEndlessly = Source.repeat("repeated");

        Source<String, Cancellable> periodic = Source.tick(
            Duration.ofSeconds(1l), // initial delay
            Duration.ofSeconds(5L), // Repeate interval
            "tick"              // Push this value every 5s after first wait for 1s.
                                    // If no demand, then some ticks will be lost.
        );

        periodic.runForeach(s -> System.out.println(s), materializer);

        Source<Integer, NotUsed> srcFromJavaIterable = Source.from(
            IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList())
        );

        Source<Integer, NotUsed> srcFromRange = Source.range(1, 10);

        // Iterator is created each time the source is materialized.
        Source<Integer, NotUsed> srcFromIterator = Source.fromIterator(
            () -> Arrays.asList(1,2,3,4,5).iterator()
        );

        srcFromIterator.runForeach(i -> System.out.println(i), materializer);

        // Infinitely repeated the list.
        Source<Integer, NotUsed> cycle = Source.cycle(
            () -> Arrays.asList(1,2,3,4,5).iterator()
        );

        // Unfold - stateful : the previous value is remembered.
        Source<Integer, NotUsed> unfold = Source.unfold(
            0, // initia value
            value -> {
                if (value < 100)
                    return Optional.of(new Pair(value + 1, value));
                else
                    return Optional.empty();
            }
        );

        // UnfoldAsync - returns CompletionStage<Optional)
        Source<Integer, NotUsed> unfoldAsync = Source.unfoldAsync(
            0, // initia value
            value -> {
                if (value < 100)
                    return CompletableFuture.completedFuture(Optional.of(new Pair(value + 1, value)));
                else
                    return CompletableFuture.completedFuture(Optional.empty());
            }
        );
    }

    public static class Message {}

    public static class SimpleActor extends AbstractBehavior<Message> {

        public SimpleActor(ActorContext<Message> context) {
            super(context);
        }

        public static Behavior<Message> create() {
            return Behaviors.setup(SimpleActor::new);
        }

        @Override
        public Receive<Message> createReceive() {
            return newReceiveBuilder()
                .onMessage(Message.class, this::onMsgAction)
                .build();
        }

        private Behavior<Message> onMsgAction(Message m) {
            return Behaviors.same();
        }
    }
}

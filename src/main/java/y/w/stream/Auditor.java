package y.w.stream;

import akka.Done;
import akka.NotUsed;
import akka.event.LoggingAdapter;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import java.time.Duration;
import java.util.concurrent.CompletionStage;
import y.w.stream.common.Car;

public class Auditor {
    private final Sink<Car, CompletionStage<Integer>> count;
    private final Materializer materializer;

    public Auditor(Materializer materializer) {
        count = Sink.fold(
            0,
            (count, ignore) -> count + 1
        );

        this.materializer = materializer;
    }

    public Sink<Car, CompletionStage<Integer>> getCount() {
        return count;
    }

    Sink<Object, CompletionStage<Done>> log(LoggingAdapter loggingAdapter) {
        return Sink.foreach(elem -> loggingAdapter.debug(elem.toString()));
    }

    Flow<Car, Car, NotUsed> sample(Duration sampleSize) {
        return Flow
            .of(Car.class)
            .takeWithin(sampleSize);
    }

    CompletionStage<Integer> audit(Source<Car, NotUsed> cars, Duration sampleSize) {
        return
            cars
                .via(sample(sampleSize))
                .runWith(getCount(), materializer);
    }
}

package y.w.stream;

import akka.actor.Cancellable;
import akka.stream.javadsl.Source;
import java.time.Duration;
import y.w.stream.common.UnfinishedCar;

public class BodyShop {
    private final Source<UnfinishedCar, Cancellable> cars;

    public BodyShop(Duration buildTime) {
        cars  = Source.tick(buildTime, buildTime, new UnfinishedCar());
    }

    public Source<UnfinishedCar, Cancellable> getCars() {
        return cars;
    }
}

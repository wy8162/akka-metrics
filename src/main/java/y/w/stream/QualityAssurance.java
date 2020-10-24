package y.w.stream;

import akka.NotUsed;
import akka.japi.function.Function;
import akka.stream.ActorAttributes;
import akka.stream.Supervision;
import akka.stream.Supervision.Directive;
import akka.stream.javadsl.Flow;
import y.w.stream.common.Car;
import y.w.stream.common.SerialNumber;
import y.w.stream.common.UnfinishedCar;

public class QualityAssurance {
    static class CarFailedInspection extends IllegalStateException {

        public CarFailedInspection(UnfinishedCar car) {
            super("Car failed inspection: " + car);
        }
    }
    private final Flow<UnfinishedCar, Car, NotUsed> inspect;

    public QualityAssurance() {
        Function<Throwable, Directive> decider = e -> {
            if (e instanceof CarFailedInspection)
                return (Directive) Supervision.resume();
            else
                return (Directive) Supervision.stop();
        };

        this.inspect = Flow
            .of(UnfinishedCar.class)
            .filter(this::isValid)
            .map(car -> {
                if (isValid(car)) {
                    return new Car(
                        new SerialNumber(),
                        car.getColor().get(),
                        car.getEngine().get(),
                        car.getWheels(),
                        car.getUpgrade());
                } else {
                    throw new CarFailedInspection(car);
                }
            })
            .withAttributes(ActorAttributes.withSupervisionStrategy(decider));
    }

    public Flow<UnfinishedCar, Car, NotUsed> getInspect() {
        return inspect;
    }

    private boolean isValid(UnfinishedCar car) {
        return
            car.getColor().isPresent() &&
            car.getWheels().size() == 4 &&
            car.getEngine().isPresent();
    }
}

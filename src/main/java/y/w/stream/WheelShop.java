package y.w.stream;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;
import y.w.stream.common.UnfinishedCar;
import y.w.stream.common.Wheel;

public class WheelShop {
    private final Source<Wheel, NotUsed> wheels;
    private final Flow<UnfinishedCar, UnfinishedCar, NotUsed> installWheels;

    public WheelShop() {
        wheels = Source.repeat(new Wheel());

        installWheels = Flow
            .of(UnfinishedCar.class)
            .zip(getWheels().grouped(4))
            .map(carAndWheels ->
                carAndWheels.first().installWheels(carAndWheels.second())
            );
    }

    public Flow<UnfinishedCar, UnfinishedCar, NotUsed> getInstallWheels() {
        return installWheels;
    }

    public Source<Wheel, NotUsed> getWheels() {
        return wheels;
    }
}

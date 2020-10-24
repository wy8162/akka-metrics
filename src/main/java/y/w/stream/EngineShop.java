package y.w.stream;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;
import java.util.Vector;
import java.util.stream.Stream;
import y.w.stream.common.Engine;
import y.w.stream.common.Shipment;
import y.w.stream.common.UnfinishedCar;

public class EngineShop {
    private final Source<Shipment, NotUsed> shipments;
    private final Source<Engine, NotUsed> engines;
    private final Flow<UnfinishedCar, UnfinishedCar, NotUsed> installEngine;

    public EngineShop(int shipmentSize) {
        shipments = Source.fromIterator(
            () -> Stream.generate(() -> {
                Vector<Engine> engines = new Vector<>(shipmentSize);

                for (int i=0; i < shipmentSize; i++)
                    engines.add(new Engine());

                return new Shipment(engines);
            }).iterator());

        engines = shipments
            .mapConcat(Shipment::getEngines);

        installEngine = Flow
            .of(UnfinishedCar.class)
            .zip(engines)
            .map( carAndEngine ->
                carAndEngine.first().installEngine(carAndEngine.second())
             );
    }

    public Flow<UnfinishedCar, UnfinishedCar, NotUsed> getInstallEngine() {
        return installEngine;
    }

    public Source<Engine, NotUsed> getEngines() {
        return engines;
    }

    public Source<Shipment, NotUsed> getShipments() {
        return shipments;
    }
}

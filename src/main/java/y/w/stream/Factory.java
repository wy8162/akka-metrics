package y.w.stream;

import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import java.util.List;
import java.util.concurrent.CompletionStage;
import y.w.stream.common.Car;

public class Factory {
    private final BodyShop bodyShop;
    private final PaintShop paintShop;
    private final EngineShop engineShop;
    private final WheelShop wheelShop;
    private final QualityAssurance qualityAssurance;
    private final UpgradeShop upgradeShop;
    private final Materializer materializer;

    public Factory(BodyShop bodyShop, PaintShop paintShop, EngineShop engineShop,
        WheelShop wheelShop, QualityAssurance qualityAssurance, UpgradeShop upgradeShop, Materializer materializer) {
        this.bodyShop = bodyShop;
        this.paintShop = paintShop;
        this.engineShop = engineShop;
        this.wheelShop = wheelShop;
        this.qualityAssurance = qualityAssurance;
        this.upgradeShop = upgradeShop;
        this.materializer = materializer;
    }

    CompletionStage<List<Car>> orderCars(int quantity) {
        return
            bodyShop.getCars()
                //.withAttributes(ActorAttributes.dispatcher("..."))
                .via(paintShop.getPaint())
                .async()
                .via(engineShop.getInstallEngine())
                .async()
                .via(wheelShop.getInstallWheels())
                .async()
                .via(upgradeShop.getInstallUpgrade())
                .via(qualityAssurance.getInspect())
                .take(quantity)
                .runWith(Sink.seq(), materializer);
    }
}

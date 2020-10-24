package y.w.stream;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;
import java.util.Set;
import y.w.stream.common.Color;
import y.w.stream.common.UnfinishedCar;

public class PaintShop {
    private final Source<Color, NotUsed> colors;
    private final Flow<UnfinishedCar, UnfinishedCar, NotUsed> paint;

    public PaintShop(Set<Color> colorsSet) {
        colors = Source.cycle(colorsSet::iterator);

        paint = Flow
            .of(UnfinishedCar.class)
            .zip(getColors())
            .map(
                carAndColor -> carAndColor.first().paint(carAndColor.second())
            );
    }

    public Flow<UnfinishedCar, UnfinishedCar, NotUsed> getPaint() {
        return paint;
    }

    public Source<Color, NotUsed> getColors() {
        return colors;
    }


}

package y.w.stream.common;

import static y.w.stream.common.Busy.busy;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Vector;

/**
 * Operations always return a new car because immutability.
 */
public class UnfinishedCar {
    private static final Random random = new Random(7);
    private static final int busyTimeScale = 10;
    private static final Duration paintTime = calculateRandomMillis();
    private static final Duration installEngineTime = calculateRandomMillis();
    private static final Duration installWheelsTime = calculateRandomMillis();
    private static final Duration installUpgradeTime = calculateRandomMillis();

    private final Optional<Color> color;
    private final Optional<Engine> engine;
    private final List<Wheel> wheels;
    private final Optional<Upgrade> upgrade;

    public Optional<Color> getColor() {
        return color;
    }

    public Optional<Engine> getEngine() {
        return engine;
    }

    public List<Wheel> getWheels() {
        return new Vector<>(wheels);
    }

    public Optional<Upgrade> getUpgrade() {
        return upgrade;
    }

    public UnfinishedCar() {
        color = Optional.empty();
        engine = Optional.empty();
        wheels = new Vector<>();
        upgrade = Optional.empty();
    }

    UnfinishedCar(
            Optional<Color> color,
            Optional<Engine> engine,
            List<Wheel> wheels,
            Optional<Upgrade> upgrade
    ) {
        this.color = color;
        this.engine = engine;
        this.wheels = new Vector<>(wheels);
        this.upgrade = upgrade;
    }

    private static Duration calculateRandomMillis() {
        return Duration.ofMillis(random.nextInt(busyTimeScale));
    }

    public UnfinishedCar paint(Color color) {

        busy(paintTime);

        return new UnfinishedCar(
                Optional.of(color),
                engine,
                wheels,
                upgrade
        );
    }

    public UnfinishedCar installEngine(Engine engine) {

        busy(installEngineTime);

        return new UnfinishedCar(
                color,
                Optional.of(engine),
                wheels,
                upgrade
        );
    }

    public UnfinishedCar installWheels(List<Wheel> wheels) {

        busy(installWheelsTime);

        return new UnfinishedCar(
                color,
                engine,
                wheels,
                upgrade
        );
    }

    public UnfinishedCar installUpgrade(Upgrade upgrade) {

        busy(installUpgradeTime);

        return new UnfinishedCar(
                color,
                engine,
                wheels,
                Optional.of(upgrade)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnfinishedCar that = (UnfinishedCar) o;
        return Objects.equals(color, that.color) &&
                Objects.equals(engine, that.engine) &&
                Objects.equals(wheels, that.wheels) &&
                Objects.equals(upgrade, that.upgrade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, engine, wheels, upgrade);
    }
}

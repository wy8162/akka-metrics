package y.w.stream.common;

import java.util.Objects;

public class Engine {
    private final SerialNumber serialNumber;

    SerialNumber getSerialNumber() {
        return serialNumber;
    }

    public Engine() {
        this.serialNumber = new SerialNumber();
    }

    Engine(SerialNumber serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Engine engine = (Engine) o;
        return Objects.equals(serialNumber, engine.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }
}

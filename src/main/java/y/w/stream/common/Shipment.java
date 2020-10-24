package y.w.stream.common;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class Shipment {
    private final List<Engine> engines;

    public List<Engine> getEngines() {
        return new Vector<>(engines);
    }

    public Shipment(List<Engine> engines) {
        this.engines = new Vector<>(engines);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return Objects.equals(engines, shipment.engines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(engines);
    }
}

package comparadores;

import entidades.Vehiculo;
import java.util.Comparator;

public class ComparatorPorAnio implements Comparator<Vehiculo> {

    @Override
    public int compare(Vehiculo v1, Vehiculo v2) {
        return Integer.compare(v1.getAnio(), v2.getAnio());
    }
}

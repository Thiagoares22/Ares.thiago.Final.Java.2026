package filtros;

import entidades.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public class Filtros {

    public static List<Vehiculo> filtrarMayores(List<? extends Vehiculo> vehiculos, int anioMinimo) {
        List<Vehiculo> resultado = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            if (v.getAnio() >= anioMinimo) {
                resultado.add(v);
            }
        }
        return resultado;
    }

    public static void agregarATodos(List<? super Vehiculo> destino, Vehiculo vehiculo) {
        destino.add(vehiculo);
    }

    public static <T extends Vehiculo> List<T> filtrarPorClase(List<T> vehiculos, Class<T> clase) {
        List<T> resultado = new ArrayList<>();
        for (T v : vehiculos) {
            if (clase.isInstance(v)) {
                resultado.add(v);
            }
        }
        return resultado;
    }

    public static String obtenerResumen(List<? extends Vehiculo> vehiculos) {
        StringBuilder sb = new StringBuilder();
        sb.append("Total de vehiculos: ").append(vehiculos.size()).append("\n");
        for (Vehiculo v : vehiculos) {
            sb.append("  - ").append(v.toString()).append("\n");
        }
        return sb.toString();
    }
}

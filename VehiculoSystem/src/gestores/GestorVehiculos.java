package gestores;

import entidades.Vehiculo;
import entidades.EstadoVehiculo;
import excepciones.VehiculoDuplicadoException;
import excepciones.VehiculoNoEncontradoException;
import interfaces.ICrud;
import interfaces.IteradorPersonalizado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class GestorVehiculos<T extends Vehiculo> implements ICrud<T> {

    private List<T> vehiculos;

    public GestorVehiculos() {
        this.vehiculos = new ArrayList<>();
    }

    @Override
    public void agregar(T vehiculo) {
        vehiculos.add(vehiculo);
    }

    public void agregarSeguro(T vehiculo) throws VehiculoDuplicadoException {
        if (buscar(vehiculo.getPatente()) != null) {
            throw new VehiculoDuplicadoException("Ya existe un vehiculo con patente: " + vehiculo.getPatente());
        }
        vehiculos.add(vehiculo);
    }

    @Override
    public void eliminar(T vehiculo) {
        vehiculos.remove(vehiculo);
    }

    public void eliminarPorPatente(String patente) throws VehiculoNoEncontradoException {
        T v = buscar(patente);
        if (v == null) {
            throw new VehiculoNoEncontradoException("No se encontro vehiculo con patente: " + patente);
        }
        vehiculos.remove(v);
    }

    @Override
    public void actualizar(T viejo, T nuevo) {
        int idx = vehiculos.indexOf(viejo);
        if (idx != -1) {
            vehiculos.set(idx, nuevo);
        }
    }

    @Override
    public T buscar(String patente) {
        for (T v : vehiculos) {
            if (v.getPatente().equalsIgnoreCase(patente)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public List<T> listar() {
        return new ArrayList<>(vehiculos);
    }

    @Override
    public int cantidad() {
        return vehiculos.size();
    }

    public void ordenarNatural() {
        Collections.sort(vehiculos);
    }

    public void ordenarConComparator(Comparator<T> comparator) {
        vehiculos.sort(comparator);
    }

    public IteradorPersonalizado<T> getIterador() {
        return new IteradorPersonalizado<>(vehiculos);
    }

    public List<T> filtrar(Predicate<T> criterio) {
        List<T> resultado = new ArrayList<>();
        for (T v : vehiculos) {
            if (criterio.test(v)) {
                resultado.add(v);
            }
        }
        return resultado;
    }

    public void aplicarModificacion(Consumer<T> modificacion) {
        for (T v : vehiculos) {
            modificacion.accept(v);
        }
    }

    public List<String> convertirAStrings(Function<T, String> converter) {
        List<String> resultado = new ArrayList<>();
        for (T v : vehiculos) {
            resultado.add(converter.apply(v));
        }
        return resultado;
    }

    public void incrementarAnio(int cantidad) {
        Consumer<T> incrementar = v -> v.setAnio(v.getAnio() + cantidad);
        aplicarModificacion(incrementar);
    }

    public void cambiarEstado(EstadoVehiculo nuevoEstado) {
        Consumer<T> cambiar = v -> v.setEstado(nuevoEstado);
        aplicarModificacion(cambiar);
    }

    public List<T> filtrarPorEstado(EstadoVehiculo estado) {
        return filtrar(v -> v.getEstado() == estado);
    }

    public List<T> filtrarPorMarca(String marca) {
        return filtrar(v -> v.getMarca().equalsIgnoreCase(marca));
    }

    public List<T> filtrarPorAnioMinimo(int anioMin) {
        return filtrar(v -> v.getAnio() >= anioMin);
    }
}

package interfaces;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class IteradorPersonalizado<T> implements Iterator<T> {

    private final List<T> lista;
    private int posicion;

    public IteradorPersonalizado(List<T> lista) {
        this.lista = lista;
        this.posicion = 0;
    }

    @Override
    public boolean hasNext() {
        return posicion < lista.size();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No hay mas elementos en la coleccion");
        }
        return lista.get(posicion++);
    }

    public void reset() {
        this.posicion = 0;
    }

    public int getPosicion() {
        return posicion;
    }
}

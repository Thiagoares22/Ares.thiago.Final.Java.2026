package interfaces;

import java.util.List;

public interface ICrud<T> {
    void agregar(T elemento);
    void eliminar(T elemento);
    void actualizar(T viejo, T nuevo);
    T buscar(String patente);
    List<T> listar();
    int cantidad();
}

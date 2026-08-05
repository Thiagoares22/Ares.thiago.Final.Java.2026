package excepciones;

public class VehiculoDuplicadoException extends Exception {

    public VehiculoDuplicadoException(String mensaje) {
        super(mensaje);
    }

    public VehiculoDuplicadoException() {
        super("Ya existe un vehiculo con esa patente en el sistema");
    }
}

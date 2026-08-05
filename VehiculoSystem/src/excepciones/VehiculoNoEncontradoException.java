package excepciones;

public class VehiculoNoEncontradoException extends Exception {

    public VehiculoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public VehiculoNoEncontradoException() {
        super("Vehiculo no encontrado en el sistema");
    }
}

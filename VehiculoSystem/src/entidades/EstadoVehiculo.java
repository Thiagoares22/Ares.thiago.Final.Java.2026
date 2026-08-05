package entidades;

public enum EstadoVehiculo {
    DISPONIBLE("Disponible"),
    EN_MANTENIMIENTO("En Mantenimiento"),
    VENDIDO("Vendido"),
    RESERVADO("Reservado");

    private String descripcion;

    EstadoVehiculo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}

package entidades;

public enum TipoMoto {
    SCOOTER("Scooter"),
    DEPORTIVA("Deportiva"),
    TOURING("Touring"),
    ENDURO("Enduro");

    private String descripcion;

    TipoMoto(String descripcion) {
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

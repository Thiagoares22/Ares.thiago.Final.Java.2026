package entidades;

public enum TipoCombustible {
    GASOLINA("Gasolina"),
    DIESEL("Diesel"),
    ELECTRICO("Electrico"),
    HIBRIDO("Hibrido"),
    GNC("GNC");

    private String descripcion;

    TipoCombustible(String descripcion) {
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

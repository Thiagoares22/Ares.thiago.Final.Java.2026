package entidades;

import java.io.Serializable;
import java.util.Objects;

public abstract class Vehiculo implements Comparable<Vehiculo>, Serializable {

    private static final long serialVersionUID = 1L;

    protected String marca;
    protected String modelo;
    protected int anio;
    protected String patente;
    protected EstadoVehiculo estado;
    protected TipoCombustible combustible;

    public Vehiculo(String marca, String modelo, int anio, String patente, EstadoVehiculo estado, TipoCombustible combustible) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.patente = patente;
        this.estado = estado;
        this.combustible = combustible;
    }

    public Vehiculo(String marca, String modelo, int anio, String patente, TipoCombustible combustible) {
        this(marca, modelo, anio, patente, EstadoVehiculo.DISPONIBLE, combustible);
    }

    public Vehiculo(String marca, String modelo, int anio, String patente) {
        this(marca, modelo, anio, patente, EstadoVehiculo.DISPONIBLE, TipoCombustible.GASOLINA);
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public EstadoVehiculo getEstado() {
        return estado;
    }

    public void setEstado(EstadoVehiculo estado) {
        this.estado = estado;
    }

    public TipoCombustible getCombustible() {
        return combustible;
    }

    public void setCombustible(TipoCombustible combustible) {
        this.combustible = combustible;
    }

    public abstract double calcularImpuesto();

    public abstract String getTipoVehiculo();

    @Override
    public int compareTo(Vehiculo otro) {
        return Integer.compare(this.anio, otro.anio);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vehiculo otro = (Vehiculo) obj;
        return Objects.equals(patente, otro.patente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patente);
    }

    @Override
    public String toString() {
        return getTipoVehiculo() + " | " + marca + " " + modelo + " | " + anio + " | " + patente
                + " | " + estado + " | " + combustible;
    }
}

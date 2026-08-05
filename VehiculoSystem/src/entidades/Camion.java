package entidades;

import interfaces.Exportable;

public class Camion extends Vehiculo implements Exportable {

    private double capacidadCarga;
    private int cantidadEjes;

    public Camion(String marca, String modelo, int anio, String patente, EstadoVehiculo estado,
                  TipoCombustible combustible, double capacidadCarga, int cantidadEjes) {
        super(marca, modelo, anio, patente, estado, combustible);
        this.capacidadCarga = capacidadCarga;
        this.cantidadEjes = cantidadEjes;
    }

    public Camion(String marca, String modelo, int anio, String patente, TipoCombustible combustible,
                  double capacidadCarga, int cantidadEjes) {
        super(marca, modelo, anio, patente, combustible);
        this.capacidadCarga = capacidadCarga;
        this.cantidadEjes = cantidadEjes;
    }

    public Camion(String marca, String modelo, int anio, String patente) {
        super(marca, modelo, anio, patente, TipoCombustible.DIESEL);
        this.capacidadCarga = 5000;
        this.cantidadEjes = 2;
    }

    public double getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public int getCantidadEjes() {
        return cantidadEjes;
    }

    public void setCantidadEjes(int cantidadEjes) {
        this.cantidadEjes = cantidadEjes;
    }

    @Override
    public double calcularImpuesto() {
        return 80000 + (capacidadCarga * 2.5);
    }

    @Override
    public String getTipoVehiculo() {
        return "Camion";
    }

    @Override
    public String toString() {
        return super.toString() + " | " + capacidadCarga + " kg | " + cantidadEjes + " ejes";
    }

    @Override
    public String toCSV() {
        return getTipoVehiculo() + "," + getMarca() + "," + getModelo() + "," + getAnio() + ","
                + getPatente() + "," + getEstado() + "," + getCombustible() + "," + capacidadCarga + "," + cantidadEjes;
    }

    @Override
    public String getEncabezadoCSV() {
        return "Tipo,Marca,Modelo,Anio,Patente,Estado,Combustible,CapacidadCarga,CantidadEjes";
    }
}

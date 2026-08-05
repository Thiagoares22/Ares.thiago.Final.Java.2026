package entidades;

import interfaces.Exportable;

public class Auto extends Vehiculo implements Exportable {

    private TipoAuto tipoAuto;
    private int cantPuertas;

    public Auto(String marca, String modelo, int anio, String patente, EstadoVehiculo estado,
                TipoCombustible combustible, TipoAuto tipoAuto, int cantPuertas) {
        super(marca, modelo, anio, patente, estado, combustible);
        this.tipoAuto = tipoAuto;
        this.cantPuertas = cantPuertas;
    }

    public Auto(String marca, String modelo, int anio, String patente, TipoCombustible combustible,
                TipoAuto tipoAuto, int cantPuertas) {
        super(marca, modelo, anio, patente, combustible);
        this.tipoAuto = tipoAuto;
        this.cantPuertas = cantPuertas;
    }

    public Auto(String marca, String modelo, int anio, String patente) {
        super(marca, modelo, anio, patente);
        this.tipoAuto = TipoAuto.SEDAN;
        this.cantPuertas = 4;
    }

    public TipoAuto getTipoAuto() {
        return tipoAuto;
    }

    public void setTipoAuto(TipoAuto tipoAuto) {
        this.tipoAuto = tipoAuto;
    }

    public int getCantPuertas() {
        return cantPuertas;
    }

    public void setCantPuertas(int cantPuertas) {
        this.cantPuertas = cantPuertas;
    }

    @Override
    public double calcularImpuesto() {
        double base = anio >= 2020 ? 50000 : 30000;
        if (tipoAuto == TipoAuto.SUV) {
            base *= 1.2;
        }
        return base;
    }

    @Override
    public String getTipoVehiculo() {
        return "Auto";
    }

    @Override
    public String toString() {
        return super.toString() + " | " + tipoAuto + " | " + cantPuertas + " puertas";
    }

    @Override
    public String toCSV() {
        return getTipoVehiculo() + "," + getMarca() + "," + getModelo() + "," + getAnio() + ","
                + getPatente() + "," + getEstado() + "," + getCombustible() + "," + tipoAuto + "," + cantPuertas;
    }

    @Override
    public String getEncabezadoCSV() {
        return "Tipo,Marca,Modelo,Anio,Patente,Estado,Combustible,TipoAuto,Puertas";
    }
}

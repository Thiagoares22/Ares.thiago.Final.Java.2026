package entidades;

import interfaces.Exportable;

public class Moto extends Vehiculo implements Exportable {

    private int cilindrada;
    private TipoMoto tipoMoto;

    public Moto(String marca, String modelo, int anio, String patente, EstadoVehiculo estado,
                TipoCombustible combustible, int cilindrada, TipoMoto tipoMoto) {
        super(marca, modelo, anio, patente, estado, combustible);
        this.cilindrada = cilindrada;
        this.tipoMoto = tipoMoto;
    }

    public Moto(String marca, String modelo, int anio, String patente, TipoCombustible combustible,
                int cilindrada, TipoMoto tipoMoto) {
        super(marca, modelo, anio, patente, combustible);
        this.cilindrada = cilindrada;
        this.tipoMoto = tipoMoto;
    }

    public Moto(String marca, String modelo, int anio, String patente) {
        super(marca, modelo, anio, patente);
        this.cilindrada = 150;
        this.tipoMoto = TipoMoto.SCOOTER;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    public TipoMoto getTipoMoto() {
        return tipoMoto;
    }

    public void setTipoMoto(TipoMoto tipoMoto) {
        this.tipoMoto = tipoMoto;
    }

    @Override
    public double calcularImpuesto() {
        double base = cilindrada > 250 ? 25000 : 15000;
        if (tipoMoto == TipoMoto.DEPORTIVA) {
            base *= 1.3;
        }
        return base;
    }

    @Override
    public String getTipoVehiculo() {
        return "Moto";
    }

    @Override
    public String toString() {
        return super.toString() + " | " + cilindrada + "cc | " + tipoMoto;
    }

    @Override
    public String toCSV() {
        return getTipoVehiculo() + "," + getMarca() + "," + getModelo() + "," + getAnio() + ","
                + getPatente() + "," + getEstado() + "," + getCombustible() + "," + cilindrada + "," + tipoMoto;
    }

    @Override
    public String getEncabezadoCSV() {
        return "Tipo,Marca,Modelo,Anio,Patente,Estado,Combustible,Cilindrada,TipoMoto";
    }
}

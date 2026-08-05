package persistencia;

import entidades.Vehiculo;
import interfaces.Exportable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaCSV {

    private static <E extends Enum<E>> E parseEnum(Class<E> enumClass, String valor) {
        if (valor == null || valor.isEmpty()) {
            return null;
        }
        String v = valor.trim();
        try {
            return Enum.valueOf(enumClass, v.toUpperCase());
        } catch (IllegalArgumentException e) {
            for (E c : enumClass.getEnumConstants()) {
                if (c.toString().equalsIgnoreCase(v)) {
                    return c;
                }
            }
            throw e;
        }
    }

    public static <T extends Vehiculo> void guardarCSV(List<T> vehiculos, String archivo) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            if (!vehiculos.isEmpty()) {
                pw.println("Tipo,Marca,Modelo,Anio,Patente,Estado,Combustible,Extra1,Extra2");
            }
            for (T v : vehiculos) {
                if (v instanceof Exportable) {
                    pw.println(((Exportable) v).toCSV());
                } else {
                    pw.println(v.getTipoVehiculo() + "," + v.getMarca() + "," + v.getModelo() + ","
                            + v.getAnio() + "," + v.getPatente() + "," + v.getEstado() + ","
                            + v.getCombustible() + ",,");
                }
            }
        }
    }

    public static List<Vehiculo> cargarCSV(String archivo) throws IOException {
        List<Vehiculo> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primera = true;
            while ((linea = br.readLine()) != null) {
                if (primera) {
                    primera = false;
                    continue;
                }
                String[] parts = linea.split(",");
                if (parts.length >= 6) {
                    String tipo = parts[0].trim();
                    String marca = parts[1].trim();
                    String modelo = parts[2].trim();
                    int anio = Integer.parseInt(parts[3].trim());
                    String patente = parts[4].trim();
                    String estado = parts[5].trim();
                    String combustible = parts.length > 6 ? parts[6].trim() : "GASOLINA";

                    entidades.EstadoVehiculo est = parseEnum(entidades.EstadoVehiculo.class, estado);
                    entidades.TipoCombustible comb = parseEnum(entidades.TipoCombustible.class, combustible);

                    switch (tipo.toLowerCase()) {
                        case "auto":
                            String tipoAuto = parts.length > 7 ? parts[7].trim() : "SEDAN";
                            int puertas = parts.length > 8 ? Integer.parseInt(parts[8].trim()) : 4;
                            lista.add(new entidades.Auto(marca, modelo, anio, patente, est, comb,
                                    parseEnum(entidades.TipoAuto.class, tipoAuto), puertas));
                            break;
                        case "camion":
                            double capCarga = parts.length > 7 ? Double.parseDouble(parts[7].trim()) : 5000;
                            int ejes = parts.length > 8 ? Integer.parseInt(parts[8].trim()) : 2;
                            lista.add(new entidades.Camion(marca, modelo, anio, patente, est, comb, capCarga, ejes));
                            break;
                        case "moto":
                            int cilindrada = parts.length > 7 ? Integer.parseInt(parts[7].trim()) : 150;
                            String tipoMoto = parts.length > 8 ? parts[8].trim() : "SCOOTER";
                            lista.add(new entidades.Moto(marca, modelo, anio, patente, est, comb,
                                    cilindrada, parseEnum(entidades.TipoMoto.class, tipoMoto)));
                            break;
                    }
                }
            }
        }
        return lista;
    }
}

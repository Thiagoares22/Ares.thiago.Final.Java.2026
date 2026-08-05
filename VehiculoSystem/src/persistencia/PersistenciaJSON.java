package persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import entidades.Auto;
import entidades.Camion;
import entidades.Moto;
import entidades.Vehiculo;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaJSON {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Vehiculo.class, new VehiculoTypeAdapter())
            .create();

    public static <T extends Vehiculo> void guardarJSON(List<T> vehiculos, String archivo) throws IOException {
        try (Writer writer = new FileWriter(archivo)) {
            Type listType = new TypeToken<List<Vehiculo>>() {}.getType();
            gson.toJson(vehiculos, listType, writer);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Vehiculo> cargarJSON(String archivo) throws IOException {
        try (Reader reader = new FileReader(archivo)) {
            Type listType = new TypeToken<List<Vehiculo>>() {}.getType();
            List<Vehiculo> raw = gson.fromJson(reader, listType);
            if (raw == null) return new ArrayList<>();
            List<Vehiculo> resultado = new ArrayList<>();
            for (Vehiculo v : raw) {
                if (v instanceof Auto || v instanceof Camion || v instanceof Moto) {
                    resultado.add(v);
                }
            }
            return resultado;
        }
    }

    public static void exportarTXT(List<? extends Vehiculo> vehiculos, String archivo) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("========================================");
            pw.println("   REPORTE DE VEHICULOS");
            pw.println("   Sistema de Gestion de Vehiculos");
            pw.println("========================================");
            pw.println();
            pw.printf("Total de vehiculos: %d%n", vehiculos.size());
            pw.println();
            pw.println("----------------------------------------");
            int i = 1;
            for (Vehiculo v : vehiculos) {
                pw.printf("#%d - %s%n", i, v.toString());
                pw.printf("     Impuesto: $%.2f%n", v.calcularImpuesto());
                pw.println();
                i++;
            }
            pw.println("----------------------------------------");
            pw.println("Fin del reporte.");
        }
    }
}

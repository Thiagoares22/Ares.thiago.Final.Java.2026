package persistencia;

import entidades.Vehiculo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaDAT {

    public static <T extends Vehiculo> void serializar(List<T> vehiculos, String archivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(new ArrayList<>(vehiculos));
        }
    }

    
    public static <T extends Vehiculo> List<T> deserializar(String archivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<T>) ois.readObject();
        }
    }
}

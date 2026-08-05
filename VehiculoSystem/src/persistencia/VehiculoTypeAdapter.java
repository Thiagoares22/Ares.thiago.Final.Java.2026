package persistencia;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import entidades.Auto;
import entidades.Camion;
import entidades.Moto;
import entidades.Vehiculo;

import java.io.IOException;

public class VehiculoTypeAdapter extends TypeAdapter<Vehiculo> {

    private final Gson gson = new Gson();

    @Override
    public void write(JsonWriter out, Vehiculo value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        JsonObject obj = (JsonObject) gson.toJsonTree(value);
        obj.addProperty("tipo", value.getTipoVehiculo());
        gson.toJson(obj, out);
    }

    @Override
    public Vehiculo read(JsonReader in) throws IOException {
        JsonObject obj = JsonParser.parseReader(in).getAsJsonObject();
        return gson.fromJson(obj, resolverClase(obj));
    }

    private Class<? extends Vehiculo> resolverClase(JsonObject obj) {
        if (obj.has("tipo")) {
            switch (obj.get("tipo").getAsString()) {
                case "Camion":
                    return Camion.class;
                case "Moto":
                    return Moto.class;
                default:
                    return Auto.class;
            }
        }
        if (obj.has("capacidadCarga") || obj.has("cantidadEjes")) {
            return Camion.class;
        }
        if (obj.has("cilindrada") || obj.has("tipoMoto")) {
            return Moto.class;
        }
        return Auto.class;
    }
}

package ui;

import comparadores.ComparatorPorAnio;
import comparadores.ComparatorPorMarca;
import entidades.Auto;
import entidades.Camion;
import entidades.EstadoVehiculo;
import entidades.Moto;
import entidades.TipoAuto;
import entidades.TipoCombustible;
import entidades.TipoMoto;
import entidades.Vehiculo;
import excepciones.VehiculoDuplicadoException;
import gestores.GestorVehiculos;
import interfaces.IteradorPersonalizado;
import persistencia.PersistenciaCSV;
import persistencia.PersistenciaDAT;
import persistencia.PersistenciaJSON;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class MainController {

    private GestorVehiculos<Vehiculo> gestor = new GestorVehiculos<>();
    private ObservableList<Vehiculo> observableLista = FXCollections.observableArrayList();

    @FXML
    private TableView<Vehiculo> tablaVehiculos;
    @FXML
    private TableColumn<Vehiculo, String> colTipo;
    @FXML
    private TableColumn<Vehiculo, String> colMarca;
    @FXML
    private TableColumn<Vehiculo, String> colModelo;
    @FXML
    private TableColumn<Vehiculo, Integer> colAnio;
    @FXML
    private TableColumn<Vehiculo, String> colPatente;
    @FXML
    private TableColumn<Vehiculo, String> colEstado;
    @FXML
    private TableColumn<Vehiculo, String> colCombustible;
    @FXML
    private TableColumn<Vehiculo, Double> colImpuesto;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtAnio;
    @FXML
    private TextField txtPatente;
    @FXML
    private TextField txtExtra1;
    @FXML
    private TextField txtExtra2;
    @FXML
    private ComboBox<String> cmbTipo;
    @FXML
    private ComboBox<EstadoVehiculo> cmbEstado;
    @FXML
    private ComboBox<TipoCombustible> cmbCombustible;
    @FXML
    private ComboBox<String> cmbOrdenar;
    @FXML
    private ComboBox<String> cmbFiltrar;
    @FXML
    private Label lblMensaje;

    @FXML
    private void initialize() {
        cmbTipo.getItems().addAll("Auto", "Camion", "Moto");
        cmbTipo.setValue("Auto");

        cmbEstado.getItems().addAll(EstadoVehiculo.values());
        cmbEstado.setValue(EstadoVehiculo.DISPONIBLE);

        cmbCombustible.getItems().addAll(TipoCombustible.values());
        cmbCombustible.setValue(TipoCombustible.GASOLINA);

        cmbOrdenar.getItems().addAll("Natural (Anio)", "Por Marca", "Por Anio");
        cmbOrdenar.setValue("Natural (Anio)");

        cmbFiltrar.getItems().addAll("Todos", "Disponibles", "En Mantenimiento", "Vendidos",
                "Reservados", "Autos", "Camiones", "Motos", "Anio >= 2020");
        cmbFiltrar.setValue("Todos");

        configurarColumnasTabla();

        tablaVehiculos.setItems(observableLista);
        VBox.setVgrow(tablaVehiculos, Priority.ALWAYS);

        tablaVehiculos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarSeleccionEnFormulario(newVal);
            }
        });
    }

    private void configurarColumnasTabla() {
        colTipo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipoVehiculo()));
        colMarca.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMarca()));
        colModelo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModelo()));
        colAnio.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAnio()).asObject());
        colPatente.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPatente()));
        colEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado().getDescripcion()));
        colCombustible.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCombustible().getDescripcion()));
        colImpuesto.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().calcularImpuesto()).asObject());
    }

    private void cargarSeleccionEnFormulario(Vehiculo v) {
        txtMarca.setText(v.getMarca());
        txtModelo.setText(v.getModelo());
        txtAnio.setText(String.valueOf(v.getAnio()));
        txtPatente.setText(v.getPatente());
        cmbEstado.setValue(v.getEstado());
        cmbCombustible.setValue(v.getCombustible());
        cmbTipo.setValue(v.getTipoVehiculo());

        if (v instanceof Auto) {
            Auto a = (Auto) v;
            txtExtra1.setText(a.getTipoAuto().name());
            txtExtra2.setText(String.valueOf(a.getCantPuertas()));
        } else if (v instanceof Camion) {
            Camion c = (Camion) v;
            txtExtra1.setText(String.valueOf(c.getCapacidadCarga()));
            txtExtra2.setText(String.valueOf(c.getCantidadEjes()));
        } else if (v instanceof Moto) {
            Moto m = (Moto) v;
            txtExtra1.setText(String.valueOf(m.getCilindrada()));
            txtExtra2.setText(m.getTipoMoto().name());
        }
    }

    private Vehiculo crearVehiculo(String tipo, String marca, String modelo, int anio, String patente,
                                   EstadoVehiculo estado, TipoCombustible combustible) {
        switch (tipo) {
            case "Camion":
                double capCarga = 5000;
                int ejes = 2;
                try { capCarga = Double.parseDouble(txtExtra1.getText()); } catch (Exception ignored) {}
                try { ejes = Integer.parseInt(txtExtra2.getText()); } catch (Exception ignored) {}
                return new Camion(marca, modelo, anio, patente, estado, combustible, capCarga, ejes);
            case "Moto":
                int cilindrada = 150;
                TipoMoto tipoMoto = TipoMoto.SCOOTER;
                try { cilindrada = Integer.parseInt(txtExtra1.getText()); } catch (Exception ignored) {}
                try { tipoMoto = TipoMoto.valueOf(txtExtra2.getText().toUpperCase()); } catch (Exception ignored) {}
                return new Moto(marca, modelo, anio, patente, estado, combustible, cilindrada, tipoMoto);
            default:
                TipoAuto tipoAuto = TipoAuto.SEDAN;
                int puertas = 4;
                try { tipoAuto = TipoAuto.valueOf(txtExtra1.getText().toUpperCase()); } catch (Exception ignored) {}
                try { puertas = Integer.parseInt(txtExtra2.getText()); } catch (Exception ignored) {}
                return new Auto(marca, modelo, anio, patente, estado, combustible, tipoAuto, puertas);
        }
    }

    // --- Acciones CRUD ---

    @FXML
    private void agregarVehiculo() {
        try {
            String marca = txtMarca.getText();
            String modelo = txtModelo.getText();
            int anio = Integer.parseInt(txtAnio.getText());
            String patente = txtPatente.getText();
            EstadoVehiculo estado = cmbEstado.getValue();
            TipoCombustible combustible = cmbCombustible.getValue();

            if (marca.isEmpty() || modelo.isEmpty() || patente.isEmpty()) {
                lblMensaje.setText("Error: Complete todos los campos obligatorios.");
                return;
            }

            Vehiculo v = crearVehiculo(cmbTipo.getValue(), marca, modelo, anio, patente, estado, combustible);
            gestor.agregarSeguro(v);
            actualizarTabla();
            limpiarFormulario();
            lblMensaje.setText("Vehiculo agregado: " + patente);
        } catch (NumberFormatException e) {
            lblMensaje.setText("Error: El anio debe ser un numero valido.");
        } catch (VehiculoDuplicadoException e) {
            lblMensaje.setText("Error: " + e.getMessage());
        } catch (Exception e) {
            lblMensaje.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void actualizarVehiculo() {
        Vehiculo seleccionado = tablaVehiculos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblMensaje.setText("Seleccione un vehiculo de la tabla para actualizar.");
            return;
        }
        try {
            String marca = txtMarca.getText();
            String modelo = txtModelo.getText();
            int anio = Integer.parseInt(txtAnio.getText());
            String patente = txtPatente.getText();
            EstadoVehiculo estado = cmbEstado.getValue();
            TipoCombustible combustible = cmbCombustible.getValue();

            Vehiculo nuevo = crearVehiculo(cmbTipo.getValue(), marca, modelo, anio, patente, estado, combustible);
            gestor.actualizar(seleccionado, nuevo);
            actualizarTabla();
            limpiarFormulario();
            lblMensaje.setText("Vehiculo actualizado.");
        } catch (NumberFormatException e) {
            lblMensaje.setText("Error: El anio debe ser un numero valido.");
        } catch (Exception e) {
            lblMensaje.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarVehiculo() {
        Vehiculo seleccionado = tablaVehiculos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblMensaje.setText("Seleccione un vehiculo para eliminar.");
            return;
        }
        gestor.eliminar(seleccionado);
        actualizarTabla();
        limpiarFormulario();
        lblMensaje.setText("Vehiculo eliminado.");
    }

    @FXML
    private void limpiarFormulario() {
        txtMarca.clear();
        txtModelo.clear();
        txtAnio.clear();
        txtPatente.clear();
        txtExtra1.clear();
        txtExtra2.clear();
        cmbEstado.setValue(EstadoVehiculo.DISPONIBLE);
        cmbCombustible.setValue(TipoCombustible.GASOLINA);
        cmbTipo.setValue("Auto");
        tablaVehiculos.getSelectionModel().clearSelection();
    }

    @FXML
    private void ordenarVehiculos() {
        String criterio = cmbOrdenar.getValue();
        if ("Por Marca".equals(criterio)) {
            gestor.ordenarConComparator(new ComparatorPorMarca());
        } else if ("Por Anio".equals(criterio)) {
            gestor.ordenarConComparator(new ComparatorPorAnio());
        } else {
            gestor.ordenarNatural();
        }
        actualizarTabla();
        lblMensaje.setText("Ordenado por: " + criterio);
    }

    @FXML
    private void filtrarVehiculos() {
        String filtro = cmbFiltrar.getValue();
        List<Vehiculo> resultado;
        switch (filtro) {
            case "Disponibles": resultado = gestor.filtrarPorEstado(EstadoVehiculo.DISPONIBLE); break;
            case "En Mantenimiento": resultado = gestor.filtrarPorEstado(EstadoVehiculo.EN_MANTENIMIENTO); break;
            case "Vendidos": resultado = gestor.filtrarPorEstado(EstadoVehiculo.VENDIDO); break;
            case "Reservados": resultado = gestor.filtrarPorEstado(EstadoVehiculo.RESERVADO); break;
            case "Autos": resultado = gestor.filtrar(v -> v instanceof Auto); break;
            case "Camiones": resultado = gestor.filtrar(v -> v instanceof Camion); break;
            case "Motos": resultado = gestor.filtrar(v -> v instanceof Moto); break;
            case "Anio >= 2020": resultado = gestor.filtrarPorAnioMinimo(2020); break;
            default: resultado = gestor.listar(); break;
        }
        observableLista.setAll(resultado);
        lblMensaje.setText("Filtro: " + filtro + " (" + resultado.size() + " resultados)");
    }

    // --- Funciones Funcionales ---

    @FXML
    private void incrementarAnio() {
        gestor.incrementarAnio(1);
        actualizarTabla();
        lblMensaje.setText("Anio incrementado +1 a todos (Consumer)");
    }

    @FXML
    private void cambiarTodosADisponible() {
        gestor.cambiarEstado(EstadoVehiculo.DISPONIBLE);
        actualizarTabla();
        lblMensaje.setText("Estado cambiado a DISPONIBLE para todos (Consumer)");
    }

    @FXML
    private void mostrarResumen() {
        List<String> resumen = gestor.convertirAStrings(v -> v.getTipoVehiculo() + " " + v.getMarca() + " " + v.getModelo());
        StringBuilder sb = new StringBuilder();
        for (String s : resumen) {
            sb.append("- ").append(s).append("\n");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resumen de Vehiculos");
        alert.setHeaderText("Usando Function para convertir a String (" + resumen.size() + " vehiculos)");
        alert.setContentText(sb.toString());
        alert.showAndWait();
        lblMensaje.setText("Resumen generado con " + resumen.size() + " vehiculos (Function)");
    }

    @FXML
    private void iterarVehiculos() {
        IteradorPersonalizado<Vehiculo> it = gestor.getIterador();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (it.hasNext()) {
            Vehiculo v = it.next();
            sb.append(i).append(": ").append(v.getPatente()).append(" - ").append(v.getMarca())
                    .append(" ").append(v.getModelo()).append("\n");
            i++;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Iterator Personalizado");
        alert.setHeaderText("Recorriendo la lista (" + i + " vehiculos)");
        alert.setContentText(sb.toString());
        alert.showAndWait();
        lblMensaje.setText("Iterator recorrio " + i + " vehiculos");
    }

    // --- Persistencia ---

    @FXML
    private void guardarDAT() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Guardar .dat");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("DAT", "*.dat"));
            fc.setInitialFileName("vehiculos.dat");
            File file = fc.showSaveDialog(tablaVehiculos.getScene().getWindow());
            if (file != null) {
                PersistenciaDAT.serializar(gestor.listar(), file.getAbsolutePath());
                lblMensaje.setText("Guardado en .dat: " + file.getName());
            }
        } catch (Exception e) {
            lblMensaje.setText("Error al guardar .dat: " + e.getMessage());
        }
    }

    @FXML
    private void cargarDAT() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Cargar .dat");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("DAT", "*.dat"));
            File file = fc.showOpenDialog(tablaVehiculos.getScene().getWindow());
            if (file != null) {
                List<Vehiculo> cargados = PersistenciaDAT.deserializar(file.getAbsolutePath());
                gestor = new GestorVehiculos<>();
                cargados.forEach(gestor::agregar);
                actualizarTabla();
                lblMensaje.setText("Cargado .dat: " + cargados.size() + " vehiculos.");
            }
        } catch (Exception e) {
            lblMensaje.setText("Error al cargar .dat: " + e.getMessage());
        }
    }

    @FXML
    private void guardarCSV() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Guardar CSV");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            fc.setInitialFileName("vehiculos.csv");
            File file = fc.showSaveDialog(tablaVehiculos.getScene().getWindow());
            if (file != null) {
                PersistenciaCSV.guardarCSV(gestor.listar(), file.getAbsolutePath());
                lblMensaje.setText("Guardado en CSV: " + file.getName());
            }
        } catch (Exception e) {
            lblMensaje.setText("Error al guardar CSV: " + e.getMessage());
        }
    }

    @FXML
    private void cargarCSV() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Cargar CSV");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            File file = fc.showOpenDialog(tablaVehiculos.getScene().getWindow());
            if (file != null) {
                List<Vehiculo> cargados = PersistenciaCSV.cargarCSV(file.getAbsolutePath());
                gestor = new GestorVehiculos<>();
                cargados.forEach(gestor::agregar);
                actualizarTabla();
                lblMensaje.setText("Cargado CSV: " + cargados.size() + " vehiculos.");
            }
        } catch (Exception e) {
            lblMensaje.setText("Error al cargar CSV: " + e.getMessage());
        }
    }

    @FXML
    private void guardarJSON() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Guardar JSON");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
            fc.setInitialFileName("vehiculos.json");
            File file = fc.showSaveDialog(tablaVehiculos.getScene().getWindow());
            if (file != null) {
                PersistenciaJSON.guardarJSON(gestor.listar(), file.getAbsolutePath());
                lblMensaje.setText("Guardado en JSON: " + file.getName());
            }
        } catch (Exception e) {
            lblMensaje.setText("Error al guardar JSON: " + e.getMessage());
        }
    }

    @FXML
    private void cargarJSON() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Cargar JSON");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
            File file = fc.showOpenDialog(tablaVehiculos.getScene().getWindow());
            if (file != null) {
                List<Vehiculo> cargados = PersistenciaJSON.cargarJSON(file.getAbsolutePath());
                gestor = new GestorVehiculos<>();
                cargados.forEach(gestor::agregar);
                actualizarTabla();
                lblMensaje.setText("Cargado JSON: " + cargados.size() + " vehiculos.");
            }
        } catch (Exception e) {
            lblMensaje.setText("Error al cargar JSON: " + e.getMessage());
        }
    }

    @FXML
    private void exportarTXT() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Exportar TXT");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            fc.setInitialFileName("reporte_vehiculos.txt");
            File file = fc.showSaveDialog(tablaVehiculos.getScene().getWindow());
            if (file != null) {
                PersistenciaJSON.exportarTXT(gestor.listar(), file.getAbsolutePath());
                lblMensaje.setText("Reporte exportado: " + file.getName());
            }
        } catch (Exception e) {
            lblMensaje.setText("Error al exportar: " + e.getMessage());
        }
    }

    // --- Utilidades ---

    private void actualizarTabla() {
        observableLista.setAll(gestor.listar());
    }
}

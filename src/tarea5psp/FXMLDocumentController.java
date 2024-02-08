package tarea5psp;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtPass;
    @FXML
    private TextField txtRol;
    @FXML
    private TextField txtPuerto;
    @FXML
    private TextArea txtArea;
    @FXML
    private Button botonAltas;
    @FXML
    private Button botonArrancar;
    
    Validacion validacion;
    
    Servidor serverSocket;
    @FXML
    private Label label;
    
    @FXML
    private void validar(ActionEvent event) {
        
        String usuario = txtUsuario.getText();
        String contrasena = txtPass.getText();
        int idRol = validacion.buscar(usuario, contrasena);
        if (validacion.permisos(4, idRol)) {
            botonAltas.setDisable(false);
            
        }
        if (validacion.permisos(5, idRol)) {
            botonArrancar.setDisable(false);
        }
        
    }
    
    @FXML
    private void altas(ActionEvent event) {
        int idRol = Integer.parseInt(txtRol.getText());
        String usuario = txtUsuario.getText();
        String contrasena = txtPass.getText();
        validacion.altas(usuario, contrasena, idRol);
        
    }
    
    @FXML
    private void arrancarServidor(ActionEvent event) {
        if (!txtPuerto.getText().equals("")) {
            
            txtArea.appendText("******************************************\r\n"
                    + //
                    "\r\n"
                    + //
                    "*PSP - Tarea Individual 4 - Cliente / Servidor*\r\n"
                    + //
                    "\r\n"
                    + //
                    "******************************************\r\n"
                    + //
                    "\r\n"
                    + //
                    "* Miriam Gallardo González-Amor *\r\n"
                    + //
                    "\r\n"
                    + //
                    "******************************************\r\n"
                    + //
                    "\r\n"
                    + //
                    "* 53772609N     *                     \r\n");
            try {
                this.serverSocket = new Servidor(20, Integer.parseInt(txtPuerto.getText()));
                serverSocket.start();
                txtArea.appendText("servidor encendido");
                
                
            } catch (Exception exception) {
                
            }
        } else {
            txtArea.appendText("Introduce un puerto para poder arrancar el servidor");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validacion = new Validacion();
        //Asoaciamos el fichero serverKey.jks" al keystore
        System.setProperty("javax.net.ssl.keyStore", "serverKey.jks");
        //Le pasamos la contraseña
        System.setProperty("javax.net.ssl.keyStorePassword", "claveSecreta");
    }
    
}

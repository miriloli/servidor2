package tarea5psp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Validacion {

    private String usuario;
    private String password;
    private int rol;

    public Validacion() {

    }

    public Validacion(String usuario, String password, int rol) {
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;

    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int altas(String usuario, String password, int rol) {

        int resultado = 0;
        try {
            String sha256 = pruebasSha256(password);
            Class.forName("com.mysql.jdbc.Driver");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/tarea5PSP?user=tarea");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/tarea5PSP","root","");
            //Connection connection= DriverManager.getConnection("jdbc:mysql://localhost/Tarea5PSP","tarea","12345");
            String sql = "insert into user values(?,?,?);";
            PreparedStatement sentencia = connection.prepareStatement(sql);
            sentencia.setString(1, usuario);
            sentencia.setString(2, sha256);
            sentencia.setInt(3, rol);

            resultado = sentencia.executeUpdate();
            sentencia.close();
            connection.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            resultado = -1;
        }
        return resultado;
    }

    public int buscar(String usuario, String password) {

        int resultado = 0;
        try {
            String sha256 = pruebasSha256(password);
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/tarea5PSP","root","");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Tarea5PSP", "tarea", "12345");
            //Connection connection= DriverManager.getConnection("jdbc:mysql://localhost/Tarea5PSP","tarea","12345");
            String sql = "select * from user where usuario=? and password=?";
            PreparedStatement sentencia = connection.prepareStatement(sql);
            sentencia.setString(1, usuario);
            sentencia.setString(2, sha256);

            ResultSet r = sentencia.executeQuery();

            if (r.next()) {
                resultado = r.getInt(3);//Nos quedamos con el rol para setear las funciones Enable/Disable
            }
            sentencia.close();
            connection.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            resultado = -1;
        }
        return resultado;
    }

    public boolean permisos(int idFuncion, int idRol) {

        boolean resultado = false;
        try {
            //String sha256 = pruebasSha256(password);
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/tarea5PSP","root","");
            String sql = "select * from r_f where idRol=? and idFuncion=?";
            PreparedStatement sentencia = connection.prepareStatement(sql);
            sentencia.setInt(1, idRol);
            sentencia.setInt(2, idFuncion);

            ResultSet r = sentencia.executeQuery();

            if (r.next()) {
                resultado = true;
            }
            sentencia.close();
            connection.close();
        } catch (Exception exception) {
            resultado = false;
        }
        return resultado;
    }

    private String pruebasSha256(String comando) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("sha-256");
        byte[] cifrado = messageDigest.digest(comando.getBytes());
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < cifrado.length; i++) {
            stringBuffer.append(Integer.toHexString(0xFF & cifrado[i]));
        }

        return stringBuffer.toString();
    }
}

package A_Modelo;
import java.sql.Connection;
import java.sql.DriverManager;//clase para establecer la conexión con la BD.
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

//Mecanísmo de persistencia de este pequeño software.
public class B_Conexion {
    
    //JDBC significa Java™ EE Database Connectivity (conectividad de bases de datos Java).
    protected String DriverXYZ = "com.mysql.cj.jdbc.Driver";
    protected String url = "jdbc:mysql://localhost:3306/Testeo?serverTimezone=UTC";//?serverTimezone=UTC
    protected String user = "root", pass = "holamundo2022";
    public Connection con;//permite establecer conexión a la BD y la apertura de flujo de datos.

    public Connection getConnection() { //método para conectar a nuestra base de datos
        try {
            //cargamos el driver que vamos a utilizar
            Class.forName(DriverXYZ);//registramos el driver que vamos a utilizar
            //Conectamos con la BD.
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Exepción: " + e.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(B_Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}

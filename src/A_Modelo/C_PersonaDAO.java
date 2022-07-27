package A_Modelo;

//El acceso a datos se realiza mediante DAO (DATA ACCESS OBJECT).
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class C_PersonaDAO {

    //Sólo se accede a los datos através de los métodos definidos en el DAO
    //Las instancias de PreparedStatement contienen una sentencia SQL que ya ha sido compilada
    PreparedStatement psStatement;//Un objeto que representa una instrucción SQL precompilada. 
    //Una tabla de datos que representa un conjunto de resultados de una base de datos
    ResultSet rs;//se genera al ejecutar una declaración que consulta la base de datos.
    Connection CanalDConexion;
    B_Conexion Claseconexion = new B_Conexion();
    A_Persona person = new A_Persona();
    ResultSetMetaData rsmd;

    public List listarDao() {
        List<A_Persona> datos = new ArrayList<>();
        try {
            CanalDConexion = Claseconexion.getConnection();
            psStatement = CanalDConexion.prepareStatement("select * from TesteoAlumnos");
            rs = psStatement.executeQuery();//alojamos el conjunto de resultados en la variable.
            /*solo tiene un cursor que se mueve hacia adelante y no es actualizables.
            Método que mueve el cursor una fila dentro del ResultSet. Inicialmente 
            el cursor se sitúa antes de la primera fila. Cuando el cursor se posiciona 
            después de la última fila el método devuelve false.*/
            while (rs.next()) { //next se utiliza para desplazarse por filas en un ResultSet de una en una.
                A_Persona personaa = new A_Persona();
                personaa.setId(rs.getInt(1));
                personaa.setNom(rs.getString(2));
                personaa.setCorreo(rs.getString(3));
                personaa.setTelefono(rs.getString(4));
                datos.add(personaa);
            }
        } catch (SQLException e) {
        }
        return datos;
    }

    public int agregar(A_Persona per) {
        int r = 0;
        String sql = "insert into TesteoAlumnos(Nombres,Correo,Telefono)values(?,?,?)";//? en lugar de los parámetros.
        try {
            CanalDConexion = Claseconexion.getConnection();
            psStatement = CanalDConexion.prepareStatement(sql);
            psStatement.setString(1, per.getNom());
            psStatement.setString(2, per.getCorreo());
            psStatement.setString(3, per.getTelefono());
            r = psStatement.executeUpdate();

            if (r == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lo sentimos: " + e.getMessage());
            return r = 0;
        }
    }

    public int Actualizar(A_Persona per) {
        int r = 0;
        String sql = "update TesteoAlumnos set Nombres=?,Correo=?,Telefono=? where Id=?";
        try {
            CanalDConexion = Claseconexion.getConnection();
            psStatement = CanalDConexion.prepareStatement(sql);
            psStatement.setString(1, per.getNom());
            psStatement.setString(2, per.getCorreo());
            psStatement.setString(3, per.getTelefono());
            psStatement.setInt(4, per.getId());
            r = psStatement.executeUpdate();
            if (r == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException e) {
        }
        return r;
    }

    public int Delete(int id) {
        int r = 0;
        String sql = "delete from TesteoAlumnos where Id=" + id;
        try {
            CanalDConexion = Claseconexion.getConnection();
            psStatement = CanalDConexion.prepareStatement(sql);
            r = psStatement.executeUpdate();
        } catch (HeadlessException | SQLException e) {
        }
        return r;
    }

    public void Buscar(int id) {
        try {
            CanalDConexion = Claseconexion.getConnection();
            psStatement = CanalDConexion.prepareStatement("SELECT * FROM TesteoAlumnos WHERE Id = " + id);
            rs = psStatement.executeQuery();//la variable rs trae los valores de la consulta
            if (rs.next()) { //para leer varias posibles filas
                //System.out.println("Usuario encontrado");
                JOptionPane.showMessageDialog(null, "Usuario encontrado\nId:               "
                        + rs.getString("Id") + "\nNombres:    "
                        + rs.getString("Nombres") + "\nCorreo:       "
                        + rs.getString("Correo") + "\nTeléfono:    " + rs.getString("Telefono"),
                        "Felicidades", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error usuario no encontrado", "Lo sentimos", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Exception: " + e.getMessage(), "Lo sentimos", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Este método se mantiene aquí pero no se está utilizando.
    DefaultTableModel modelo;

    public void setearTabla(String sql) {
        try {
            modelo = new DefaultTableModel();//modelo.setRowCount(0);//elimina todas las filas
            CanalDConexion = Claseconexion.getConnection();
            psStatement = CanalDConexion.prepareStatement(sql);
            rs = psStatement.executeQuery();
            rsmd = (ResultSetMetaData) rs.getMetaData();
            int col = rsmd.getColumnCount();

            for (int i = 1; i <= col; i++) {
                modelo.addColumn(rsmd.getColumnLabel(i));
            }

            if (rs.next()) {  //mientras haya registros vamos a crear un vector
                String filas[] = new String[col];
                for (int i = 0; i < col; i++) {
                    filas[i] = rs.getString(i + 1);
                }
                modelo.addRow(filas);//modelo.setRowCount(0);
            } else {
                JOptionPane.showMessageDialog(null, "Id no encontrado");
            }
            //Interfaz.tabla.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Exception: " + e.getMessage());
        }
    }

    public void limpiarTabla2() {
        modelo.setRowCount(0);
    }
}

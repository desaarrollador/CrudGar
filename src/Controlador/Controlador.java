package Controlador;

//El controlador actua como como intermediario entre el modelo y la vista.
//Gestionando el flujo de información entre ellos.
import A_Modelo.A_Persona;
import A_Modelo.C_PersonaDAO;
import B_Vista.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Controlador implements ActionListener {

    C_PersonaDAO PersonaDao = new C_PersonaDAO();//creando instancias para acceder a los metodos 
    A_Persona ObjetoPersona = new A_Persona(); //así podemos interconectar las clases
    vista Interfaz = new vista();
    //Se usa DefaultTableModel debido a que JTableModel no es una clase en si sino una interfaz por lo que no puede instanciarla.
    DefaultTableModel modeloTabadefault = new DefaultTableModel();

    @SuppressWarnings("LeakingThisInConstructor") //para excluir avisos de botones no inicializados.
    public Controlador(vista v) {
        this.Interfaz = v;//trabajamos con los métodos de la clase Jframe vista
        this.Interfaz.BtnListar.addActionListener(this);
        this.Interfaz.BtnNuevo.addActionListener(this);
        this.Interfaz.BtnAgregar.addActionListener(this);
        this.Interfaz.BtnEliminar.addActionListener(this);
        this.Interfaz.BtnEditar.addActionListener(this);
        this.Interfaz.BtnActualizar.addActionListener(this);
        this.Interfaz.BtnBuscar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) { //controlador de eventos bottons
        if (e.getSource() == Interfaz.BtnListar) {
            limpiarTabla();
            listar(Interfaz.tabla);
            nuevo();
        }
        if (e.getSource() == Interfaz.BtnNuevo) {
            nuevo();
        }
        if (e.getSource() == Interfaz.BtnAgregar) {
            Agregar();
        }
        if (e.getSource() == Interfaz.BtnEliminar) {
            Eliminar();
            listar(Interfaz.tabla);
            nuevo();
        }
        if (e.getSource() == Interfaz.BtnEditar) {
            listar(Interfaz.tabla);
            Editar();
        }
        if (e.getSource() == Interfaz.BtnActualizar) {
            Actualizar();
            listar(Interfaz.tabla);

        }
        if (e.getSource() == Interfaz.BtnBuscar) {
            listar(Interfaz.tabla);
            Buscar();

        }
    }

    //métodos primarios de esta clase.
    public void listar(JTable tabla) {
        centrarCeldas(tabla);
        //obteniendo los datos de la tabla establecida en la clase vista y se la asignamos a modelodefault
        modeloTabadefault = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modeloTabadefault);
        List<A_Persona> DatosAlojados = PersonaDao.listarDao();//obtebemos los datos
        Object[] objeto = new Object[4];//cantidad de columnas 4 que tiene mi tabla TesteoAlumnos
        //el límite de nuestro bucle es la cantidad de filas que tiene mi tabla TesteoALumnos
        for (int i = 0; i < DatosAlojados.size(); i++) {
            objeto[0] = DatosAlojados.get(i).getId();//columna 0 fila 0 obten id
            objeto[1] = DatosAlojados.get(i).getNom();
            objeto[2] = DatosAlojados.get(i).getCorreo();
            objeto[3] = DatosAlojados.get(i).getTelefono();
            modeloTabadefault.addRow(objeto);
        }
        //ajustar el tamaño de las celdas en JTable
        tabla.setRowHeight(27);
        tabla.setRowMargin(14);

    }

    public void Agregar() {
        if (EstaVacio() == true) {
            JOptionPane.showMessageDialog(null, "Debes llenar todos los campos", "Cuidad", JOptionPane.ERROR_MESSAGE);
        } else {
            String nom = Interfaz.txtNom.getText();//obtenemos los datos
            String correo = Interfaz.txtCorreo.getText();//contenidos
            String tel = Interfaz.txtTel.getText();//en los texfield
            ObjetoPersona.setNom(nom);
            ObjetoPersona.setCorreo(correo);
            ObjetoPersona.setTelefono(tel);
            int r = PersonaDao.agregar(ObjetoPersona);
            if (r == 1) {
                JOptionPane.showMessageDialog(Interfaz, "Usuario Agregado con Exito.", "Felicidades", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(Interfaz, "Error");
            }
            limpiarTabla();
            listar(Interfaz.tabla);
            nuevo();
        }

    }

    public void Eliminar() {
        int fila = Interfaz.tabla.getSelectedRow();//retorna el indice de la fila seleccionada.
        if (fila == -1) {
            JOptionPane.showMessageDialog(Interfaz, "Debe Seleccionar una Fila...!!!");
        } else {
            int id = Integer.parseInt((String) Interfaz.tabla.getValueAt(fila, 0).toString());
            PersonaDao.Delete(id);
            JOptionPane.showMessageDialog(Interfaz, "Usuario Eliminado...!!!");
        }
        limpiarTabla();
    }

    public void Editar() {
        int fila = Interfaz.tabla.getSelectedRow();//retorna el índice de la fila seleccionada
        if (fila == -1) {
            JOptionPane.showMessageDialog(Interfaz, "Debee Seleccionar Una fila..!!", "Cuidado", JOptionPane.ERROR_MESSAGE);
        } else {
            //getValueAt(fila, 0) Devuelve el valor de la celda en la fila y la columna
            Integer id = Integer.parseInt((String) Interfaz.tabla.getValueAt(fila, 0).toString());//toString se utiliza para convertir a String
            String nom = (String) Interfaz.tabla.getValueAt(fila, 1);
            String correo = (String) Interfaz.tabla.getValueAt(fila, 2);
            String tel = (String) Interfaz.tabla.getValueAt(fila, 3);
            //String i = id.toString();
            Interfaz.txtId.setText("" + id);
            Interfaz.txtNom.setText(nom);
            Interfaz.txtCorreo.setText(correo);
            Interfaz.txtTel.setText(tel);
        }
    }

    public void Actualizar() {
        if (Interfaz.txtId.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "No se Identifica el Id \nDebe selecionar la opcion Editar", "Cuidado", JOptionPane.ERROR_MESSAGE);
        } else {
            int id = Integer.parseInt(Interfaz.txtId.getText());
            String nom = Interfaz.txtNom.getText();
            String correo = Interfaz.txtCorreo.getText();
            String tel = Interfaz.txtTel.getText();
            ObjetoPersona.setId(id);
            ObjetoPersona.setNom(nom);
            ObjetoPersona.setCorreo(correo);
            ObjetoPersona.setTelefono(tel);
            int r = PersonaDao.Actualizar(ObjetoPersona);
            if (r == 1) {
                JOptionPane.showMessageDialog(Interfaz, "Usuario Actualizado con Exito.");
            } else {
                JOptionPane.showMessageDialog(Interfaz, "Error");
            }
        }
        limpiarTabla();
    }

    public void Buscar() {
        String id;
        boolean isNumeric;
        int conversionID = 0;
        try {
            do {
                id = JOptionPane.showInputDialog(null, "Dame el Id a buscar");
                isNumeric = id.chars().allMatch(Character::isDigit);
            } while (isNumeric == false);
            conversionID = Integer.parseInt(id);
        } catch (NumberFormatException | NullPointerException e) {
        }
        PersonaDao.Buscar(conversionID);
    }

    //métodos secundarios de esta clase.
    void nuevo() {
        Interfaz.txtId.setText("");
        Interfaz.txtNom.setText("");
        Interfaz.txtTel.setText("");
        Interfaz.txtCorreo.setText("");
        Interfaz.txtNom.requestFocus();
    }

    void centrarCeldas(JTable tabla) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < Interfaz.tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    public void limpiarTabla() {
        for (int i = 0; i < Interfaz.tabla.getRowCount(); i++) {
            modeloTabadefault.removeRow(i);
            i = i - 1;
        }
    }

    private boolean EstaVacio() {
        String nom = Interfaz.txtNom.getText();
        String correo = Interfaz.txtCorreo.getText();
        String tel = Interfaz.txtTel.getText();
        return nom.equals("") || correo.equals("") || tel.equals(""); //esta vacía
    }

}

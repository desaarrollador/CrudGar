package A_Modelo;

//El modelo define que datos debe tener la aplicación
//su lógica de negocio (funcionalidad del sistema)y sus mecanismos de persistencia.
public class A_Persona {

    //si el estado de estos datos cambia el modelo generalmente notificará a la vista
    private int id;
    private String nom;
    private String correo;
    private String telefono;

    public A_Persona() {
    }

    public A_Persona(int id, String nom, String correo, String telefono) {
        this.id = id;
        this.nom = nom;
        this.correo = correo;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

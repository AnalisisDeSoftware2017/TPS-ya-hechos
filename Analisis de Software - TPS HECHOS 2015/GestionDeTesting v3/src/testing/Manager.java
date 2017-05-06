package testing;


/**
 * Clase que funciona como punto de entrada de la aplicacion. Instancia la clase
 * MainView que, junto con Analizador, se encargará de las tareas a ejecutar.
 */
public class Manager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainView vista = new MainView();
        
        java.awt.EventQueue.invokeLater(vista);
    }
}

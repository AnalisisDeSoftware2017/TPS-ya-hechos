/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlpac;

import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrador
 */
public class DatosPacienteW extends javax.swing.JFrame {
    
    String codPac="";
    String nomPac="";
    /**
     * Creates new form DatosPacienteW
     */
    public DatosPacienteW() { //CONSTRUCTOR 1
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/iconoCM.png")).getImage()); //Agrego el icono a la ventana
        setSize(400,400); //le doy tamaño
        setResizable(false); // Anula poder maximizar la ventana.
        setLocationRelativeTo(null); // La ventana en el centro.        
        setTitle("Ingreso de Datos"); // Le da un titulo a la ventana.
    }
    
     public DatosPacienteW(String codigo) { //CONSTRUCTOR para crear un paciente desde otras clases.
         this.codPac=codigo; // Guardo el dato pasado. 
    }
    
    public String getCodigoPac(){
        // Obtenemos el codigo del paciente ingresado.
        this.codPac=codigoPac.getText();
        return codPac;
    }
    
    public String getNombrePac(){
        // Obtenemos el nombre del paciente ingresado.
        this.nomPac=nombrePac.getText();
        return nomPac;
    }
    
    private static boolean soloLetras(String cadena){
      if(cadena.matches("[áéíóúña-zÑÁÉÍÓÚA-Z][áéíóúña-zÁÉÍÓÚÑA-z ]*")) // Se valida en esta función que no puede ser vacía la cadena.
        return true; //Son letras.
      else
        return false; //No son letras.
    }

    private static boolean alfanumerico(String cadena){
      if(cadena.matches("[0-9a-zA-z]+")) // Tampoco puede ser vacía una cadena.
        return true; // Es alfanimerico
      else
        return false; //No es alfanumerico
    }
    
    public int validarDatosPac(){
        String mensaje="";
        int ret=0; // comienza siendo valido.
        if(!alfanumerico(codPac)){ // El codigo del paciente debe ser alfanumerico sin espacios.
                mensaje="El código no es válido o no se ha ingresado. "; //Se guarda mensaje de aviso.
                ret++; //Ret se incrementa porque ya no es valido.
        }
        if(!soloLetras(nomPac)){ // El nombre del paciente sólo debe contener letras.
         mensaje= mensaje + "El nombre del paciente posee caracteres incorrectos o no se ha ingresado."; //Se guarda mensaje de aviso.
         ret++; // Ret deja de ser valido.
        }
        if(ret!=0)
            JOptionPane.showMessageDialog(null, mensaje);
        return ret; // Correctos o Incorrectos.
    }
    
    public boolean ExistePac(){
        boolean found = false; // Creo una variable boolena que será la que idique si existe o no el paciente.
        try {
            DataInputStream datopac = null; // Luego abro el archivo de pacientes para recorrerlo.
            datopac = new DataInputStream(new FileInputStream(System.getProperty("user.home") + "\\datopac.txt")); // abre el archivo de pacientes para lectura
            int sw1 = 1; // Bandera para recorrer el archivo hasta su fin. 
            while (sw1 != 0) {// recorre los registros
                try {
                    String codpa = EncryptHelper.Desencriptar(datopac.readUTF());// lee el registro
                    datopac.readUTF();
                    if (codpa.equals(codPac)){
                        /*compara el codigo digitado con el codigo del peciente de la
                        tabla "datopac" */
                        found = true; //ENCONTRADO
                    }
                } catch (EOFException e) {
                    sw1 = 0;
                }
            }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error al verificar la existencia del paciente"); // Mensaje inesperado de error.
        }
        return found; // Se devuelve true or false.
  }
    
    public void grabarDatosPac(){
        // Esta función se encarga de grabar los datos en el archivo de pacientes.
        try{
            DataOutputStream datopac = null; //Para luego abrir el archivo de pacientes para escritura con append habilitado.
            datopac = new DataOutputStream(new FileOutputStream(System.getProperty("user.home") + "\\datopac.txt", true)); //Se abre el arvhivo.
            // graba los datos en el archivo
            datopac.writeUTF(EncryptHelper.Encriptar(codPac)); //Se graba el codigo de paciente.
            datopac.writeUTF(EncryptHelper.Encriptar(nomPac)); // Se graba el nombre de paciente.
            JOptionPane.showMessageDialog(null, "Datos guardados de forma exitosa.");
        }catch(Exception ioe){
            JOptionPane.showMessageDialog(null, "Error al intentar abrir el archivo de Pacientes");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        codigoPac = new javax.swing.JTextField();
        nombrePac = new javax.swing.JTextField();
        ingresarBtn = new javax.swing.JButton();
        anterior = new javax.swing.JButton();
        borrarC = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        mINI = new javax.swing.JMenuItem();
        mIngDat = new javax.swing.JMenuItem();
        mInf = new javax.swing.JMenuItem();
        opciones = new javax.swing.JMenu();
        Salir = new javax.swing.JMenuItem();
        cerrarSesion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DATOS DEL PACIENTE");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 11, 380, 26);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Código:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(37, 94, 48, 17);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Nombre:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(31, 134, 54, 17);

        codigoPac.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        codigoPac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoPacActionPerformed(evt);
            }
        });
        codigoPac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                codigoPacKeyTyped(evt);
            }
        });
        getContentPane().add(codigoPac);
        codigoPac.setBounds(103, 93, 93, 21);

        nombrePac.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nombrePac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombrePacKeyTyped(evt);
            }
        });
        getContentPane().add(nombrePac);
        nombrePac.setBounds(103, 133, 210, 21);

        ingresarBtn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ingresarBtn.setText("Ingresar");
        ingresarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ingresarBtnActionPerformed(evt);
            }
        });
        ingresarBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ingresarBtnKeyTyped(evt);
            }
        });
        getContentPane().add(ingresarBtn);
        ingresarBtn.setBounds(275, 172, 90, 33);

        anterior.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        anterior.setText("Anterior");
        anterior.setMaximumSize(new java.awt.Dimension(77, 33));
        anterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anteriorActionPerformed(evt);
            }
        });
        anterior.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                anteriorKeyTyped(evt);
            }
        });
        getContentPane().add(anterior);
        anterior.setBounds(31, 251, 100, 33);

        borrarC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        borrarC.setText("Borrar Campos");
        borrarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarCActionPerformed(evt);
            }
        });
        borrarC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                borrarCKeyTyped(evt);
            }
        });
        getContentPane().add(borrarC);
        borrarC.setBounds(130, 172, 140, 33);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo.jpg"))); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(0, -10, 530, 390);

        jMenu4.setText("Ayuda");
        jMenu4.setToolTipText("");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Manual");
        jMenuItem4.setToolTipText("Vista de guía rápida de uso.");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuBar3.add(jMenu4);

        jMenu5.setText("Ir a");

        mINI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        mINI.setText("Menu Inicio");
        mINI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mINIActionPerformed(evt);
            }
        });
        jMenu5.add(mINI);

        mIngDat.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        mIngDat.setText("Ingreso de Datos");
        mIngDat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mIngDatActionPerformed(evt);
            }
        });
        jMenu5.add(mIngDat);

        mInf.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        mInf.setText("Informes");
        mInf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mInfActionPerformed(evt);
            }
        });
        jMenu5.add(mInf);

        jMenuBar3.add(jMenu5);

        opciones.setText("Opciones");

        Salir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        Salir.setText("Salir del Programa");
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });
        opciones.add(Salir);

        cerrarSesion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        cerrarSesion.setText("Cerrar Sesión");
        cerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarSesionActionPerformed(evt);
            }
        });
        opciones.add(cerrarSesion);

        jMenuBar3.add(opciones);

        setJMenuBar(jMenuBar3);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void anteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anteriorActionPerformed
        // TODO add your handling code here:
        IngresoPacientesW menu=new IngresoPacientesW();
        menu.setVisible(true); // Vuelve el menu de ingreso de datos
        dispose(); // Desaparece el ingreso de datos de paciente.
    }//GEN-LAST:event_anteriorActionPerformed

    private void ingresarBtnKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ingresarBtnKeyTyped
        // TODO add your handling code here:
                // Creamos un evento para que cuando se presione la tecla Enter sea como presionar el botón.
        //******************************************************************************************
        char cTeclaPresionada=evt.getKeyChar();
        // da click al detectar la tecla ENTER.
        if(cTeclaPresionada==KeyEvent.VK_ENTER){
            //Ejecuta el botón (dar click)
            ingresarBtn.doClick();
        }// fin del if.
    }//GEN-LAST:event_ingresarBtnKeyTyped

    private void anteriorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_anteriorKeyTyped
        // TODO add your handling code here:
                // Creamos un evento para que cuando se presione la tecla Enter sea como presionar el botón.
        //******************************************************************************************
        char cTeclaPresionada=evt.getKeyChar();
        // da click al detectar la tecla ENTER.
        if(cTeclaPresionada==KeyEvent.VK_ENTER){
            //Ejecuta el botón (dar click)
            anterior.doClick();
        }// fin del if.
    }//GEN-LAST:event_anteriorKeyTyped

    private void ingresarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ingresarBtnActionPerformed
        // TODO add your handling code here:        
            getCodigoPac(); //Obtenemos el codigo del paciente ingresado.
            getNombrePac(); // Obtenemos el nombre del paciente ingresado.
            if(validarDatosPac()==0){ // Si los datos estan bien ingresados, verifico la existencia del paciente.
                if(!ExistePac()) // Si el paciente no está registrado en el archivo entonces lo agrego.
                    grabarDatosPac(); //Se guardan los datos ingresados en el archivo.
                else
                    JOptionPane.showMessageDialog(null, "El código de paciente ya existe."); //Se informa que el paciente ya existe.
            }      
    }//GEN-LAST:event_ingresarBtnActionPerformed

    private void codigoPacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoPacActionPerformed
        // TODO add your handling code here:
        // Creamos un evento para que cuando se presione la tecla Enter sea como presionar el botón Ingresar.
        //******************************************************************************************

    }//GEN-LAST:event_codigoPacActionPerformed

    private void codigoPacKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoPacKeyTyped
        // TODO add your handling code here:
                char cTeclaPresionada=evt.getKeyChar();
        // da click en ingresar al detectar la tecla ENTER.
        if(cTeclaPresionada==KeyEvent.VK_ENTER){
            //Ejecuta el botón (dar click)
            ingresarBtn.doClick();
        }// fin del if.
    }//GEN-LAST:event_codigoPacKeyTyped

    private void nombrePacKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrePacKeyTyped
        // TODO add your handling code here:
                char cTeclaPresionada=evt.getKeyChar();
        // da click en ingresar al detectar la tecla ENTER.
        if(cTeclaPresionada==KeyEvent.VK_ENTER){
            //Ejecuta el botón (dar click)
            ingresarBtn.doClick();
        }// fin del if.
    }//GEN-LAST:event_nombrePacKeyTyped

    private void borrarCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_borrarCKeyTyped
        // TODO add your handling code here:
        char cTeclaPresionada=evt.getKeyChar();
        // da click al detectar la tecla ENTER.
        if(cTeclaPresionada==KeyEvent.VK_ENTER){
            //Ejecuta el botón (dar click)
            borrarC.doClick();
        }// fin del if.
    }//GEN-LAST:event_borrarCKeyTyped

    private void borrarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarCActionPerformed
        // TODO add your handling code here:
        codigoPac.setText(""); // Se borra el contenido del codigo.
        nombrePac.setText(""); // Se borra el contenido del nombre.
    }//GEN-LAST:event_borrarCActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        ManualW guiaRapida=new ManualW();
        guiaRapida.setVisible(true);
        setTitle("Manual de Ayuda");
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void mINIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mINIActionPerformed
        // TODO add your handling code here:
        MainW menu=new MainW();
        menu.setVisible(true); // Vuelve el menu proncipal
        dispose(); // Desaparece el informes.
    }//GEN-LAST:event_mINIActionPerformed

    private void mInfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mInfActionPerformed
        // TODO add your handling code here:
                // TODO add your handling code here:
        InformesW iw=new InformesW();
        iw.setVisible(true); // Hago visible la ventana de Informes.
        dispose(); // Desaparece el menu.
    }//GEN-LAST:event_mInfActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        // TODO add your handling code here:
        System.exit(0); // SE SALE DEL PROGRAMA.
    }//GEN-LAST:event_SalirActionPerformed

    private void cerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarSesionActionPerformed
        // TODO add your handling code here:
        Login log=new Login(); // Abre el log principal del sistema.
        log.setVisible(true); // Hace visible el log.
        dispose();//SE OCULTA
    }//GEN-LAST:event_cerrarSesionActionPerformed

    private void mIngDatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mIngDatActionPerformed
        // TODO add your handling code here:
        IngresoPacientesW ipw=new IngresoPacientesW();
        ipw.setVisible(true); // Hago visible la ventana de pacientes.    
        dispose(); // Para que se oculte el menu.
    }//GEN-LAST:event_mIngDatActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DatosPacienteW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DatosPacienteW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DatosPacienteW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DatosPacienteW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DatosPacienteW().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Salir;
    private javax.swing.JButton anterior;
    private javax.swing.JButton borrarC;
    private javax.swing.JMenuItem cerrarSesion;
    public javax.swing.JTextField codigoPac;
    private javax.swing.JButton ingresarBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem mINI;
    private javax.swing.JMenuItem mInf;
    private javax.swing.JMenuItem mIngDat;
    public javax.swing.JTextField nombrePac;
    private javax.swing.JMenu opciones;
    // End of variables declaration//GEN-END:variables
}

package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import controlador.logica_ventana;

public class ventana extends JFrame {
    public JTextField txt_nombres, txt_telefono, txt_email;
    public JButton btn_add, btn_eliminar;
    public JComboBox<String> cmb_categoria;
    public JCheckBox chb_favorito;
    public JTable tablaContactos;
    public DefaultTableModel modeloTabla;
    public JProgressBar barraProgreso;

    public ventana() {
        setTitle("GESTIÓN DE CONTACTOS - Sergio Guncay");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 600);
        setLocationRelativeTo(null);
        
        JTabbedPane pestanas = new JTabbedPane(); 

        // --- Panel Registro ---
        JPanel pRegistro = new JPanel(new BorderLayout());
        JPanel pForm = new JPanel(new GridLayout(5, 2, 10, 10));
        pForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        txt_nombres = new JTextField(); 
        txt_telefono = new JTextField(); 
        txt_email = new JTextField();
        cmb_categoria = new JComboBox<>(new String[]{"Elija una Categoria", "Familia", "Trabajo", "Amigos"});
        chb_favorito = new JCheckBox("CONTACTO FAVORITO");

        pForm.add(new JLabel("NOMBRES:")); pForm.add(txt_nombres);
        pForm.add(new JLabel("TELEFONO:")); pForm.add(txt_telefono);
        pForm.add(new JLabel("EMAIL:")); pForm.add(txt_email);
        pForm.add(new JLabel("CATEGORÍA:")); pForm.add(cmb_categoria);
        pForm.add(new JLabel("OPCION:")); pForm.add(chb_favorito);

        // --- PANEL DE BOTONES  ---
        JPanel pContenedorBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        btn_add = new JButton("AGREGAR");
        btn_eliminar = new JButton("ELIMINAR");
        
        // Ajustamos el tamaño
        Dimension tamañoBoton = new Dimension(180, 50);
        btn_add.setPreferredSize(tamañoBoton);
        btn_eliminar.setPreferredSize(tamañoBoton);
        
        pContenedorBotones.add(btn_add);
        pContenedorBotones.add(btn_eliminar);

        pRegistro.add(pForm, BorderLayout.NORTH);
        pRegistro.add(pContenedorBotones, BorderLayout.CENTER);

        // --- Panel Estadísticas ---
        modeloTabla = new DefaultTableModel(new String[]{"Nombre", "Teléfono", "Email", "Cat", "Fav"}, 0);
        tablaContactos = new JTable(modeloTabla);
        pestanas.addTab("Registro", pRegistro);
        pestanas.addTab("Estadísticas", new JScrollPane(tablaContactos));

        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setStringPainted(true);
        
        getContentPane().add(pestanas, BorderLayout.CENTER);
        getContentPane().add(barraProgreso, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                ventana vista = new ventana();
                new logica_ventana(vista);
                vista.setVisible(true);
            } catch (Exception e) { e.printStackTrace(); }
        });
    }
}
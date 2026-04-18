package controlador;

import modelo.persona;
import modelo.personaDAO;
import vista.ventana;

import java.awt.event.*;
import javax.swing.JOptionPane;
import java.util.List;

public class logica_ventana implements ActionListener, ItemListener {
    private ventana delegado;
    private String categoria = "";
    private boolean favorito = false;

    public logica_ventana(ventana v) {
        this.delegado = v;
        this.delegado.btn_add.addActionListener(this);
        this.delegado.btn_eliminar.addActionListener(this);
        this.delegado.cmb_categoria.addItemListener(this);
        this.delegado.chb_favorito.addItemListener(this);
        actualizarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Lógica AGREGAR
        if (e.getSource() == delegado.btn_add) {
            String nom = delegado.txt_nombres.getText();
            String tel = delegado.txt_telefono.getText();
            String em = delegado.txt_email.getText();

            if (!nom.isEmpty() && !tel.isEmpty() && !categoria.equals("Elija una Categoria")) {
                persona p = new persona(nom, tel, em, categoria, favorito);
                delegado.barraProgreso.setValue(50);
                if (new personaDAO(p).escribirArchivo()) {
                    delegado.barraProgreso.setValue(100);
                    actualizarTabla();
                    JOptionPane.showMessageDialog(delegado, "Contacto Registrado!!!");
                    vaciarCasillas();
                }
            } else {
                JOptionPane.showMessageDialog(delegado, "Llene todos los campos!!!");
            }
        }

        // Lógica ELIMINAR
        if (e.getSource() == delegado.btn_eliminar) {
            int fila = delegado.tablaContactos.getSelectedRow();
            if (fila != -1) {
                String nombre = delegado.tablaContactos.getValueAt(fila, 0).toString();
                int confirmar = JOptionPane.showConfirmDialog(delegado, "¿Eliminar a " + nombre + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirmar == JOptionPane.YES_OPTION) {
                    if (new personaDAO(new persona()).eliminarContacto(nombre)) {
                        JOptionPane.showMessageDialog(delegado, "Contacto eliminado!");
                        actualizarTabla();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(delegado, "Seleccione un contacto de la tabla.");
            }
        }
    }

    private void vaciarCasillas() {
        delegado.txt_nombres.setText("");
        delegado.txt_telefono.setText("");
        delegado.txt_email.setText("");
        delegado.cmb_categoria.setSelectedIndex(0);
        delegado.chb_favorito.setSelected(false);
        this.categoria = "";
        this.favorito = false;
        delegado.txt_nombres.requestFocus();
    }

    private void actualizarTabla() {
        try {
            List<persona> lista = new personaDAO(new persona()).leerArchivo();
            delegado.modeloTabla.setRowCount(0);
            for (persona p : lista) {
                delegado.modeloTabla.addRow(new Object[]{p.getNombre(), p.getTelefono(), p.getEmail(), p.getCategoria(), p.isFavorito()});
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    @Override public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == delegado.cmb_categoria) categoria = delegado.cmb_categoria.getSelectedItem().toString();
        if (e.getSource() == delegado.chb_favorito) favorito = delegado.chb_favorito.isSelected();
    }
}
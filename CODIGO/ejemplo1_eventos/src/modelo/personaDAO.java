package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class personaDAO {
    private File archivo;
    private persona p;

    public personaDAO(persona p) {
        this.p = p;
        this.archivo = new File("c:/gestionContactos");
        if (!archivo.exists()) archivo.mkdir();
        this.archivo = new File(archivo.getAbsolutePath(), "datosContactos.csv");
    }

    public boolean escribirArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(p.datosContacto());
            bw.newLine();
            bw.flush(); 
            return true;
        } catch (IOException e) { return false; }
    }
    
    public boolean eliminarContacto(String nombreAEliminar) {
        try {
            List<persona> listaActual = leerArchivo();
            // Removemos si el nombre coincide (ignorando mayúsculas/minúsculas)
            boolean seElimino = listaActual.removeIf(p -> p.getNombre().equalsIgnoreCase(nombreAEliminar));
            
            if (seElimino) {
                // Borramos el archivo viejo y creamos uno nuevo con la lista actualizada
                archivo.delete();
                for (persona pExistente : listaActual) {
                    new personaDAO(pExistente).escribirArchivo();
                }
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    

    public List<persona> leerArchivo() throws IOException {
        List<persona> lista = new ArrayList<>();
        if (!archivo.exists()) return lista;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length == 5) {
                    lista.add(new persona(d[0], d[1], d[2], d[3], Boolean.parseBoolean(d[4])));
                }
            }
        }
        return lista;
    }
}
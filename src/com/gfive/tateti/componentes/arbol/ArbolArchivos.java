package com.gfive.tateti.componentes.arbol;

import java.awt.EventQueue;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import com.gfive.tateti.estructuras.HashSetObservable;

public class ArbolArchivos extends JTree {

    /**
     * ID de serie por defecto.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construye el �rbol de archivos sin datos.
     */
    public ArbolArchivos() {
        super(new DefaultMutableTreeNode());
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setRootVisible(false);
    }

    /**
     * Carga recursivamente en el �rbol la estructura del sistema de archivos, actualizando la
     * vista.
     * 
     * @param rutaInicial
     *            - la ruta donde se empieza a examinar el sistema de archivos.
     * @param observador
     *            - objeto al que se le notifican las inserciones de archivos.
     */
    public void cargarNodos(Path rutaInicial, HashSetObservable.Observador observador) {
        // Cambiar el setRootVisible es un workaround para un bug de swing.
        EventQueue.invokeLater(() -> setRootVisible(true)); 

        getRaiz().removeAllChildren();
        
        HashSetObservable<Path> archivosQueLlenar = new HashSetObservable<Path>(observador);
        marcarArchivosParaCargar(rutaInicial, archivosQueLlenar);
        llenarArbol(getRaiz(), rutaInicial, archivosQueLlenar);
        
        EventQueue.invokeLater(() -> updateUI()); 
        EventQueue.invokeLater(() -> { for (int i = 0; i < getRowCount(); i++) expandRow(i); }); 
        EventQueue.invokeLater(() -> setRootVisible(false)); 
    }
    
    /**
     * Recorre el �rbol de archivos a partir del archivo dado. Si el archivo es .java o es una
     * carpeta con archivos .java en alguna parte de su jerarqu�a, la agrega al conjunto pasado.
     * 
     * @param archivo - archivo del que se parte para marcar los archivos.
     * @param conjunto - mapa en el que se van guardando los archivos que se marcan para inserci�n
     *                   en el �rbol.
     * @return true si agreg� el archivo pasado al �rbol, false de lo contrario.
     */
    private boolean marcarArchivosParaCargar(Path archivo, HashSetObservable<Path> conjunto) {
        if (Files.isRegularFile(archivo) &&
            archivo.getFileName().toString().toLowerCase().endsWith(".java")) {
            // Si es un archivo normal y .java, lo agregamos al conjunto y ya terminamos.
            conjunto.put(archivo);
            return true;
        }
        if (!Files.isDirectory(archivo)) {
            // El archivo no es una carpeta, y no es .java. No nos interesa.
            return false;
        }

        // Recorremos todos los hijos, llamando recursivamente a esta funci�n. Si alguno
        // se agrega, agregamos a este archivo tambi�n.
        try (Stream<Path> stream = Files.list(archivo)) {
             boolean marcada = stream
                 .parallel()
                 .map((hijo) -> marcarArchivosParaCargar(hijo, conjunto))
                 // La reducci�n se asegura que, con que uno devuelva true, el resultado sea true.
                 // Evitamos usar anyMatch() para asegurarnos que se recorran todos los elementos
                 // del stream.
                 .reduce(false, (r1, r2) -> r1 == true || r2 == true);
            if (marcada)
                conjunto.put(archivo);
            return marcada;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Recorre el sistema de archivos, buscando archivos para agregar al �rbol, hijos de la ruta
     * pasada contenidos el archivosQueLlenar.
     * 
     * @param predecesor
     *            - nodo padre del sub�rbol que se crear�.
     * @param archivo
     *            - archivo del que se parte para llenar el �rbol.
     * @param archivosQueLlenar
     *            - mapa con los archivos que deben insertarse en el �rbol.
     */
    private void llenarArbol(DefaultMutableTreeNode predecesor,
                             Path archivo,
                             Map<Path, Path> archivosQueLlenar) {
        NodoArbol nodo = NodoArbol.construir(archivo);
        predecesor.add(nodo);

        if (!nodo.esCarpeta())
            return;

        try {
            Files
                .list(nodo.getRutaArchivo())
                .parallel()
                .filter(hijo -> archivosQueLlenar.containsKey(hijo))
                .forEach(hijo -> llenarArbol(nodo, hijo, archivosQueLlenar));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return el nodo ra�z del �rbol.
     */
    public DefaultMutableTreeNode getRaiz() {
        return (DefaultMutableTreeNode) getModel().getRoot();
    }
    
    @Override
    public NodoArbol getLastSelectedPathComponent() {
        return (NodoArbol) super.getLastSelectedPathComponent();
    }
}

package com.gfive.tateti.componentes.visor;

import java.awt.Container;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.gfive.tateti.componentes.arbol.ArbolArchivos;
import com.gfive.tateti.componentes.arbol.NodoArbol;
import com.gfive.tateti.log.Log;

/**
 * Extensi�n del editor de c�digo fuente RSyntaxTextArea.
 * @author nicolas
 *
 */
public class VisorCodigoFuente extends RSyntaxTextArea implements TreeSelectionListener {

    /**
     * ID de serializaci�n por defecto.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Panel de desplazamiento que contiene al textarea.
     */
    private final RTextScrollPane scrollPane;
    
    /**
     * El �rbol de archivos al que el visor est� relacionado.
     */
    private final ArbolArchivos arbol;

    // TODO mover a otro lado.
    private ModeloTablaMetricas modelo;

    /**
     * Construye un visor de c�digo fuente asociado a un �rbol de archivos dado.
     * @param arbol - el �rbol al que el visor est� relacionado. No puede ser null.
     */
    public VisorCodigoFuente(ArbolArchivos arbol, ModeloTablaMetricas modelo) {
        this.modelo = modelo;
        Objects.requireNonNull(arbol);
        this.arbol = arbol;

        scrollPane = new RTextScrollPane(this);

        setEditable(false);
        setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

        arbol.addTreeSelectionListener(this);
    }
    
    /**
     * Agrega el visor al contenedor dado.
     * @param container - objeto que contendr� al visor.
     * @param constraints - par�metros de posicionamiento pasados al LayoutManager.
     */
    public void agregarAContenedor(Container container, String constraints) {
        container.add(scrollPane, constraints);
    }

    @Override
    public void valueChanged(TreeSelectionEvent evento) {
        // La selecci�n del �rbol cambi�. Buscamos cu�l es el nuevo elemento seleccionado.
        NodoArbol nodo = arbol.getLastSelectedPathComponent();

        // Si la nueva selecci�n est� vac�a, no hay nada que hacer.
        if (nodo == null)
            return;
        
        // TODO mover el modelo a otro lado.
        modelo.setMetricas(nodo.calcularMetricas());
        
        // Si seleccion� una carpeta, tampoco hay nada que hacer.
        if (nodo.esCarpeta())
            return;

        try {
            setText("");
            Files
                .lines(nodo.getRutaArchivo())
                .forEach((linea) -> append(linea + "\n"));
        } catch (IOException e) {
            Log log = new Log();
            log.reportarError(e);
            setText("No se pudo abrir el archivo: " + e.getMessage());
        }
        
        // Scrolleamos hacia la parte superior del archivo.
        setCaretPosition(0);
    }
}

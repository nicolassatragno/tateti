package com.gfive.tateti.interfaz;

import java.awt.Container;
import java.awt.EventQueue;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;

import com.gfive.tateti.componentes.arbol.ArbolArchivos;
import com.gfive.tateti.componentes.dialogoabrir.AbreArchivo;
import com.gfive.tateti.componentes.visor.ModeloTablaMetricas;
import com.gfive.tateti.componentes.visor.VisorCodigoFuente;

/**
 * Ventana principal de la aplicaci�n, conteniendo el panel de c�digo y las m�tricas.
 * 
 * @author nicolas
 *
 */
public class VentanaPrincipal extends JFrame implements AbreArchivo {

    /**
     * ID de serie por defecto.
     */
    private static final long serialVersionUID = 1L;

    /**
     * El nombre de la aplicaci�n mostrado al usuario.
     */
    private static final String NOMBRE_APLICACION = "tateti";

    /**
     * �rbol que muestra todos los archivos y subcarpetas de la carpeta seleccionada para trabajar
     * en la aplicaci�n.
     */
    private ArbolArchivos arbol;
    
    /**
     * Arranca la aplicaci�n.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new VentanaPrincipal());
    }

    /**
     * Inicializa todos los widgets de la ventana principal.
     */
    public VentanaPrincipal() {
        setTitle(NOMBRE_APLICACION);
        setBounds(0, 0, 1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(new Menu(this));
        setLayout(new MigLayout("", "[30%][70%]", "[80%][20%]"));

        Container pane = getContentPane();

        arbol = new ArbolArchivos();
        pane.add(new JScrollPane(arbol), "grow");
        
        ModeloTablaMetricas modelo = new ModeloTablaMetricas();

        new VisorCodigoFuente(arbol, modelo).agregarAContenedor(pane, "grow, wrap");
        
        pane.add(new JTable(modelo), "span 2, grow, wrap");

        setVisible(true);
    }

    @Override
    public void abrirArchivo(Path archivo) {
        EventQueue.invokeLater(() -> arbol.cargarNodos(archivo));
    }
}

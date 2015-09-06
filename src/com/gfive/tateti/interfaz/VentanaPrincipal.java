package com.gfive.tateti.interfaz;

import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTree;

import net.miginfocom.swing.MigLayout;

/**
 * Ventana principal de la aplicaci�n, conteniendo el panel de c�digo y las m�tricas.
 * @author nicolas
 *
 */
public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * El nombre de la aplicaci�n mostrado al usuario.
	 */
	private static final String NOMBRE_APLICACION = "tateti";

	/**
	 * Arranca la aplicaci�n.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				VentanaPrincipal ventana = new VentanaPrincipal();
				ventana.setVisible(true);
			}
		});
	}

	/**
	 * Inicializa todos los widgets de la ventana principal.
	 */
	public VentanaPrincipal() {
		setTitle(NOMBRE_APLICACION);
		setBounds(0, 0, 1024, 768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(new Menu());
		setLayout(new MigLayout());
		
		Container pane = getContentPane();
		JTree arbol = new JTree();
		pane.add(arbol);
	}
}

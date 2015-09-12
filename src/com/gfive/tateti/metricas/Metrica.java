package com.gfive.tateti.metricas;

import java.util.List;

/**
 * M�trica que se aplica a un archivo de c�digo fuente.
 * Antes de intentar mostrar una m�trica, debe procesarse.
 * Las m�tricas pueden ser procesadas una sola vez en su vida.
 * @author nicolas
 *
 */
public interface Metrica {

    /**
     * Realiza el procesamiento del archivo, llenando los valores de la m�trica.
     * @param lineasArchivo - un listado con todas las l�neas del archivo.
     * @throws RuntimeException si la m�trica intenta procesarse dos veces.
     */
    public void procesar(List<String> lineasArchivo) throws RuntimeException;
    
    /**
     * Devuelve una nueva m�trica que es el resultado de agregar esta m�trica a la m�trica pasada
     * por par�metro.
     * 
     * @param metrica - la m�trica con la que esta debe agregarse.
     */
    public Metrica agregar(Metrica metrica);
    
    /**
     * @return el nombre amigable para el usuario de la m�trica.
     */
    public String getNombre();
    
    /**
     * @return el valor de la m�trica, formateado para que sea amigable para el usuario.
     * @throws RuntimeException si se intenta obtener el valor de una m�trica cuyo procesamiento no
     * ha concluido.
     */
    public String getValor() throws RuntimeException;
}

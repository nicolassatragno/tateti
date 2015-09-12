package com.gfive.tateti.metricas;

import java.util.List;

/**
 * Cuenta la cantidad de l�neas en un archivo de c�digo fuente.
 * @author nicolas
 *
 */
public class CantidadLineas implements Metrica {
    
    /**
     * Nombre de la m�trica.
     */
    private static final String NOMBRE_CANTIDAD_LINEAS = "Cantidad de l�neas";
    
    /**
     * La cantidad de l�neas que tiene el archivo.
     */
    private Integer cantidadLineas;
    
    /**
     * Constructor por defecto.
     */
    public CantidadLineas() { }

    /**
     * Construye una m�trica de cantidad de l�neas a partir de una cantidad dada.
     * @param cantidadLineas
     */
    private CantidadLineas(int cantidadLineas) {
        this.cantidadLineas = cantidadLineas;
    }

    @Override
    public void procesar(List<String> lineasArchivo) {
        if (cantidadLineas != null)
            throw new RuntimeException("La cantidad de l�neas se calcul� antes");
        cantidadLineas = lineasArchivo.size();
    }

    @Override
    public String getNombre() {
        return NOMBRE_CANTIDAD_LINEAS;
    }

    @Override
    public String getValor() {
        if (cantidadLineas == null)
            throw new RuntimeException("La cantidad de l�neas todav�a no se calcul�");

        return Integer.toString(cantidadLineas);
    }

    @Override
    public Metrica agregar(Metrica metrica) {
        CantidadLineas otra = (CantidadLineas)metrica;
        return new CantidadLineas(cantidadLineas + otra.cantidadLineas);
    }

}

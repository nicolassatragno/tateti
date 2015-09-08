package com.gfive.tateti.exception;

import com.gfive.tateti.log.Log;

/**
 * Une excepci�n con un mensaje amigable para mostrarle al usuario.
 * 
 * @author nicolas
 *
 */
public class TatetiException extends Exception {

    /**
     * ID de serie por defecto.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construye una excepci�n a partir de un error particular de tateti.
     * 
     * @param mensaje
     */
    public TatetiException(String mensaje) {
        super(mensaje);
        new Log().debug(mensaje);
    }

    /**
     * Construye una excepci�n a partir de otra excepci�n de Java.
     * 
     * @param mensaje
     * @param causa
     */
    public TatetiException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        Log log = new Log();
        log.error(mensaje);
        log.reportarError(causa);
    }
}

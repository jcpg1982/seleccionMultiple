package pe.com.dms.movilasist.annotacion;


import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({StatusServer.PENDIENTE,
        StatusServer.ENVIADO})
@Retention(RetentionPolicy.SOURCE)
public @interface StatusServer {
    String PENDIENTE = "P"; // Parcialmente enviado al servidor (Las tablas relacionadas estan pendientes de envio)
    String ENVIADO = "E"; // Se envio correctamente al servidor y se reserva en la BD por x días
}
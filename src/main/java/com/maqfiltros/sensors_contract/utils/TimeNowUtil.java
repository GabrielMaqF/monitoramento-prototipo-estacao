package com.maqfiltros.sensors_contract.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class TimeNowUtil {

    private static final ZoneId BRAZIL_ZONE = ZoneId.of("America/Sao_Paulo");

    public static ZonedDateTime getCurrentZonedDateTime() {
        return ZonedDateTime.now(BRAZIL_ZONE);
    }

    private TimeNowUtil() {
        throw new UnsupportedOperationException("Classe utilitária não pode ser instanciada");
    }
}

package com.cloroapp.monitoreo.config

import com.cloroapp.monitoreo.model.Medicion
import com.cloroapp.monitoreo.model.PuntoMedicion
import com.cloroapp.monitoreo.repository.MedicionRepository
import com.cloroapp.monitoreo.repository.PuntoMedicionRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataInitializer {

    private val logger = LoggerFactory.getLogger(DataInitializer::class.java)

    @Bean
    fun seedMonitoreoData(
        puntoRepository: PuntoMedicionRepository,
        medicionRepository: MedicionRepository
    ): CommandLineRunner = CommandLineRunner {
        if (puntoRepository.count() > 0) {
            logger.info("Base de datos de monitoreo ya contiene datos. Omitiendo seed inicial.")
            return@CommandLineRunner
        }

        logger.info("Inicializando datos base en ms-monitoreo...")

        val puntos = listOf(
            // Granja Melipilla
            PuntoMedicion("MEL-01", "Granja Melipilla", "Melipilla, Región Metropolitana", "Galpón 1", "Salida de tanque de agua", "TANQUE"),
            PuntoMedicion("MEL-02", "Granja Melipilla", "Melipilla, Región Metropolitana", "Galpón 2", "Línea de distribución principal", "LINEA_DISTRIBUCION"),
            PuntoMedicion("MEL-03", "Granja Melipilla", "Melipilla, Región Metropolitana", "Galpón 3", "Extremo de línea de bebederos", "BEBEDERO"),
            PuntoMedicion("MEL-04", "Granja Melipilla", "Melipilla, Región Metropolitana", "Galpón 4", "Extremo de línea de bebederos", "BEBEDERO"),

            // Granja Pomaire
            PuntoMedicion("POM-01", "Granja Pomaire", "Pomaire, Región Metropolitana", "Galpón 1", "Salida de tanque de agua", "TANQUE"),
            PuntoMedicion("POM-02", "Granja Pomaire", "Pomaire, Región Metropolitana", "Galpón 2", "Línea de distribución principal", "LINEA_DISTRIBUCION"),
            PuntoMedicion("POM-03", "Granja Pomaire", "Pomaire, Región Metropolitana", "Galpón 3", "Extremo de línea de bebederos", "BEBEDERO"),
            PuntoMedicion("POM-04", "Granja Pomaire", "Pomaire, Región Metropolitana", "Galpón 4", "Extremo de línea de bebederos", "BEBEDERO"),

            // Granja San Pedro
            PuntoMedicion("SPE-01", "Granja San Pedro", "San Pedro, Región Metropolitana", "Galpón 1", "Salida de tanque de agua", "TANQUE"),
            PuntoMedicion("SPE-02", "Granja San Pedro", "San Pedro, Región Metropolitana", "Galpón 2", "Línea de distribución principal", "LINEA_DISTRIBUCION"),
            PuntoMedicion("SPE-03", "Granja San Pedro", "San Pedro, Región Metropolitana", "Galpón 3", "Extremo de línea de bebederos", "BEBEDERO"),
            PuntoMedicion("SPE-04", "Granja San Pedro", "San Pedro, Región Metropolitana", "Galpón 4", "Extremo de línea de bebederos", "BEBEDERO")
        )

        puntoRepository.saveAll(puntos)
        logger.info("Se registraron {} puntos de medición en 3 granjas.", puntos.size)

        val ahora = System.currentTimeMillis()
        val medicionesIniciales = listOf(
            Medicion(codigoPunto = "MEL-01", valorPpm = 4.20, fechaHoraMillis = ahora - 600000, observacion = "Nivel óptimo en tanque", registradoPor = "Sensor IoT"),
            Medicion(codigoPunto = "MEL-02", valorPpm = 3.80, fechaHoraMillis = ahora - 500000, observacion = "Lectura estable", registradoPor = "Sensor IoT"),
            Medicion(codigoPunto = "MEL-03", valorPpm = 3.50, fechaHoraMillis = ahora - 400000, observacion = "Línea con flujo normal", registradoPor = "Operario 1"),
            Medicion(codigoPunto = "MEL-04", valorPpm = 1.80, fechaHoraMillis = ahora - 300000, observacion = "Alerta: cloro bajo en bebedero", registradoPor = "Sensor IoT"),

            Medicion(codigoPunto = "POM-01", valorPpm = 4.50, fechaHoraMillis = ahora - 600000, observacion = "Dosificación adecuada", registradoPor = "Sensor IoT"),
            Medicion(codigoPunto = "POM-02", valorPpm = 4.10, fechaHoraMillis = ahora - 500000, observacion = "Presión y cloro en rango", registradoPor = "Sensor IoT"),
            Medicion(codigoPunto = "POM-03", valorPpm = 3.90, fechaHoraMillis = ahora - 400000, observacion = "Lectura conforme", registradoPor = "Operario 2"),
            Medicion(codigoPunto = "POM-04", valorPpm = 3.70, fechaHoraMillis = ahora - 300000, observacion = "Bebederos limpios", registradoPor = "Sensor IoT"),

            Medicion(codigoPunto = "SPE-01", valorPpm = 4.00, fechaHoraMillis = ahora - 600000, observacion = "Tanque central abastecido", registradoPor = "Sensor IoT"),
            Medicion(codigoPunto = "SPE-02", valorPpm = 3.60, fechaHoraMillis = ahora - 500000, observacion = "Línea regular", registradoPor = "Sensor IoT"),
            Medicion(codigoPunto = "SPE-03", valorPpm = 5.20, fechaHoraMillis = ahora - 400000, observacion = "Advertencia leve: cloro elevado", registradoPor = "Sensor IoT"),
            Medicion(codigoPunto = "SPE-04", valorPpm = 3.85, fechaHoraMillis = ahora - 300000, observacion = "Normalizado", registradoPor = "Operario 3")
        )

        medicionRepository.saveAll(medicionesIniciales)
        logger.info("Se cargaron {} mediciones iniciales en el historial.", medicionesIniciales.size)
    }
}

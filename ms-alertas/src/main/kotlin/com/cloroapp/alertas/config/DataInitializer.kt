package com.cloroapp.alertas.config

import com.cloroapp.alertas.model.AccionCorrectiva
import com.cloroapp.alertas.model.Alerta
import com.cloroapp.alertas.repository.AccionCorrectivaRepository
import com.cloroapp.alertas.repository.AlertaRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataInitializer {

    private val logger = LoggerFactory.getLogger(DataInitializer::class.java)

    @Bean
    fun seedAlertasData(
        alertaRepository: AlertaRepository,
        accionRepository: AccionCorrectivaRepository
    ): CommandLineRunner = CommandLineRunner {
        if (alertaRepository.count() > 0) {
            logger.info("Base de datos de alertas ya contiene datos. Omitiendo seed inicial.")
            return@CommandLineRunner
        }

        logger.info("Inicializando datos base en ms-alertas...")

        val ahora = System.currentTimeMillis()

        // Alerta 1: Crítica y abierta en MEL-04 (cloro bajo)
        val alerta1 = alertaRepository.save(
            Alerta(
                codigoPunto = "MEL-04",
                valorPpm = 1.80,
                tipo = "INSUFICIENTE",
                severidad = "CRITICO",
                estado = "ABIERTA",
                fechaHoraMillis = ahora - 1800000
            )
        )

        // Acción correctiva asociada a la alerta 1
        alerta1.id?.let { alertaId ->
            accionRepository.save(
                AccionCorrectiva(
                    alertaId = alertaId,
                    codigoPunto = "MEL-04",
                    descripcion = "Ajuste de válvula dosificadora de hipoclorito de sodio en línea de bebederos",
                    responsable = "Operario 1",
                    fechaHoraMillis = ahora - 900000,
                    estadoResolucion = "APLICADA"
                )
            )
        }

        // Alerta 2: Advertencia y cerrada en SPE-03 (cloro alto)
        alertaRepository.save(
            Alerta(
                codigoPunto = "SPE-03",
                valorPpm = 5.20,
                tipo = "EXCESIVO",
                severidad = "ADVERTENCIA",
                estado = "CERRADA",
                fechaHoraMillis = ahora - 7200000,
                cerradaPor = "Supervisor San Pedro",
                fechaCierreMillis = ahora - 3600000,
                observacionCierre = "Purga de línea realizada y valor normalizado a 3.85 ppm verificado con fotómetro"
            )
        )

        logger.info("Datos iniciales cargados en ms-alertas con éxito.")
    }
}

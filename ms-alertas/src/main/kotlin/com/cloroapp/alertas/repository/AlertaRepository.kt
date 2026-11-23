package com.cloroapp.alertas.repository

import com.cloroapp.alertas.model.Alerta
import org.springframework.data.jpa.repository.JpaRepository

interface AlertaRepository : JpaRepository<Alerta, Long> {
    fun findAllByOrderByFechaHoraMillisDesc(): List<Alerta>
    fun findByEstadoIgnoreCaseOrderByFechaHoraMillisDesc(estado: String): List<Alerta>
    fun findByCodigoPuntoOrderByFechaHoraMillisDesc(codigoPunto: String): List<Alerta>
}

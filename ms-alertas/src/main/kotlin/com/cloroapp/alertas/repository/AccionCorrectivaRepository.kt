package com.cloroapp.alertas.repository

import com.cloroapp.alertas.model.AccionCorrectiva
import org.springframework.data.jpa.repository.JpaRepository

interface AccionCorrectivaRepository : JpaRepository<AccionCorrectiva, Long> {
    fun findAllByOrderByFechaHoraMillisDesc(): List<AccionCorrectiva>
    fun findByAlertaIdOrderByFechaHoraMillisDesc(alertaId: Long): List<AccionCorrectiva>
}

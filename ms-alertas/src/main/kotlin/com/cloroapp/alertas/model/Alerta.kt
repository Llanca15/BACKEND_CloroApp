package com.cloroapp.alertas.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "alertas",
    indexes = [
        Index(name = "idx_alertas_codigo_estado", columnList = "codigo_punto, estado"),
        Index(name = "idx_alertas_fecha", columnList = "fecha_hora_millis")
    ]
)
class Alerta(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "codigo_punto", nullable = false, length = 32)
    var codigoPunto: String = "",

    @Column(name = "valor_ppm", nullable = false)
    var valorPpm: Double = 0.0,

    @Column(name = "tipo", nullable = false, length = 30)
    var tipo: String = "INSUFICIENTE", // INSUFICIENTE o EXCESIVO

    @Column(name = "severidad", nullable = false, length = 30)
    var severidad: String = "CRITICO", // ADVERTENCIA o CRITICO

    @Column(name = "estado", nullable = false, length = 20)
    var estado: String = "ABIERTA", // ABIERTA o CERRADA

    @Column(name = "fecha_hora_millis", nullable = false)
    var fechaHoraMillis: Long = System.currentTimeMillis(),

    @Column(name = "cerrada_por", nullable = true, length = 100)
    var cerradaPor: String? = null,

    @Column(name = "fecha_cierre_millis", nullable = true)
    var fechaCierreMillis: Long? = null,

    @Column(name = "observacion_cierre", nullable = true, length = 500)
    var observacionCierre: String? = null
)

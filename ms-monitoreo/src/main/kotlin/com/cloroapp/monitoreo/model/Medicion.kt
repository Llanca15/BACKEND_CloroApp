package com.cloroapp.monitoreo.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "mediciones",
    indexes = [
        Index(name = "idx_mediciones_codigo_fecha", columnList = "codigo_punto, fecha_hora_millis")
    ]
)
class Medicion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "codigo_punto", nullable = false, length = 32)
    var codigoPunto: String = "",

    @Column(name = "valor_ppm", nullable = false)
    var valorPpm: Double = 0.0,

    @Column(name = "fecha_hora_millis", nullable = false)
    var fechaHoraMillis: Long = System.currentTimeMillis(),

    @Column(name = "observacion", nullable = false, length = 500)
    var observacion: String = "",

    @Column(name = "registrado_por", nullable = false, length = 100)
    var registradoPor: String = "Sistema"
)

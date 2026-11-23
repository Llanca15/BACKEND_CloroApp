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
    name = "acciones_correctivas",
    indexes = [
        Index(name = "idx_acciones_alerta", columnList = "alerta_id")
    ]
)
class AccionCorrectiva(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "alerta_id", nullable = false)
    var alertaId: Long = 0L,

    @Column(name = "codigo_punto", nullable = false, length = 32)
    var codigoPunto: String = "",

    @Column(name = "descripcion", nullable = false, length = 500)
    var descripcion: String = "",

    @Column(name = "responsable", nullable = false, length = 100)
    var responsable: String = "",

    @Column(name = "fecha_hora_millis", nullable = false)
    var fechaHoraMillis: Long = System.currentTimeMillis(),

    @Column(name = "estado_resolucion", nullable = false, length = 30)
    var estadoResolucion: String = "REGISTRADA", // REGISTRADA, APLICADA, VERIFICADA

    @Column(name = "foto_ruta", nullable = true, length = 300)
    var fotoRuta: String? = null
)

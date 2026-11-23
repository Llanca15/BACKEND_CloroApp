package com.cloroapp.monitoreo.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "puntos_medicion")
class PuntoMedicion(
    @Id
    @Column(name = "codigo", nullable = false, length = 32)
    var codigo: String = "",

    @Column(name = "granja", nullable = false, length = 120)
    var granja: String = "",

    @Column(name = "ubicacion_granja", nullable = false, length = 160)
    var ubicacionGranja: String = "",

    @Column(name = "galpon", nullable = false, length = 80)
    var galpon: String = "",

    @Column(name = "ubicacion", nullable = false, length = 160)
    var ubicacion: String = "",

    @Column(name = "tipo", nullable = false, length = 60)
    var tipo: String = ""
)

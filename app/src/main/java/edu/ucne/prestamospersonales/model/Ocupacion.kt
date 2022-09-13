package edu.ucne.prestamospersonales.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Ocupaciones")
data class Ocupacion(
    @PrimaryKey(autoGenerate = true)
    val OcupacionId: Int=0,
    val Descripcion: String="",
    val Salario: Double=0.0
)
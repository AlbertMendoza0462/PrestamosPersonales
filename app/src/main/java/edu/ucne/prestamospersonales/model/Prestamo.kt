package edu.ucne.prestamospersonales.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Prestamos")
data class Prestamo(
    @PrimaryKey(autoGenerate = true)
    val PrestamoId: Int=0,
    val Fecha: String="",
    val Vence: String="",
    val PersonaId: Int=0,
    val Concepto: String="",
    val Monto: Double=0.0,
    val Balance: Double=0.0
)
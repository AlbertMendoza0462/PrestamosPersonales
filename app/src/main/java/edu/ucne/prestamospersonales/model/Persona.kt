package edu.ucne.prestamospersonales.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Personas")
data class Persona(
    @PrimaryKey(autoGenerate = true)
    val PersonaId: Int=0,
    val Nombres: String="",
    val Telefono: String="",
    val Celular: String="",
    val Email: String="",
    val Direccion: String="",
    val FechaNacimiento: String="",
    val OcupacionId: Int=0,
    val Balance: Double=0.0
)
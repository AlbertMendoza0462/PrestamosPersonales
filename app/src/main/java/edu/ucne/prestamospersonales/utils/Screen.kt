package edu.ucne.prestamospersonales.utils

sealed class Screen (val route : String){

    object OcupacionesScreen : Screen("RegistroOcupaciones")
    object PersonaScreen : Screen("RegistroPersonas")
    object PrestamoScreen : Screen("RegistroPrestamos")
    object ConsultaPrestamoScreen : Screen("ConsultaPrestamos")
}
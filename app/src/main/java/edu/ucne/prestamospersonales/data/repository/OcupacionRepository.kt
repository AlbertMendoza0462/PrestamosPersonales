package edu.ucne.prestamospersonales.data.repository

import edu.ucne.prestamospersonales.data.dao.OcupacionDao
import edu.ucne.prestamospersonales.model.Ocupacion
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OcupacionRepository @Inject constructor(
    val ocupacionDao:OcupacionDao
) {

    suspend fun insert(ocupacion :Ocupacion){
        ocupacionDao.insertOcupacion(ocupacion)
    }

    suspend fun delete(ocupacion : Ocupacion){
        ocupacionDao.deleteOcupacion(ocupacion)
    }

    fun find(ocupacionId: Int):Flow<Ocupacion> {
        return ocupacionDao.getOcupacion(ocupacionId)
    }

    fun getAll(): Flow<List<Ocupacion>>{
        return ocupacionDao.getAll()
    }
}

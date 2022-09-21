package edu.ucne.prestamospersonales.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.prestamospersonales.data.AppDataBase
import edu.ucne.prestamospersonales.data.dao.OcupacionDao
import edu.ucne.prestamospersonales.data.dao.PersonaDao
import edu.ucne.prestamospersonales.data.dao.PrestamoDao
import edu.ucne.prestamospersonales.data.repository.OcupacionRepository
import edu.ucne.prestamospersonales.data.repository.PersonaRepository
import edu.ucne.prestamospersonales.data.repository.PrestamoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun ProvideTicketDb(@ApplicationContext context: Context): AppDataBase {
        val DATABASE_NAME = "prestamos_personales.db"
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun ProvidePersonaDAO(appDataBase: AppDataBase): PersonaDao {
        return appDataBase.personaDao
    }

    @Provides
    fun ProvideOcupacionDAO(appDataBase: AppDataBase): OcupacionDao {
        return appDataBase.ocupacionDao
    }

    @Provides
    fun ProvidePrestamoDAO(appDataBase: AppDataBase): PrestamoDao {
        return appDataBase.prestamoDao
    }

    @Provides
    fun provideOcupacionRepository(ocupaciondao: OcupacionDao): OcupacionRepository {
        return OcupacionRepository(ocupaciondao)
    }

    @Provides
    fun providePersonaRepository(personaDao: PersonaDao): PersonaRepository {
        return PersonaRepository(personaDao)
    }

    @Provides
    fun providePrestamoRepository(prestamoDao: PrestamoDao): PrestamoRepository {
        return PrestamoRepository(prestamoDao)
    }
}
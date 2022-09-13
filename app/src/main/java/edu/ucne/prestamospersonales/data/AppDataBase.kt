package edu.ucne.prestamospersonales.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.ucne.prestamospersonales.data.dao.OcupacionDao
import edu.ucne.prestamospersonales.model.Ocupacion

@Database(
    entities = [Ocupacion::class],
    version = 2,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract val ocupacionDao: OcupacionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = context.let {
                        Room.databaseBuilder(
                            context.applicationContext,
                            AppDataBase::class.java,
                            "ocup_database.db"
                        ).fallbackToDestructiveMigration().build()
                    }
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
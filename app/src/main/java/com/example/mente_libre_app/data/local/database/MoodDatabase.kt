package com.example.mente_libre_app.data.local.mood

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Base de datos local para registrar los estados de ánimo del usuario.
 * Cada registro se guarda como una entidad MoodEntry.
 *
 * Esta base de datos es independiente de la de usuarios (AppDatabase),
 * y se guarda en un archivo separado llamado "mood_records.db".
 */
@Database(
    entities = [MoodEntry::class], // Entidad que define las columnas de la tabla
    version = 1,                   // Versión inicial de la base de datos
    exportSchema = false
)
abstract class MoodDatabase : RoomDatabase() {

    // DAO que contiene las operaciones de lectura/escritura
    abstract fun moodDao(): MoodDao

    companion object {
        @Volatile
        private var INSTANCE: MoodDatabase? = null

        /**
         * Devuelve una instancia única (singleton) de la base de datos.
         * Si ya existe, la reutiliza. Si no, la crea.
         */
        fun getInstance(context: Context): MoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoodDatabase::class.java,
                    "mood_records.db" // Nombre físico del archivo en almacenamiento interno
                )
                    // Destruye y vuelve a crear la DB solo si cambia el esquema (útil en desarrollo)
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}

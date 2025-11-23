package com.example.mente_libre_app.data.local.mood

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MoodEntry::class],
    version = 2,          // 👈 súbela a 2
    exportSchema = false
)
abstract class MoodDatabase : RoomDatabase() {

    abstract fun moodDao(): MoodDao

    companion object {
        @Volatile
        private var INSTANCE: MoodDatabase? = null

        fun getInstance(context: Context): MoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoodDatabase::class.java,
                    "mood_records.db"
                )
                    .fallbackToDestructiveMigration()   // 👈 por si cambias algo después
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}

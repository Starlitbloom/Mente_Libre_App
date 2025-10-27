package com.example.mente_libre_app.data.local.mood

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Acceso a datos de estados de ánimo.
 * Todas las funciones son suspend para ejecutarlas en corrutinas (evita main-thread).
 */
@Dao
interface MoodDao {

    @Query("""
        SELECT * FROM mood_entries
        WHERE date BETWEEN :start AND :end
        ORDER BY date ASC
    """)
    suspend fun entriesBetween(start: String, end: String): List<MoodEntry>

    @Query("SELECT * FROM mood_entries WHERE date = :date LIMIT 1")
    suspend fun entryOn(date: String): MoodEntry?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: MoodEntry)

    // (Opcional) útil para pruebas
    @Query("DELETE FROM mood_entries")
    suspend fun clear()
}

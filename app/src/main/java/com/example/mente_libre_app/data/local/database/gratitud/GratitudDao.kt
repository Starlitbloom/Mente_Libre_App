package com.example.mente_libre_app.data.local.database.gratitud


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GratitudDao {

    @Query("SELECT * FROM gratitud_entries ORDER BY date DESC")
    suspend fun getAll(): List<GratitudEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: GratitudEntry)
}

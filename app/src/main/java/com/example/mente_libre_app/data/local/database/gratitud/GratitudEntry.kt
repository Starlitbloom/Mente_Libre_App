
package com.example.mente_libre_app.data.local.database.gratitud


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gratitud_entries")
data class GratitudEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,        // "2025-11-21"
    val moodLabel: String?,  // felicidad / triste / ansioso / etc
    val title: String?,
    val text: String
)

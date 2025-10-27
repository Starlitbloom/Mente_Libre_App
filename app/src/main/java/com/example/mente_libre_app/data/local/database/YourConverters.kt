package com.example.mente_libre_app.data.local

import androidx.room.TypeConverter
import java.time.LocalDate

class YourConverters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.toString()

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? =
        dateString?.let { LocalDate.parse(it) }
}

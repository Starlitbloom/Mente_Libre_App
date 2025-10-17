package com.example.mente_libre_app.data.local.database

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mente_libre_app.data.local.user.UserDao
import com.example.mente_libre_app.data.local.user.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private const val DB_NAME = "mente_libre.db"

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()

                val instance = builder.addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Usamos INSTANCE que ya apunta a la base de datos
                        val dbInstance = INSTANCE ?: return
                        CoroutineScope(Dispatchers.IO).launch {
                            val dao = dbInstance.userDao()
                            val seed = listOf(
                                UserEntity(name = "Demo", email = "demo@duoc.cl", phone = "12345678", password = "Demo123!"),
                                UserEntity(name = "Mente Libre", email = "admin@mente.com", phone = "87654321", password = "Admin123!")
                            )
                            if (dao.count() == 0) {
                                seed.forEach { dao.insert(it) }
                            }
                        }
                    }
                }).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
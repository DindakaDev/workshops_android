package com.dindaka.workshops_android.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dindaka.workshops_android.data.local.db.dao.StorageDao
import com.dindaka.workshops_android.data.local.db.entities.StorageEntity

const val DATABASE_NAME = "workshops.db"

@Database(entities = [StorageEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storageDao(): StorageDao
}
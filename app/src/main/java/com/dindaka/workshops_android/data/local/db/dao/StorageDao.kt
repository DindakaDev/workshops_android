package com.dindaka.workshops_android.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dindaka.workshops_android.data.local.db.entities.StorageEntity

@Dao
interface StorageDao {
    @Query("SELECT * FROM storage ORDER BY name desc")
    suspend fun getItems(): List<StorageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<StorageEntity>)

    @Query("DELETE FROM storage")
    suspend fun clearStorageTable()
}
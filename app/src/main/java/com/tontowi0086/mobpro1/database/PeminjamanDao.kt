package com.tontowi0086.mobpro1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tontowi0086.mobpro1.model.Peminjaman
import kotlinx.coroutines.flow.Flow

@Dao
interface PeminjamanDao {

    @Insert
    suspend fun insert(peminjaman: Peminjaman)

    @Update
    suspend fun update(peminjaman: Peminjaman)

    @Query("SELECT * FROM Peminjaman ORDER BY jumlahHari DESC")
    fun getPeminjaman(): Flow<List<Peminjaman>>

    @Query("SELECT * FROM peminjaman WHERE id = :id")
    suspend fun getPeminjamanById(id: Long): Peminjaman

    @Query("DELETE FROM peminjaman WHERE id = :id")
    suspend fun deleteById(id: Long)
}
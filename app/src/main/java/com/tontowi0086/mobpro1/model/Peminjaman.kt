package com.tontowi0086.mobpro1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "peminjaman")
data class Peminjaman(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val namaPeminjam: String,
    val namaBarang: String,
    val jumlahHari: Int
)

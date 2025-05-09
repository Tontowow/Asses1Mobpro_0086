package com.tontowi0086.mobpro1.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tontowi0086.mobpro1.database.PeminjamanDao
import com.tontowi0086.mobpro1.model.Peminjaman
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class DetailViewModel(private val dao: PeminjamanDao) : ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(namaPeminjam: String, namaBarang: String, jumlahHari: Int) {
        val peminjaman = Peminjaman(
            namaPeminjam = namaPeminjam,
            namaBarang = namaBarang,
            jumlahHari = jumlahHari
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(peminjaman)
        }
    }

    suspend fun getPeminjaman(id: Long): Peminjaman? {
        return dao.getPeminjamanById(id)
    }

    fun update(id: Long, namaPeminjam: String, namaBarang: String, jumlahHari: Int) {
        val peminjaman = Peminjaman(
            id = id,
            namaPeminjam = namaPeminjam,
            namaBarang = namaBarang,
            jumlahHari = jumlahHari
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(peminjaman)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}

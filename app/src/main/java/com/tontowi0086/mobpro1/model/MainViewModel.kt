package com.tontowi0086.mobpro1.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tontowi0086.mobpro1.database.PeminjamanDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: PeminjamanDao) : ViewModel() {
    val data: StateFlow<List<Peminjaman>> = dao.getPeminjaman().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}

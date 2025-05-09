package com.tontowi0086.mobpro1.navigation

import com.tontowi0086.mobpro1.ui.screen.KEY_ID_PEMINJAMAN

sealed class Screen(val route: String){
    data object  Home: Screen("mainScreen")
    data object  FormBaru: Screen("detailScreen")
    data object  FormUbah: Screen("detailScreen/{$KEY_ID_PEMINJAMAN}"){
        fun withId(id: Long) = "detailScreen/$id"
    }
}
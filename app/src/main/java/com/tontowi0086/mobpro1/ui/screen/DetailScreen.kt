package com.tontowi0086.mobpro1.ui.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tontowi0086.mobpro1.R
import com.tontowi0086.mobpro1.navigation.DetailViewModel
import com.tontowi0086.mobpro1.ui.ui.theme.Mobpro1Theme
import com.tontowi0086.mobpro1.util.SettingsDataStore
import com.tontowi0086.mobpro1.util.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

 // Biru Muda, Hijau Muda, Merah Muda

const val KEY_ID_PEMINJAMAN = "idPeminjaman"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)
    val dataStore = SettingsDataStore(context)
    val colorOptions = listOf(Color(0xFFB3E5FC), Color(0xFFC8E6C9), Color(0xFFFFCDD2))

    var namaPeminjam by remember { mutableStateOf("") }
    var namaBarang by remember { mutableStateOf("") }
    var jumlahHari by remember { mutableIntStateOf(1) }
    var showDialog by remember { mutableStateOf(false) }
    val selectedColorIndex by dataStore.colorIndexFlow.collectAsState(initial = 0)
    val backgroundColor = colorOptions.getOrElse(selectedColorIndex) { Color.White }

    LaunchedEffect(id) {
        if (id != null) {
            val data = viewModel.getPeminjaman(id)
            if (data != null) {
                namaPeminjam = data.namaPeminjam
                namaBarang = data.namaBarang
                jumlahHari = data.jumlahHari
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = if (id == null) "Tambah Peminjaman" else "Edit Peminjaman")
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (namaPeminjam.isBlank() || namaBarang.isBlank()) {
                            Toast.makeText(context, "Semua data harus diisi!", Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        if (id == null) {
                            viewModel.insert(namaPeminjam, namaBarang, jumlahHari)
                        } else {
                            viewModel.update(id, namaPeminjam, namaBarang, jumlahHari)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                    }

                    Box {
                        IconButton(onClick = { showDialog = true }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "Menu",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        DropdownMenu(
                            expanded = showDialog,
                            onDismissRequest = { showDialog = false }
                        ) {
                            // Specify the type for the second parameter
                            colorOptions.forEachIndexed { index, _ ->
                                DropdownMenuItem(
                                    text = { Text("Tema ${index + 1}") },
                                    onClick = {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            dataStore.saveColorIndex(index)
                                        }
                                        showDialog = false
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormPeminjaman(
            namaPeminjam = namaPeminjam,
            onNamaChange = { namaPeminjam = it },
            namaBarang = namaBarang,
            onBarangChange = { namaBarang = it },
            jumlahHari = jumlahHari,
            onHariChange = { jumlahHari = it },
            modifier = Modifier.padding(padding)
        )

        if (id != null && showDialog) {
            DisplayAlertDialog(
                onDismissRequest = { showDialog = false }
            ) {
                showDialog = false
                viewModel.delete(id)
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun FormPeminjaman(
    namaPeminjam: String, onNamaChange: (String) -> Unit,
    namaBarang: String, onBarangChange: (String) -> Unit,
    jumlahHari: Int, onHariChange: (Int) -> Unit,
    modifier: Modifier
) {
    val hariList = listOf(1, 3, 7, 30)
    val labelMap = mapOf(
        1 to "1 Hari",
        3 to "3 Hari",
        7 to "7 Hari",
        30 to "1 Bulan"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = namaPeminjam,
            onValueChange = onNamaChange,
            label = { Text("Nama Peminjam") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = namaBarang,
            onValueChange = onBarangChange,
            label = { Text("Nama Barang") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Text("Lama Peminjaman:", style = MaterialTheme.typography.bodyMedium)

        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                hariList.forEach { hari ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onHariChange(hari) }
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = (jumlahHari == hari),
                            onClick = { onHariChange(hari) }
                        )
                        Text(
                            text = labelMap[hari] ?: "$hari Hari",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.hapus)) },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailPeminjamanScreenPreview() {
    Mobpro1Theme {
        DetailScreen(rememberNavController())
    }
}

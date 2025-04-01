package com.tontowi0086.mobpro1.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class TaskViewModel : ViewModel() {
    var tasks by mutableStateOf(listOf<String>())
        private set

    fun addTask(task: String) {
        if (task.isNotEmpty()) {
            tasks = tasks + task
        }
    }

    fun editTask(index: Int, newTask: String) {
        if (newTask.isNotEmpty()) {
            tasks = tasks.toMutableList().also { it[index] = newTask }
        }
    }

    fun removeTask(index: Int) {
        tasks = tasks.toMutableList().also { it.removeAt(index) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, taskViewModel: TaskViewModel = viewModel()) {
    var taskInput by remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "To-Do List") },
                actions = {
                    IconButton(onClick = { navController.navigate("about") }) {
                        Icon(Icons.Outlined.Info, contentDescription = "Tentang")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                taskViewModel.addTask(taskInput.text.trim())
                taskInput = TextFieldValue()
            }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = taskInput,
                onValueChange = { taskInput = it },
                label = { Text("Tambah Tugas") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(taskViewModel.tasks) { task ->
                    TaskItem(taskViewModel.tasks.indexOf(task), task, taskViewModel)
                }
            }
        }
    }
}

@Composable
fun TaskItem(index: Int, task: String, taskViewModel: TaskViewModel) {
    var isEditing by remember { mutableStateOf(false) }
    var editedTask by remember { mutableStateOf(TextFieldValue(task)) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (isEditing) {
                OutlinedTextField(
                    value = editedTask,
                    onValueChange = { editedTask = it },
                    label = { Text("Edit Tugas") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = {
                        taskViewModel.editTask(index, editedTask.text.trim())
                        isEditing = false
                    }) {
                        Text("Simpan")
                    }
                    Button(onClick = { isEditing = false }) {
                        Text("Batal")
                    }
                }
            } else {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(task, style = MaterialTheme.typography.bodyLarge)
                    Row {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(Icons.Outlined.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { taskViewModel.removeTask(index) }) {
                            Icon(Icons.Outlined.Delete, contentDescription = "Hapus")
                        }
                    }
                }
            }
        }
    }
}

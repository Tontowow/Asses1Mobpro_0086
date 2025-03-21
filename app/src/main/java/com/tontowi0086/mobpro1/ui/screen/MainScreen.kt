package com.tontowi0086.mobpro1.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    var tasks by remember { mutableStateOf(listOf<String>()) }
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
                val newTask = taskInput.text.trim()
                if (newTask.isNotEmpty()) {
                    tasks = tasks + newTask
                    taskInput = TextFieldValue()
                }
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
                items(tasks) { task ->
                    TaskItem(task)
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Text(
            text = task,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

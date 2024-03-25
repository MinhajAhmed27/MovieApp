package com.example.movieapp.ui


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBar(filterText: String, onFilterTextChange: (String) -> Unit) {
    TextField(
        value = filterText,
        onValueChange = onFilterTextChange,
        label = { Text("Filter by Name") },
        modifier = Modifier.fillMaxWidth()
    )
}

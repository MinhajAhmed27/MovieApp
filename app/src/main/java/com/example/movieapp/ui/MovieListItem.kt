package com.example.movieapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.data.Movie

@Composable
fun MovieListItem(movie: Movie, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp),verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = "https://image.tmdb.org/t/p/w500/${movie.backdrop_path}")
                        .placeholder(R.drawable.sample)// Placeholder while loading
                        .build()
                ),
                contentDescription = "Movie Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Text(
                text = movie.title ?: "Movie Name Unavailable",
                style = TextStyle(fontSize = 16.sp)
            )
            Text(
                text = movie.overview ?: "Movie Description Unavailable",
                style = TextStyle(fontSize = 14.sp)
            )
        }
    }
}

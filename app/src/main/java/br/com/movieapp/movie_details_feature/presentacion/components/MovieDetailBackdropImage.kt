package br.com.movieapp.movie_details_feature.presentacion.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.movieapp.core.presentation.components.common.AsyncImageUrl

@Composable
fun MovieDetailBackdropImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {

    Box(modifier = modifier) {
        AsyncImageUrl(
            imageUrl = imageUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Preview
@Composable
private fun MovieDetailBackdropImagePreview() {
    MovieDetailBackdropImage(
        imageUrl = "",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}
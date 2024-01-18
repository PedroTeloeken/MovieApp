package br.com.movieapp.core.presentation.components.common

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import br.com.movieapp.R
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun AsyncImageUrl(
    modifier: Modifier = Modifier,
    imageUrl: String,
    crossFadeEnable: Boolean = true,
    contentScale: ContentScale = ContentScale.FillHeight,
    @DrawableRes errorImage: Int = R.drawable.ic_error_image,
    @DrawableRes placeholder: Int = R.drawable.ic_placeholder
) {

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(enable = crossFadeEnable)
            .error(errorImage)
            .placeholder(placeholder)
            .build(),
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier
    )
}
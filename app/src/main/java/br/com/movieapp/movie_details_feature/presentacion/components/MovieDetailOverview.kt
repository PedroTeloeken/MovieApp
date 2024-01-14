package br.com.movieapp.movie_details_feature.presentacion.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import br.com.movieapp.R
import br.com.movieapp.ui.theme.white

@Composable
fun MovieDetailOverview(
    modifier: Modifier = Modifier,
    overview: String
) {

    var expanded by remember { mutableStateOf(value = false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = stringResource(id = R.string.description),
            color = white,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )

        if (expanded) {
            Text(
                modifier = modifier.clickable { expanded = !expanded },
                text = stringResource(id = R.string.description),
                fontFamily = FontFamily.SansSerif,
                color = Color.DarkGray,
                fontSize = 15.sp
            )
        } else {
            Text(
                text = stringResource(id = R.string.description),
                modifier = modifier.clickable { expanded = !expanded },
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
                color = Color.DarkGray,
                fontSize = 15.sp,
                maxLines = 3
            )
        }
    }
}
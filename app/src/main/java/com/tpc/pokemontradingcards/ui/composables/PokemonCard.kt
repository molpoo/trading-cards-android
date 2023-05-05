package com.tpc.pokemontradingcards.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.mutualmobile.composesensors.SensorDelay
import com.mutualmobile.composesensors.rememberGravitySensorState
import com.tpc.pokemontradingcards.data.model.ModelCard
import com.tpc.pokemontradingcards.data.model.ModelCardEmpty
import com.tpc.pokemontradingcards.ui.commons.theme.PokemonTradingCardsTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    data: ModelCard,
    canBeRotated: Boolean = false,
    onClick: () -> Unit
) {
    val accelerometerState = rememberGravitySensorState(sensorDelay = SensorDelay.UI)

    Card(
        modifier = modifier
            .graphicsLayer {
                if (canBeRotated) {
                    rotationX = accelerometerState.yForce
                    rotationY = accelerometerState.xForce
                }
            },
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data.url)
                .crossfade(500)
                .diskCachePolicy(CachePolicy.ENABLED)
                .diskCacheKey(data.id)
                .build(),
            contentDescription = data.label,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0x28292a)
@Composable
fun PokemonCardPreview() {
    PokemonTradingCardsTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            PokemonCard(
                modifier = Modifier.align(Alignment.Center),
                data = ModelCardEmpty.copy(
                    url = "https://images.pokemontcg.io/swsh12pt5/160_hires.png"
                ),
                canBeRotated = true,
                onClick = {

                }
            )
        }
    }
}
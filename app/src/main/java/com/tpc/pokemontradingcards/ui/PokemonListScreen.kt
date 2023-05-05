package com.tpc.pokemontradingcards.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tpc.pokemontradingcards.R
import com.tpc.pokemontradingcards.data.model.ModelCardEmpty
import com.tpc.pokemontradingcards.ui.composables.PokemonCard

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PokemonListScreen(pokemonViewModel: PokemonListViewModel = hiltViewModel()) {

    val pokemonCards by pokemonViewModel.pokemonCardsData.collectAsStateWithLifecycle()
    var isCardVisible by remember { mutableStateOf(false) }
    var pokemonData by remember { mutableStateOf(ModelCardEmpty) }

    Box(Modifier.fillMaxSize()) {
        LazyVerticalGrid(modifier = Modifier.background(MaterialTheme.colorScheme.background),
            columns = GridCells.Adaptive(166.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = {
                val contentToShow =
                    pokemonCards.filter { it.idSet == pokemonViewModel.currentPokemonSet.id }

                if (contentToShow.isEmpty()) {
                    item {
                        Text("no content :'(")
                    }
                }

                items(
                    contentToShow,
                    key = { it.id }) { pokemonCard ->
                    PokemonCard(data = pokemonCard) {
                        if (!isCardVisible) {
                            isCardVisible = true
                            pokemonData = pokemonCard
                        }
                    }
                }
            })

        AnimatedVisibility(
            visible = isCardVisible, enter = fadeIn(), exit = fadeOut()
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xe628292a))
                    .clickable {
                        isCardVisible = false
                    }) {

            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = isCardVisible,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            PokemonCard(data = pokemonData, canBeRotated = true) {
                isCardVisible = false
            }
        }

        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(32.dp),
            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(8.dp),
            onClick = {
                pokemonViewModel.updatePokemonSet(PokemonSet.FOSSIL)
            }) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Edit, contentDescription = null
                )

                Text(stringResource(R.string.change_pokemon_set))
            }
        }
    }
}
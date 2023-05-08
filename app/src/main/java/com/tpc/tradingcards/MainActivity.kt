package com.tpc.tradingcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.tpc.tradingcards.ui.cardListRoute
import com.tpc.tradingcards.ui.cardsGraph
import com.tpc.tradingcards.ui.commons.theme.TradingCardsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalLayoutApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TradingCardsTheme {
                val navController = rememberAnimatedNavController()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                ) { innerPadding ->
                    AnimatedNavHost(
                        navController,
                        startDestination = cardListRoute,
                        modifier = Modifier.consumeWindowInsets(innerPadding)
                    ) {
                        cardsGraph(navController)
                    }
                }
            }
        }
    }
}
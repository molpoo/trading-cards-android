package com.tpc.tradingcards.data.repository

import com.tpc.tradingcards.data.model.Card
import com.tpc.tradingcards.data.model.CardSet
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    /**
     * Load cards from the local database
     */
    fun loadCards(): Flow<List<Card>>

    /**
     * Load card sets from the local database
     */
    fun loadSets(): Flow<List<CardSet>>

}
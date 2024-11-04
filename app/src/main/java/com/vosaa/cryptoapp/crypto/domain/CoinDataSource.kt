package com.vosaa.cryptoapp.crypto.domain

import com.vosaa.cryptoapp.core.domain.util.NetworkError
import com.vosaa.cryptoapp.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}
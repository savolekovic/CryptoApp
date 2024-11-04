package com.vosaa.cryptoapp.crypto.data.networking

import com.vosaa.cryptoapp.core.data.networking.constructUrl
import com.vosaa.cryptoapp.core.data.networking.safeCall
import com.vosaa.cryptoapp.core.domain.util.NetworkError
import com.vosaa.cryptoapp.core.domain.util.Result
import com.vosaa.cryptoapp.core.domain.util.map
import com.vosaa.cryptoapp.crypto.data.mappers.toCoin
import com.vosaa.cryptoapp.crypto.data.networking.dto.CoinsResponseDto
import com.vosaa.cryptoapp.crypto.domain.Coin
import com.vosaa.cryptoapp.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {

    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { coinsResponseDto ->
            coinsResponseDto.data.map {
                it.toCoin()
            }
        }
    }
}
package com.vosaa.cryptoapp.crypto.data.networking

import com.vosaa.cryptoapp.core.data.networking.constructUrl
import com.vosaa.cryptoapp.core.data.networking.safeCall
import com.vosaa.cryptoapp.core.domain.util.NetworkError
import com.vosaa.cryptoapp.core.domain.util.Result
import com.vosaa.cryptoapp.core.domain.util.map
import com.vosaa.cryptoapp.crypto.data.mappers.toCoin
import com.vosaa.cryptoapp.crypto.data.mappers.toCoinPrice
import com.vosaa.cryptoapp.crypto.data.networking.dto.CoinsHistoryDto
import com.vosaa.cryptoapp.crypto.data.networking.dto.CoinsResponseDto
import com.vosaa.cryptoapp.crypto.domain.Coin
import com.vosaa.cryptoapp.crypto.domain.CoinDataSource
import com.vosaa.cryptoapp.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

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

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        return safeCall<CoinsHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { coinHistoryDto ->
            coinHistoryDto.data.map {
                it.toCoinPrice()
            }
        }
    }
}
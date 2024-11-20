package com.vosaa.cryptoapp.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinsHistoryDto(
    val data: List<CoinPriceDto>
)
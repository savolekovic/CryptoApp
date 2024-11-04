package com.vosaa.cryptoapp.crypto.data.mappers

import com.vosaa.cryptoapp.crypto.data.networking.dto.CoinDto
import com.vosaa.cryptoapp.crypto.domain.Coin

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}
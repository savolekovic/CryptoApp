package com.vosaa.cryptoapp.crypto.presentation.coin_list

import com.vosaa.cryptoapp.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val message: NetworkError): CoinListEvent
}
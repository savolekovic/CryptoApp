package com.vosaa.cryptoapp.crypto.presentation.coin_list

import com.vosaa.cryptoapp.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi) : CoinListAction
}
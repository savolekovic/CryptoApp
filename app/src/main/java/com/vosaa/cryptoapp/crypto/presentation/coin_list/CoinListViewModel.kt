package com.vosaa.cryptoapp.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vosaa.cryptoapp.core.domain.util.onError
import com.vosaa.cryptoapp.core.domain.util.onSuccess
import com.vosaa.cryptoapp.crypto.domain.CoinDataSource
import com.vosaa.cryptoapp.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state.asStateFlow()

    init {
        loadCoins()
    }

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {

            }
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            coinDataSource
                .getCoins()
                .onSuccess { coins ->
                    _state.update { coinListState ->
                        coinListState.copy(
                            isLoading = false,
                            coins = coins.map { it.toCoinUi() }
                        )
                    }

                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }

}
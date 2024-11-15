package com.vosaa.cryptoapp.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vosaa.cryptoapp.core.domain.util.onError
import com.vosaa.cryptoapp.core.domain.util.onSuccess
import com.vosaa.cryptoapp.crypto.domain.CoinDataSource
import com.vosaa.cryptoapp.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state.asStateFlow()

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadCoins()
    }

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {
                _state.update {
                    it.copy(
                        selectedCoin = action.coinUi
                    )
                }
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
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }

}
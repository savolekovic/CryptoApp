package com.vosaa.cryptoapp.di

import com.vosaa.cryptoapp.core.data.networking.HttpClientFactory
import com.vosaa.cryptoapp.crypto.data.networking.RemoteCoinDataSource
import com.vosaa.cryptoapp.crypto.domain.CoinDataSource
import com.vosaa.cryptoapp.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)
}
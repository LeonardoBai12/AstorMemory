package io.lb.impl.ktor.client.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.lb.common.data.service.ClientService
import io.lb.impl.ktor.client.PokemonGameClient
import io.lb.impl.ktor.client.service.ClientServiceImpl

@Module
@InstallIn(ViewModelComponent::class)
object ClientModule {
    @Provides
    fun providesQuoteService(): ClientService {
        return ClientServiceImpl(client = PokemonGameClient.client)
    }
}

package com.github.ilyashvetsov.playlistmaker.sharing.di

import com.github.ilyashvetsov.playlistmaker.sharing.data.ExternalNavigatorImpl
import com.github.ilyashvetsov.playlistmaker.sharing.domain.ExternalNavigator
import com.github.ilyashvetsov.playlistmaker.sharing.domain.SharingInteractor
import com.github.ilyashvetsov.playlistmaker.sharing.domain.SharingInteractorImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val sharingModule = module {
    singleOf(::ExternalNavigatorImpl) { bind<ExternalNavigator>() }

    factoryOf(::SharingInteractorImpl) { bind<SharingInteractor>() }
}

package com.example.grin_technology.di

import com.example.grin_technology.presentation.UserViewModel
import com.example.grin_technology.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object UserViewModelModule {

    @Provides
    fun provideLoginViewModel(userRepository: UserRepository): UserViewModel {
        return UserViewModel(userRepository)

    }

}
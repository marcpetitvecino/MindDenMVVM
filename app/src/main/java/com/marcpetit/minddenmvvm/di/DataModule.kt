package com.marcpetit.minddenmvvm.di

import com.marcpetit.minddenmvvm.data.products.ProductsAPI
import com.marcpetit.minddenmvvm.data.products.ProductsRepositoryImpl
import com.marcpetit.minddenmvvm.domain.products.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://fakestoreapi.com/")
        .client(okHttpClient)
        .build()

    // Products

    @Singleton
    @Provides
    fun provideProductsAPI(retrofit: Retrofit): ProductsAPI = retrofit.create(ProductsAPI::class.java)

    @Singleton
    @Provides
    fun provideProductsRepository(repositoryImpl: ProductsRepositoryImpl): ProductsRepository = repositoryImpl

    // END products

}
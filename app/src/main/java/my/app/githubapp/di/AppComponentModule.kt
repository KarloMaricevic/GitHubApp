package my.app.githubapp.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import my.app.githubapp.mvp.model.repositoryDetails.LanguageResponse
import my.app.githubapp.utils.LanguageResponseDeserializer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
interface AppComponentModule {


    companion object{
        @Singleton
        @Provides
        fun providesRetrofit(): Retrofit {
            val repoLanguageDeserializer = GsonBuilder().setLenient().registerTypeAdapter(
                LanguageResponse::class.java,
                LanguageResponseDeserializer()
            ).create()


            return Retrofit
                .Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(repoLanguageDeserializer))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
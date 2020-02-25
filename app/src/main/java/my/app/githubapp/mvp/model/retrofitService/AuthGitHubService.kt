package my.app.githubapp.mvp.model.retrofitService

import io.reactivex.Single
import my.app.githubapp.mvp.model.retrofitService.responses.AccessTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthGitHubService {


    @Headers("Accept : application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("client_id") code: String
    ): Single<AccessTokenResponse>
}
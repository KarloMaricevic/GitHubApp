package my.app.githubapp.mvp.model.retrofitService.responses

import com.google.gson.annotations.SerializedName

data class AccessTokenResponse(

    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String
)
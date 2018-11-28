package br.com.hoov.donations.Store

import android.os.Build
import br.com.hoov.donations.Model.Comment
import br.com.hoov.donations.Model.Donation
import br.com.hoov.donations.Utilities.Void
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.google.gson.GsonBuilder

private val server: ServerInterface by lazy {
    val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS")
        .create()

    val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(Server.baseURL)
        .client(client)
        .build()

    retrofit.create(ServerInterface::class.java)
}

class Server {

    companion object {
        /**
         * The localhost domain, whether we're using simulator or a physical device. Note that the
         * physical device domain must be updated always that we change network or renew the server
         * lease in the network.
         */
        private val domain = if (Build.FINGERPRINT.contains("generic")) "10.0.2.2" else "192.168.15.16"
        internal val baseURL = "http://$domain:8080/JSONRestWebServiceExample/JavaCodeGeeks/"

        @JvmStatic fun fetchDonations(completionHandler: (List<Donation>?, Throwable?) -> Void) {
            server.getDonations().enqueue(object : Callback<List<Donation>> {

                override fun onResponse(p0: Call<List<Donation>>, response: Response<List<Donation>>) {
                    val results = response.body() ?: return completionHandler(null, Error("An unknown response was received."))
                    completionHandler(results, null)
                }

                override fun onFailure(p0: Call<List<Donation>>?, t: Throwable?) {
                    completionHandler(null, t)
                }
            })
        }

        @JvmStatic fun fetchDonationComments(donationID: Int, completionHandler: (List<Comment>?, Throwable?) -> Void) {
            server.getDonationComments(donationID).enqueue(object : Callback<List<Comment>> {

                override fun onResponse(p0: Call<List<Comment>>, response: Response<List<Comment>>) {
                    val comments = response.body() ?: return completionHandler(null, Error("An unknown response was received."))
                    val sortedComments = comments.sortedBy { it.creationDate }
                    completionHandler(sortedComments, null)
                }

                override fun onFailure(p0: Call<List<Comment>>?, t: Throwable?) {
                    completionHandler(null, t)
                }
            })
        }

        @JvmStatic fun postCommentForDonation(donationID: Int, comment: String, completionHandler: (Comment?, Throwable?) -> Void) {
            server.postCommentForDonation(donationID, comment).enqueue(object : Callback<Comment> {

                @Suppress("NAME_SHADOWING")
                override fun onResponse(p0: Call<Comment>, response: Response<Comment>) {
                    val comment = response.body() ?: return completionHandler(null, Error("An unknown response was received."))
                    completionHandler(comment, null)
                }

                override fun onFailure(p0: Call<Comment>?, t: Throwable?) {
                    completionHandler(null, t)
                }
            })
        }
    }
}

private interface ServerInterface {
    @GET("donations")
    fun getDonations(): Call<List<Donation>>
    @GET("donations/{id}/comments")
    fun getDonationComments(@Path("id") donationID: Int): Call<List<Comment>>
    @FormUrlEncoded
    @POST("donations/{id}/comments")
    fun postCommentForDonation(@Path("id") id: Int, @Field("comment") comment: String): Call<Comment>
}

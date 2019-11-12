package com.example.nationalparkpassport

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit
class ParkManager
{
    private val okHttpClient: OkHttpClient

    // This runs extra code when TwitterManager is created (e.g. the constructor)
    init {
        val builder = OkHttpClient.Builder()

        // This sets network timeouts (in case the phone can't connect
        // to the server or the server is down)
        builder.connectTimeout(20, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)

        // This causes all network traffic to be logged to the console
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging)

        okHttpClient = builder.build()
    }
    fun retrieveParks(stateCode:String, successCallback: (List<Park>) -> Unit, errorCallback: (Exception) -> Unit)
    {


        val request = Request.Builder()
            .url("https://developer.nps.gov/api/v1/parks?stateCode=$stateCode&api_key=IS0ChGgMSmnhwTqvxI71JbzAUZcIHNPba43WYzz2")
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Same error handling to last time
                errorCallback(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val park = mutableListOf<Park>()
                val responseString = response.body()?.string()

                if(response.isSuccessful()&& responseString != null){
                    val parks = JSONObject(responseString).getJSONArray("data")
                    for(i in 0 until parks.length()){
                       val curr = parks.getJSONObject(i)
                        val parkName = curr.getString("fullName")
                        val parkCode = curr.getString("parkCode")

                        val address = " "
                        park.add(
                            Park(
                            name = parkName,
                            address=address,
                            parkCode = parkCode
                            )
                        )
                    }
                    successCallback(park)
                }
                else {
                    // Invoke the callback passed to our [retrieveTweets] function.
                    errorCallback(Exception("Search Parks call failed"))
                }
            }
        })
    }

}



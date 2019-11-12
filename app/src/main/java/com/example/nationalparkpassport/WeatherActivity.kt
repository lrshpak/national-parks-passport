package com.example.nationalparkpassport

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class WeatherActivity : AppCompatActivity()
{
    private val okHttpClient: OkHttpClient
    private lateinit var temperature: TextView
    private lateinit var humidity : TextView
    private lateinit var forecast :TextView
    private lateinit var visiblity :TextView
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        temperature = findViewById(R.id.temp)
        humidity = findViewById(R.id.humidityValue)
        forecast = findViewById(R.id.forecastVal)
        visiblity = findViewById(R.id.visibilityVal)




       val intent: Intent = intent
        val parkName = intent.getStringExtra("weather")


        val geocoder = Geocoder(this, Locale.getDefault())

        val maxResults = 1

        val results = geocoder.getFromLocationName(parkName, maxResults)
        if(results.size !=0) {

            val latitude = results.get(0).getLatitude()
            val longitude = results.get(0).getLongitude()

            val request = Request.Builder()
                .url("https://api.darksky.net/forecast/8f08e6a23b539d2a2601eb3942c2010b/$latitude,$longitude")
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Same error handling to last time

                }

                override fun onResponse(call: Call, response: Response) {

                    val responseString = response.body()?.string()

                    if (response.isSuccessful() && responseString != null) {

                        val weather = JSONObject(responseString)
                        val currently = weather.getJSONObject("currently")
                        val currentTemp = currently.getDouble("temperature")
                        val currentHumidity = currently.getDouble("humidity")
                        val currentVisibility = currently.getDouble("visibility")
                        val minutely = weather.getJSONObject("minutely")
                        val minSummary = minutely.getString("summary")


                        runOnUiThread {
                            temperature.text = currentTemp.toString() + "°F"
                            humidity.text = currentHumidity.toString()
                            forecast.text = minSummary
                            visiblity.text = currentVisibility.toString() + "km"


                        }
                        //successCallback(park)
                    } else {
                        // Invoke the callback passed to our [retrieveTweets] function.

                    }
                }


            })


        }
        else
        {
            temperature.text ="Sorry No Weather Data Available"
        }







    }
}
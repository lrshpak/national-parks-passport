package com.example.nationalparkpassport

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_park_info.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class ParkInfo : AppCompatActivity() {

    private val okHttpClient: OkHttpClient

    private lateinit var parkName: TextView

    private lateinit var parkDescription: TextView


    private lateinit var weatherButton: Button

  //  private lateinit var generalDirection: TextView
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
        setContentView(R.layout.activity_park_info)

        Log.d("ParkInfo", "onCreate called")

        val intent: Intent = intent
        val parkCode = intent.getStringExtra("parkCode")

        parkName = findViewById(R.id.fullParkName)
        parkDescription = findViewById(R.id.parkDescription)
       // generalDirection= findViewById(R.id.generalDirections)
        weatherButton=findViewById(R.id.weatherButton)

        weatherButton.setOnClickListener {
            val intent: Intent = Intent(this, WeatherActivity::class.java)
            intent.putExtra("weather", parkName.text)
            startActivity(intent)
        }


        val request = Request.Builder()
            .url("https://developer.nps.gov/api/v1/parks?parkCode=$parkCode&api_key=IS0ChGgMSmnhwTqvxI71JbzAUZcIHNPba43WYzz2")
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Same error handling to last time

            }

            override fun onResponse(call: Call, response: Response) {

                val responseString = response.body()?.string()

                if(response.isSuccessful()&& responseString != null){
                    val parks = JSONObject(responseString).getJSONArray("data")
                    for(i in 0 until parks.length()){
                        val curr = parks.getJSONObject(i)
                        runOnUiThread {
                            parkName.text = curr.getString("fullName")
                            parkDescription.text = curr.getString("description")
                            //generalDirection.text = curr.getString("directionInfo")
                        }





                    }
                    //successCallback(park)
                }
                else {
                    // Invoke the callback passed to our [retrieveTweets] function.

                }
            }





        })

    }



}
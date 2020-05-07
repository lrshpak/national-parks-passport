package com.example.nationalparkpassport

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast

class ViewParksActivity : AppCompatActivity() {

    private val parkManager : ParkManager = ParkManager()

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parkz)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        //val parks = generateFakeParks()
        val intent : Intent =intent
        val stateCode = intent.getStringExtra("State")
        parkManager.retrieveParks(stateCode,
            successCallback = {
                park ->
                runOnUiThread {
                    recyclerView.adapter = ParkAdapter(
                        parks = park,
                        rowClickedCallback = { clickedPark ->
                            val parkCode = clickedPark
                            val intent: Intent = Intent(this@ViewParksActivity, ParkInfo::class.java)
                            intent.putExtra("parkCode", parkCode)
                            startActivity(intent)

                        }
                    )

                }
            },
            errorCallback = {
            runOnUiThread{
                Toast.makeText(this@ViewParksActivity,"Error getting parks",Toast.LENGTH_LONG).show()
            }
        }
        )


    }

}

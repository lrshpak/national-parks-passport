package com.example.nationalparkpassport

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import java.util.*

class LandingActivity : AppCompatActivity()
{
    private lateinit  var searchStateButton : Button
    private lateinit var searchStateText: EditText


    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val inputtedUsername: String = searchStateText.text.toString().trim()

            //enables button once there is text
            val enableButton: Boolean = inputtedUsername.isNotEmpty()
            searchStateButton.isEnabled=enableButton


        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_activity)

        searchStateButton=findViewById(R.id.searchForState)
        searchStateText = findViewById(R.id.stateText)

        searchStateButton.setOnClickListener {

            val resultStrings= ArrayList<String>()
            var getAddress:String

            // set up geocoder
            val geocoder = Geocoder(this, Locale.getDefault())
            val locationName = searchStateText.getText().toString()
            val maxResults = 2
            lateinit var stateCodeTest : String

            val results = geocoder.getFromLocationName(locationName, maxResults)

            //fills list with contents of the results array
            for (r in results) {


                //resultStrings.add(r.getAddressLine(0).toString())
                resultStrings.add(r.adminArea)
                 stateCodeTest = r.adminArea

            }
            val arrayAdapter= ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice)
            arrayAdapter.addAll(resultStrings)
            val stateCodeMap = mapOf(
                "Alabama" to "AL",
                "Alaska" to "AK",
                "Arizona" to "AZ",
                "Arkansas" to "AR",
                "California" to "Ca",
                "Colorado" to "CO",
                "Connecticut" to "CT",
                "Delaware" to "DE",
                "District of Columbia" to "DC",
                "Florida" to "FL",
                "Georgia" to "GA",
                "Hawaii" to "HI",
                "Idaho" to "ID",
                "Illinois" to "IL",
                "Indiana" to "IN",
                "Iowa" to "IA",
                "Kansas" to "KS",
                "Kentucky" to "KY",
                "Louisiana" to "LA",
                "Maine" to "ME",
                "Maryland" to "MD",
                "Massachusetts" to "MA",
                "Michigan" to "MI",
                "Minnesota" to "MN",
                "Mississippi" to "MS",
                "Missouri" to "MO",
                "Montana" to "MT",
                "Nebraska" to "NE",
                "Nevada" to "NV",
                "New Hampshire" to "NH",
                "New Jersey" to "NJ",
                "New Mexico" to "NM",
                "New York" to "NY",
                "North Carolina" to "NC",
                "North Dakota" to "ND",
                "Ohio" to "OH",
                "Oklahoma" to "OK",
                "Oregon" to "OR",
                "Pennsylvania" to "PA",
                "Rhode Island" to "RI",
                "South Carolina" to "SC",
                "South Dakota" to "SD",
                "Tennessee" to "TN",
                "Texas" to "TX",
                "Utah" to "UT",
                "Vermont" to "VT",
                "Virginia" to "VA",
                "Washington" to "WA",
                "West Virginia" to "WV",
                "Wisconsin" to "WI",
                "Wyoming" to "WY"

            )

            if(resultStrings.size!=0) {
                val stateCode = stateCodeMap[stateCodeTest]
                // val array= arrayOf(resultOne,resultTwo)
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.stateHint))

                    .setAdapter(arrayAdapter) { dialog, which ->
                        getAddress = arrayAdapter.getItem(which)
                        val intent : Intent = Intent(this, ViewParksActivity::class.java)
                        intent.putExtra("State", stateCode)
                        startActivity(intent)

                    }

                    .setPositiveButton(getString(R.string.learn)) { dialog, which ->

                    }
                    .show()
            }
            else
            {
                //shows this message if no matched addresses were found
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.stateHint))
                    .setMessage(getString(R.string.notAState))
                    .setPositiveButton(getString(R.string.okay)){dialog,which->

                    }
                    .show()
            }

        }
    }
}

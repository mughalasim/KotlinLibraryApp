package co.uk.kotlinlibraryapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private val TAG = "MAIN ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // TODO - Initialize the library from here and get a call back to check if the resource
        //  passed in can be parsed correctly
        Log.d(TAG, "Init the library")
        val library = MainLibrary(this, "munro_data") {
            if (it) {
                Toast.makeText(this, "Library initialized successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Failed to init the library", Toast.LENGTH_LONG).show()
            }
        }

        // TODO - Now that the library has been initialized correctly we can
        //   perform queries on the data

        // TODO - If the query is invalid handle the response accordingly
        library.query { success, data ->

        }


    }




}
package co.uk.kotlinlibraryapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {

    private val TAG = "MAIN ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // TODO - Initialize the library from here and get a call back to check if the resource
        //  passed in can be parsed correctly
        Log.d(TAG, "Init the library")



        // TODO - Now that the library has been initialized correctly we can
        //   perform queries on the data



        // TODO - If the query is invalid handle the response accordingly


    }
}
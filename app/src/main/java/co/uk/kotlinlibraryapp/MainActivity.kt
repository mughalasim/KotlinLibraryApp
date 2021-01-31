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
        MainLibrary(this, "munro_data") {success, message, self ->
            if (success) {
                // TODO - Now that the library has been initialized correctly we can
                //   perform queries on the data, want to avoid usage of an un-initialized library

                // TODO - If the query is invalid handle the response accordingly
                self?.query { success, message, data ->
                    Log.d(TAG, data.toString())
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }

            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

}
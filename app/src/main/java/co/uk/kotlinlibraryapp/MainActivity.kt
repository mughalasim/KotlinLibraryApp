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

        // Initialize the library from here and get a call back to check if the resource
        // passed in can be parsed correctly
        Log.d(TAG, "Init the library")
        MainLibrary(this, "munro_data") {success, message, self ->
            if (success) {
                // Now that the library has been initialized correctly we can
                // perform queries on the data, want to avoid usage of an un-initialized library
                self?.queryHeight(MainLibrary.Operation.LessThan, 10.3, MainLibrary.Sort.DEC, 0)

                self?.queryCategory(MainLibrary.Categories.TOP, MainLibrary.Sort.ASC, 10)

            }
            Log.d(TAG, message)
        }
    }

}
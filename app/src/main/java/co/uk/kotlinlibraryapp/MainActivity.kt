package co.uk.kotlinlibraryapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val TAG = "MAIN ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the library from here and get a call back to check if the resource
        // passed in can be parsed correctly

        MainLibrary(this, "munro_data") { success, message, self ->
            if (success) {
                // Now that the library has been initialized correctly we can
                // perform queries on the data, want to avoid usage of an un-initialized library
                self?.queryHeight(LibraryEnums.Operation.LessThan, 10.3, LibraryEnums.Sort.DEC, 0)

                // Can call the same function without passing in the sort and limit
                self?.queryHeight(LibraryEnums.Operation.LessThan, 10.3)

                self?.queryCategory(LibraryEnums.Categories.TOP, LibraryEnums.Sort.ASC, 10)

            }
            Log.d(TAG, message)
        }
    }

}
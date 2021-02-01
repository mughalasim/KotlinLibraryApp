package co.uk.kotlinlibraryapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import co.uk.mylibrary.LibraryEnums
import co.uk.mylibrary.MainLibrary

class MainActivity : AppCompatActivity() {

    private val tag = "MAIN ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the library from here and get a call back to check if the resource
        // passed in can be parsed correctly
        val instance = MainLibrary(this, "munro_data")

        // Now that the library has been initialized correctly we can
        // perform queries on the data, want to avoid usage of an un-initialized library
        instance.queryCategory(LibraryEnums.Categories.TOP, LibraryEnums.Sort.ASC, 10)

        instance.queryHeight(LibraryEnums.Operation.LessThan, 10.3, LibraryEnums.Sort.DEC, 40)

        // Can call the same function without passing in the sort and limit
        instance.queryHeight(LibraryEnums.Operation.LessThan, 2.0)
    }

}
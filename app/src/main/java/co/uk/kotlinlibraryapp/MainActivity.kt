package co.uk.kotlinlibraryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.uk.mylibrary.LibraryEnums
import co.uk.mylibrary.MainLibrary

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the library from here
        val instance = MainLibrary(this, "munro_data")

        // Now that the library has been initialized correctly we can perform queries on the data
        instance.queryCategory(LibraryEnums.Categories.TOP, LibraryEnums.Sort.ASC, 10)

        instance.queryHeight(LibraryEnums.Operation.LessThan, 10.3, LibraryEnums.Sort.DEC, 40)

        // Can call the same function without passing in the sort and limit
        instance.queryHeight(LibraryEnums.Operation.LessThan, 2.0)
    }

}
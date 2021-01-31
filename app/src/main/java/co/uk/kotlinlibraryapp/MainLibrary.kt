package co.uk.kotlinlibraryapp

import android.content.Context
import android.util.Log
import java.io.InputStream

class MainLibrary (context: Context, file_name: String, listener: (Boolean, String,  MainLibrary?) -> Unit) {

    private val TAG = "MAIN LIBRARY"
    private var list: List<ResultList> = listOf()

    init {
        // Im able to get the input stream from the raw resource, now I need to read one line at a time
        val stream: InputStream = context.resources.openRawResource(
            context.resources.getIdentifier(
                file_name,
                "raw", context.packageName
            )
        )

        val reader = stream.bufferedReader()
        val iterator = reader.lineSequence().iterator()

        // Skip the header
        if (iterator.hasNext())
            iterator.next()

        while (iterator.hasNext()) {
            val line = iterator.next()
            // The first line will
            Log.d(TAG, line)
            val split = line.split(",")

        }

        // Close the reader at this point
        reader.close()

        listener(true,"Successfully parsed the CSV file", this)
    }

    fun query(listener: (Boolean, String, List<ResultList>?) -> Unit) {

        listener(false,"", null)
    }
}
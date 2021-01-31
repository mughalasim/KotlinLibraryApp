package co.uk.kotlinlibraryapp

import android.content.Context
import android.content.res.Resources
import android.util.Log
import java.io.InputStream

/**
 * The library gets initialized here, you pass in the activity context, the name of the csv file
 * that is placed in the raw folder without the .csv extension and listen for a callback if they library
 * was able to parse the csv file. The files first line and any other line where first item is empty is not read
 * @param context the activity context is passed in.
 * @param file_name Name of the file without the .csv extension
 * @param listener the callback will contain the initialized class, a boolean to indicate success or false and a message.
 */
class MainLibrary(
    context: Context,
    file_name: String,
    listener: (Boolean, String, MainLibrary?) -> Unit
) {

    private val TAG = "MAIN LIBRARY"
    private var list: MutableList<ResultList> = mutableListOf()



    init {
        try {
            // Im able to get the input stream from the raw resource, now I need to read one line at a time
            val stream: InputStream = context.resources.openRawResource(
                context.resources.getIdentifier(file_name, "raw", context.packageName)
            )

            val reader = stream.bufferedReader()
            val iterator = reader.lineSequence().iterator()

            // Skip the header
            if (iterator.hasNext())
                iterator.next()

            while (iterator.hasNext()) {
                val line = iterator.next()
                val split = line.split(",")
                if (split[0] != "" && split[9] != "") {
                    val item = ResultList(
                        // Was a little confused as the name is a URL, so is the hill
                        name = split[5],
                        height = split[9].toDouble(),
                        hill = split[4],
                        reference = split[13],
                        category = split[27]
                    )
                    list.add(item)
                }
            }

            // Close the reader at this point
            reader.close()

            if (list.isEmpty()) {
                listener(false, "No data is currently present in the provided csv file", null)
            } else {
                listener(true, "Successfully parsed the CSV file", this)
            }

        } catch (e: Resources.NotFoundException) {
            Log.d(TAG, e.toString())
            listener(
                false,
                "File not found in the Raw folder, Are you sure the name is correct",
                null
            )

        } catch (f: IndexOutOfBoundsException) {
            Log.d(TAG, f.toString())
            listener(
                false,
                "The file seems to be missing some data, please check and try again",
                null
            )
        }
    }

    /**
     * Returns a List<ResultList> when you specify the type of operation either greater than or less than the provided height value, set the sort
     * and limit
     * @param operation Could be less than "<" or Greater than ">"
     * @param height The height that needs to be compared with
     * @param sort (Optional) Results to be sorted in ascending or descending order, default is Ascending
     * @param limit (Optional) Results to be limited by this value passed in
     */
    fun queryHeight(operation: LibraryEnums.Operation, height: Double, sort: LibraryEnums.Sort = LibraryEnums.Sort.ASC, limit: Int = 0): List<ResultList> {

        if (validateListSizeOrLimit(limit)){
            return listOf()
        }

        if (height < 0) {
            Log.d(TAG, "Invalid query for height: $height")
            return listOf()
        }

        var filtered: List<ResultList>

        filtered = if (operation == LibraryEnums.Operation.GreaterThan) {
            list.filter { it.height!! > height }
        } else {
            list.filter { it.height!! < height }
        }

        if (limit != 0 && limit < filtered.size) {
          filtered = filtered.take(limit)
        }

        filtered.sortedBy { it.height }

        if (sort == LibraryEnums.Sort.DEC) {
            filtered.reversed()
        }

        filtered.forEach { Log.d(TAG, it.height.toString()) }
        Log.d(TAG, "Found ${filtered.size} items")

        return filtered
    }


    /**
     * Returns a List<ResultList> when you specify the type of category either MUN or TOP, set the sort
     * and limit
     * @param category Could be "MUN" or "TOP"
     * @param sort (Optional) Results to be sorted in ascending or descending order. Default is Ascending
     * @param limit (Optional) Results to be limited by this value passed in
     */
    fun queryCategory(category: LibraryEnums.Categories, order: LibraryEnums.Sort = LibraryEnums.Sort.ASC, limit: Int = 0): List<ResultList> {

        if (validateListSizeOrLimit(limit)){
            return listOf()
        }

        var filtered: List<ResultList>

        filtered = list.filter { it.category!! ==  category.type }

        if (limit != 0 && limit < filtered.size) {
            filtered = filtered.take(limit)
        }

        filtered.sortedBy { it.height }

        if (order == LibraryEnums.Sort.DEC) {
            filtered.reversed()
        }

        filtered.forEach { Log.d(TAG, it.category.toString()) }
        Log.d(TAG, "Found ${filtered.size} items")

        return filtered
    }

    private fun validateListSizeOrLimit(limit: Int): Boolean{
        if (list.isEmpty()) {
            Log.d(TAG, "No data is currently present in the provided csv file")
            return true
        }

        if (limit < 0) {
            Log.d(TAG, "Invalid query for limit: $limit")
            return true
        }

        return false
    }

}
package co.uk.kotlinlibraryapp

import android.content.Context
import android.content.res.Resources
import android.util.Log
import java.io.InputStream
import kotlin.math.absoluteValue

class MainLibrary(
    context: Context,
    file_name: String,
    listener: (Boolean, String, MainLibrary?) -> Unit
) {

    private val TAG = "MAIN LIBRARY"
    private var list: MutableList<ResultList> = mutableListOf()

    enum class Sort { ASC, DEC }
    enum class Operation { GreaterThan, LessThan }
    enum class Categories (val type: String) { MUN("MUN"), TOP("TOP") }

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

    fun queryHeight(operation: Operation, height: Double, order: Sort, limit: Int): List<ResultList> {

        if (list.isEmpty()) {
            Log.d(TAG, "No data is currently present in the provided csv file")
            return listOf()
        }

        if (height < 0) {
            Log.d(TAG, "Invalid query for height: $height")
            return listOf()
        }

        if (limit < 0) {
            Log.d(TAG, "Invalid query for limit: $limit")
            return listOf()
        }

        var filtered: List<ResultList>

        if (operation == Operation.GreaterThan) {
            filtered = list.filter { it.height!! > height }
        } else {
           filtered = list.filter { it.height!! < height }
        }

        if (limit != 0 && limit < filtered.size) {
          filtered = filtered.take(limit)
        }

        filtered.sortedBy { it.height }

        if (order == Sort.DEC) {
            filtered.reversed()
        }

        filtered.forEach { Log.d(TAG, it.height.toString()) }
        Log.d(TAG, "Found ${filtered.size} items")

        return filtered
    }

    fun queryCategory(category: Categories, order: Sort, limit: Int): List<ResultList> {

        if (list.isEmpty()) {
            Log.d(TAG, "No data is currently present in the provided csv file")
            return listOf()
        }

        if (limit < 0) {
            Log.d(TAG, "Invalid query for limit: $limit")
            return listOf()
        }

        var filtered: List<ResultList>

        filtered = list.filter { it.category!! ==  category.type }

        if (limit != 0 && limit < filtered.size) {
            filtered = filtered.take(limit)
        }

        filtered.sortedBy { it.height }

        if (order == Sort.DEC) {
            filtered.reversed()
        }

        filtered.forEach { Log.d(TAG, it.category.toString()) }
        Log.d(TAG, "Found ${filtered.size} items")

        return filtered
    }

    // TODO - Filter by TOP or MUN
    // TODO - Sort by height (ascending or descending)
    // TODO - Sort alphabetically by name (ascending or descending)
    // TODO - Filter by top n number of results (ie top ten)
    // TODO - Filter by minimum height
    // TODO - Filter by maximum height


}
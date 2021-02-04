package co.uk.mylibrary

import android.content.Context
import android.content.res.Resources
import android.util.Log
import java.io.InputStream

/**
 * The library gets initialized here, you pass in the activity context, the name of the csv file
 * that is placed in the raw folder without the .csv extension.
 * The files first line and any other line where first item is empty is not read
 * @param context the activity context is passed in.
 * @param file_name Name of the file without the .csv extension
 */
class MainLibrary(context: Context, file_name: String) {

    private val tag = "MAIN LIBRARY"
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
                if (split.size < 29){
                    throw LibraryException("The file seems to be missing some data on line $line, please check and try again")
                }
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
                throw LibraryException("No data is currently present in the provided csv file")
            }

        } catch (e: Resources.NotFoundException) {
            Log.d(tag, e.toString())
            throw LibraryException("File not found in the Raw folder, Are you sure the name is correct")

        } catch (f: IndexOutOfBoundsException) {
            Log.d(tag, f.toString())
            throw LibraryException("The file seems to be missing some data, please check and try again")

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

        validateListSizeOrLimit(limit)

        if (height < 0) {
            throw LibraryException("Invalid query for height: $height")
        }

        return setSortAndLimit(
            if (operation == LibraryEnums.Operation.GreaterThan) {
                list.filter { it.height > height }.sortedBy { it.height }
            } else {
                list.filter { it.height < height }.sortedBy { it.height }
            },
            sort,
            limit
        )
    }

    /**
     * Returns a List<ResultList> when you specify the type of category either MUN or TOP, set the sort
     * and limit
     * @param category Could be "MUN" or "TOP"
     * @param sort (Optional) Results to be sorted in ascending or descending order. Default is Ascending
     * @param limit (Optional) Results to be limited by this value passed in
     */
    fun queryCategory(category: LibraryEnums.Categories, sort: LibraryEnums.Sort = LibraryEnums.Sort.ASC, limit: Int = 0): List<ResultList> {

        validateListSizeOrLimit(limit)

        return setSortAndLimit(
            list.filter { it.category == category.type }.sortedBy { it.name },
            sort,
            limit
        )
    }

    private fun validateListSizeOrLimit(limit: Int) {
        // Just need to make sure that we are not performing useless queries on an empty list,
        // Will throw an empty list exception
        if (list.isEmpty()) {
            throw LibraryException("No data is currently present in the provided csv file")
        }
        // If the limit passed in is invalid, the library will throw an exception
        if (limit < 0) {
            throw LibraryException("Invalid query for limit: $limit")
        }
    }

    private fun setSortAndLimit(filtered: List<ResultList>, sort: LibraryEnums.Sort, limit: Int): List<ResultList> {
        var result: List<ResultList> = filtered

        // Order the large list in ascending or descending fashion
        if (sort == LibraryEnums.Sort.DEC) {
            result = result.reversed()
        }

        // Apply a limit to the final result
        if (limit != 0 && limit < result.size) {
            result = result.take(limit)
        }

        Log.d(tag, "Found ${filtered.size} items")

        return result
    }

}
package co.uk.mylibrary

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LibraryInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("co.uk.mylibrary.test", appContext.packageName)
    }

    lateinit var libInstance :MainLibrary

    @Before
    fun setup() {
        libInstance = MainLibrary(InstrumentationRegistry.getInstrumentation().targetContext, "munro_data")
        assertNotNull(libInstance)
    }

    @Test
    fun query_height_greater_than_10() {
        val list = libInstance.queryHeight(LibraryEnums.Operation.GreaterThan, 10.0)
        assert(list.isNotEmpty())
    }

    @Test
    fun query_height_greater_than_10_and_limit_10() {
        val list = libInstance.queryHeight(LibraryEnums.Operation.GreaterThan, 10.0, limit = 10)
        assert(list.isNotEmpty())
        assert(list.size == 10)
    }

    @Test
    fun query_height_greater_than_1_is_in_ascending_order() {
        val list = libInstance.queryHeight(LibraryEnums.Operation.GreaterThan, 1.0, LibraryEnums.Sort.ASC, limit = 10)
        assert(list.isNotEmpty())
        assert(list.size == 10)
        assert(list[1].height!! <= list[8].height!!)
    }

    @Test
    fun query_height_greater_than_1_is_in_descending_order() {
        val list = libInstance.queryHeight(LibraryEnums.Operation.GreaterThan, 1.0, LibraryEnums.Sort.DEC, limit = 10)
        assert(list.isNotEmpty())
        assert(list.size == 10)
        assert(list[1].height!! >= list[8].height!!)
    }

    @Test
    fun query_category_equals_mun_and_limit_10() {
        val list = libInstance.queryCategory(LibraryEnums.Categories.MUN, LibraryEnums.Sort.DEC, 10)
        assert(list.isNotEmpty())
        assert(list.size == 10)
        assert(list[1].category.equals(LibraryEnums.Categories.MUN.type))
    }

    @Test
    fun query_category_equals_top_and_limit_10() {
        val list = libInstance.queryCategory(LibraryEnums.Categories.TOP, LibraryEnums.Sort.DEC, 10)
        assert(list.isNotEmpty())
        assert(list.size == 10)
        assert(list[1].category.equals(LibraryEnums.Categories.TOP.type))
    }
}
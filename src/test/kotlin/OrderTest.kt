import kotlin.test.Test
import kotlin.test.assertEquals

internal class OrderTest {

    private val testOrder: Order = Order()

    private val testUserOrders: Array<String> =
        arrayOf(
            "Breakfast 1,2,3",
            "Breakfast 2,3,1",
            "Breakfast 1,2,3,3,3",
            "Breakfast 1",
            "Lunch 1,2,3",
            "Lunch 1,2",
            "Lunch 1,1,2, 3",
            "Lunch 1,2,2",
            "Lunch",
            "Dinner 1,2,3,4",
            "Dinner 1,2,3"
        )

    private val testUserOrderMenu: Array<String> =
        arrayOf(
            "Breakfast",
            "Breakfast",
            "Breakfast",
            "Breakfast",
            "Lunch",
            "Lunch",
            "Lunch",
            "Lunch",
            "Lunch",
            "Dinner",
            "Dinner"
        )
    private val testUserOrderItems: Array<String> =
        arrayOf(
            "1,2,3",
            "2,3,1",
            "1,2,3,3,3",
            "1",
            "1,2,3",
            "1,2",
            "1,1,2,3",
            "1,2,2",
            "",
            "1,2,3,4",
            "1,2,3"
        )


    @Test
    fun orderUpTest(){}

    @Test
    fun validateOrderTest() {
        val expected = 0
        val testList: List<Int> = listOf(1,2,3,4)
        assertEquals(expected, testOrder.validateOrder("Placeholder", testList))
    }

    @Test
    fun breakfastTest() {}

    @Test
    fun lunchTest() {}

    @Test
    fun dinnerTest() {}


}
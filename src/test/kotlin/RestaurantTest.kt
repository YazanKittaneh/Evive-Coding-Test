import kotlin.test.Test
import kotlin.test.assertEquals


/**
 * Note to reader:
 *      I intended to implement upper and lower bound testing for each function. You For the sake of time, only
 *      basic validation was done
 */
internal class RestaurantTest {

    private val testInputs: Array<String> =
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

    private val testOutputs: Array<String> =
        arrayOf(
            "Out: [Eggs, Toast, Coffee]",
            "Out: [Eggs, Toast, Coffee]",
            "Out: [Eggs, Toast, Coffee(3)]",
            "Out: [Unable to process: Side is missing]",
            "Out: [Sandwich, Chips, Soda]",
            "Out: [Sandwich, Chips, Water]",
            "Out: [Unable to process: Main can't be ordered more than once]",
            "Out: [Sandwich, Chips(2), Water]",
            "Out: [Invalid Unable to process. Invalid Order]",
            "Out: [Steak, Potatoes, Wine, Water, Cake]",
            "Out: [Dessert is missing]"
        )

    private val testErrors: Array<String> =
        arrayOf(
            "Unable to process: Main is missing",
            "Unable to process: Main can't be ordered more than once",
            "Unable to process: Side is missing",
            "can't have more than 1 side",
            "desert isn't an Option",
            "desert isn't an Option",
            "can't have more than 1 side",
            "Dessert is missing"
        )

    private val testMenues: Array<String> =
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

    private val testUserOrderExpectations: Array<String> =
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



    /**
     * Testing error handling with item not in
     * Expected to return specific String
     */
    @Test
    fun itemizeOrderLowerTest() {
        val testRestaurant: Restaurant = Restaurant()
        val testOrder = arrayListOf<Int>(0,0,0,0)
        assertEquals(testOrder, testRestaurant.itemizeOrder(testOrder))
        assertEquals(true, testRestaurant.errorStack.contains("\"Unable to process: Item not available\""))
    }


    /**
     * Testing permutations of valid inputs
     * Expected to return an empty error stack
     */
    @Test
    fun itemizeOrderPermutation() {
        val testRestaurant: Restaurant = Restaurant()
        //testing values
        for(j in 0..10) {
            val testValue = j
            for (i in 1..4) {
                val test1 = MutableList(testValue) { i }
                val expectedOutput: MutableList<Int> = mutableListOf(0, 0, 0, 0)
                expectedOutput.set(i - 1, testValue)
                assertEquals(expectedOutput, testRestaurant.itemizeOrder(test1))
            }
        }
    }

    /**
     * Testing valid input
     * Expected to return an empty error stack
     */
    @Test
    fun validateOrderTest() {
        val testRestaurant = Restaurant()
        val testMenu = "Lunch"
        val testOrder = listOf<Int>(1,2,3)
        assertEquals(true, testRestaurant.validateOrder(testMenu, testOrder).isEmpty())
    }


    /**
     * Simulates adding inputs to the stack that would come from external functions
     * Expected to clear the printStack and errorStack
     */
    @Test
    fun cleanTable() {
        val testRestaurant = Restaurant()
        val testOrder = "Lunch 1,2,3"
        repeat(10){
            testRestaurant.printStack.add(testInputs.random())
            testRestaurant.errorStack.add(testErrors.random())
        }
        testRestaurant.cleanTable()

        assertEquals(true, testRestaurant.printStack.isEmpty())
        assertEquals(true, testRestaurant.errorStack.isEmpty())

    }
    /**
     * Simulates entering the parts of the order
     * Expected to return an Array<String> of a constructed meal, in order
     */
    @Test
    fun sendToKitchenTest() {
        val testRestaurant: Restaurant = Restaurant()
        val testMenu = "Lunch"
        val testOrder = arrayListOf<Int>(1, 2, 3, 0) //validation assumes a correct input
        val expected = arrayListOf<String>("Sandwhich", "Chips(2)", "Soda(3)")
        assertEquals(expected, testRestaurant.sendToKitchen(testMenu, testRestaurant.lunchMenu, testOrder))
    }



}
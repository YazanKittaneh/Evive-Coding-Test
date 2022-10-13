fun main(args: Array<String>) {
    while(true) {
        val order = readLine()!!
        val table = Restaurant()
        table.waiter(order)
        table.getReceipt(order)
    }
}


class Restaurant() {
    val breakfastMenu: Array<String> = arrayOf("Eggs", "Toast", "Coffee", "")
    val lunchMenu: Array<String> = arrayOf("Sandwich", "Chips", "Soda", "")
    val dinnerMenu: Array<String> = arrayOf("Steak", "Potatoes", "Wine", "Cake")
    var printStack: MutableList<String> = mutableListOf()
    var errorStack: MutableList<String> = mutableListOf()


    /**
     * Applies the itemization, validation, and 'cooking' of the order.
     *
     * @param  order, the user input.
     * @returns MutableList<String> representing each menu item and it's quantity.
     */
    fun waiter(order: String): MutableList<String>{
        val splitInput = order.split(" ")

        /*We can validate at this level that there are two items - (menu, order) */
        if (splitInput.size != 2) {
            printStack.add("Invalid Unable to process. Invalid Order")
            return printStack
        }

        val menu = splitInput[0]
        val splitOrder = splitInput[1].split(",").map { it.toInt() }

        val orderCounter = itemizeOrder(splitOrder)
        validateOrder(menu, orderCounter)

        when (menu) {
            "Breakfast" -> printStack = sendToKitchen(menu, breakfastMenu, orderCounter)
            "Lunch" -> printStack = sendToKitchen(menu, lunchMenu, orderCounter)
            "Dinner" -> printStack = sendToKitchen(menu, dinnerMenu, orderCounter)
        }

        return printStack
    }


    /**
     * Helper function that formats the order into an itemized list to make it easier to parse.
     *
     * @param  order, the user input.
     * @returns ArrayList<Int> representing the quality of each item.
     */
    fun itemizeOrder(order: List<Int>): ArrayList<Int> {
        val orderCounter: ArrayList<Int> = arrayListOf(0, 0, 0, 0)
        for (n in order) {
            when (n) {
                1 -> orderCounter[0]++
                2 -> orderCounter[1]++
                3 -> orderCounter[2]++
                4 -> orderCounter[3]++
                else -> {
                    errorStack.add("Unable to process: Item not available")
                }
            }
        }
        return orderCounter
    }


    /**
     * Makes sure the order is a valid and follows the menu rules.
     *
     * @param  menu, the user input.
     * @param  orderCounter, the itemized order.
     * @returns MutableList<String> containing any errors found in the order.
     */
    fun validateOrder(menu: String, orderCounter: List<Int>): MutableList<String> {

        //standard validation
        if (orderCounter[0] < 1) errorStack.add("Unable to process: Main is missing")
        if (orderCounter[0] > 1) errorStack.add("Unable to process: Main can't be ordered more than once")
        if (orderCounter[1] < 1) errorStack.add("Unable to process: Side is missing")

        //menu based validation
        when (menu) {
            "Breakfast" -> {
                if (orderCounter[1] > 1) errorStack.add("can't have more than 1 side")
                if (orderCounter[3] > 0) errorStack.add("desert isn't an Option")
            }

            "Lunch" -> {
                if (orderCounter[3] > 0) errorStack.add("desert isn't an Option")
            }
            "Dinner" -> {
                if (orderCounter[1] > 1) errorStack.add("can't have more than 1 side")
                if (orderCounter[3] < 1) errorStack.add("Dessert is missing")
            }
        }

        return errorStack
    }


    /**
     * Prints the input and output in the correct format.
     *
     * @param  input, the user input.
     */
    fun getReceipt(input: String){
        println("In: $input")
        if(errorStack.isNotEmpty()) println("Out: $errorStack") else println("Out: $printStack")
    }


    /**
     * A cleaner function that clears the order and error lists.
     */
    fun cleanTable(){
        this.errorStack.clear()
        this.printStack.clear()
    }


    /**
     * Takes the user inputted order
     *
     * @param  menu, the user's menu selection
     * @param  menuList, the entire restaurant's menu
     * @param  itemizedOrder, the user's itemized order
     * @returns MutableList<String> containing the user's order in the finalized format
     */
    fun sendToKitchen(menu: String, menuList: Array<String>, itemizedOrder: ArrayList<Int>): MutableList<String> {

        val finishedOrder = mutableListOf<String>()

        for (i in 0..3) { // do it for the 4 items
            if (itemizedOrder.get(i) > 1) finishedOrder.add(menuList.get(i) + "(${itemizedOrder.get(i)})")
            if (itemizedOrder.get(i) == 1) finishedOrder.add(menuList.get(i))
            when(menu){
                "Breakfast","Lunch" -> {
                    if (itemizedOrder.get(i) == 0 && i == 2) finishedOrder.add("Water")
                }
                "Dinner" -> {
                    if (i == 2) finishedOrder.add("Water")
                }
            }
        }
        return finishedOrder
    }


}


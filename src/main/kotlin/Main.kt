class Order() {
    fun main(args: Array<String>) {
        orderUp("Breakfast 1,2,3")
    }

    fun orderUp(input: String) {
        println("")
        println("In: ${input}")
        val splitInput = input.split(" ")

        /*We can validate at this level that there are two items - (menu, order) */
        if (splitInput.size != 2) {
            println("Invalid Order: Found more than one menu selection")
            return
        }

        val menu = splitInput[0]
        val order = splitInput[1].split(",").map { it.toInt() }
        val orderCounter = createOrderCount(order)

        if (validateOrder(menu, orderCounter) > 0) return


        when (menu) {
            "Breakfast" -> breakfast(orderCounter)
            "Lunch" -> lunch(orderCounter)
            "Dinner" -> dinner(orderCounter)
            else -> {
                println("lmao what. Well this shit broke")
                return
            }
        }
    }

    /**
     * All order validation will be migrated to this function to keep other functions DRY
     */
    fun validateOrder(menu: String, orderCounter: List<Int>): Int {
        var errorStack: ArrayList<String> = arrayListOf()


        if (orderCounter[0] != 1) errorStack.add("Unable to process: Main is missing")
        if (orderCounter[1] < 1) errorStack.add("Unable to process: Side is missing")

        when (menu) {
            "Breakfast" -> {
                if (orderCounter.get(1) > 1) errorStack.add("can't have more than 1 side")
                if (orderCounter.get(3) > 1) errorStack.add("desert isn't an Option")
            }

            "Lunch" -> {
                if (orderCounter.get(3) > 1) errorStack.add("desert isn't an Option")
            }

            "Dinner" -> {
                if (orderCounter.get(1) > 1) errorStack.add("can't have more than 1 side")
                if (orderCounter.get(3) < 1) errorStack.add("desert isn't an Option")
            }
        }

        println(errorStack)
        return errorStack.size
    }

    //create counter helper function
    fun createOrderCount(order: List<Int>): ArrayList<Int> {
        val orderCounter: ArrayList<Int> = arrayListOf(0, 0, 0, 0) //Main, sides, drinks, desert
        for (n in order) {
            when (n) {
                1 -> orderCounter[0]++
                2 -> orderCounter[1]++
                3 -> orderCounter[2]++
                4 -> orderCounter[3]++
                else -> {
                    println("Aint' a valid order homie")
                }
            }
        }
        return orderCounter
    }

    fun breakfast(order: ArrayList<Int>) {
        val breakfastMenu: Array<String> = arrayOf("Eggs", "Toast", "Coffee")

        var stack = mutableListOf<String>()

        for (i in 0 until order.size - 1) {
            if (order.get(i) > 1) stack.add(breakfastMenu.get(i) + "(${order.get(i)})")
            if (order.get(i) == 1) stack.add(breakfastMenu.get(i))
            if (order.get(i) == 0) break
        }

        println("Final Order: $stack")

    }

    fun lunch(order: ArrayList<Int>) {
        //Sandwich, Chips, Soda
        //At lunch, multiple sides can be ordered
        val lunchMenu: Array<String> = arrayOf("Sandwich", "Chips", "Soda", "")

        var stack = mutableListOf<String>()

        //[1,2,3]
        for (i in 0..4) {
            if (order.get(i) > 1) stack.add(lunchMenu.get(i) + "(${order.get(i)})")
            if (order.get(i) == 1) stack.add(lunchMenu.get(i))
            if (order.get(i) == 0 && i == 2) stack.add("Water")
            if (order.get(i) == 0) break
        }

        println("Final Order: $stack")
    }

    fun dinner(order: ArrayList<Int>) {
        //Steak, Potatoes, Wine, Water, Cake
        //At dinner, dessert must be ordered
        //At dinner, water is always provided

        val breakfastMenu: Array<String> = arrayOf("Steak", "Potatoes", "Wine", "Cake")


        var stack = mutableListOf<String>()

        //[1,2,3]
        for (i in 0 until order.size) {
            if (order.get(i) > 1) stack.add(breakfastMenu.get(i) + "(${order.get(i)})")
            else stack.add(breakfastMenu.get(i))
            if (i == 2) stack.add("Water")
        }

        println("Final Order: $stack")
    }

}
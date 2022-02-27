package com.example.pos2



class Macaron (Name: String,Price: Int,ID: Long,Color: String) : Product() {

    override val name : String = Name
    override var price: Int = Price
    override val id : Long = ID
    val color : String = Color
    var quantity = 1

    companion object {


        fun createMacaronList(): ArrayList<Macaron>{
            val macarons = ArrayList<Macaron>()
            macarons.add(Macaron("Lemon", 45, 1001, "Yellow"))
            macarons.add(Macaron("Red Velvet", 55, 1002, "Red"))
            macarons.add(Macaron("Earl grey", 35, 1003, "Blue"))
            macarons.add(Macaron("Rose", 25, 1004, "Pink"))
            macarons.add(Macaron("Mint", 15, 1005, "Mint"))
            macarons.add(Macaron("Coffee", 50, 1006, "Black"))
            macarons.add(Macaron("Matcha", 60, 1007, "Green"))


            return macarons
        }

        fun createDrinkList(): ArrayList<Macaron>{
            val drinks = ArrayList<Macaron>()
            drinks.add(Macaron("Lemon", 45, 1001, "Yellow"))
            drinks.add(Macaron("Red Velvet", 55, 1002, "Red"))
            drinks.add(Macaron("Earl grey", 35, 1003, "Blue"))
            drinks.add(Macaron("Rose", 25, 1004, "Pink"))
            drinks.add(Macaron("Mint", 15, 1005, "Mint"))
            drinks.add(Macaron("Coffee", 50, 1006, "Black"))
            drinks.add(Macaron("Matcha", 60, 1007, "Green"))
            return drinks
        }

        fun createPastaList(): ArrayList<Macaron>{
            val pasta = ArrayList<Macaron>()
            pasta.add(Macaron("Lemon", 45, 1001, "Yellow"))
            pasta.add(Macaron("Red Velvet", 55, 1002, "Red"))
            pasta.add(Macaron("Earl grey", 35, 1003, "Blue"))
            pasta.add(Macaron("Rose", 25, 1004, "Pink"))
            pasta.add(Macaron("Mint", 15, 1005, "Mint"))
            pasta.add(Macaron("Coffee", 50, 1006, "Black"))
            pasta.add(Macaron("Matcha", 60, 1007, "Green"))
            return pasta
        }

        fun createPizzaList(): ArrayList<Macaron>{
            val pizza = ArrayList<Macaron>()
            pizza.add(Macaron("Lemon", 45, 1001, "Yellow"))
            pizza.add(Macaron("Red Velvet", 55, 1002, "Red"))
            pizza.add(Macaron("Earl grey", 35, 1003, "Blue"))
            pizza.add(Macaron("Rose", 25, 1004, "Pink"))
            pizza.add(Macaron("Mint", 15, 1005, "Mint"))
            pizza.add(Macaron("Coffee", 50, 1006, "Black"))
            pizza.add(Macaron("Matcha", 60, 1007, "Green"))
            return pizza
        }

        fun createFruitList(): ArrayList<Macaron>{
            val fruit = ArrayList<Macaron>()
            fruit.add(Macaron("Lemon", 45, 1001, "Yellow"))
            fruit.add(Macaron("Red Velvet", 55, 1002, "Red"))
            fruit.add(Macaron("Earl grey", 35, 1003, "Blue"))
            fruit.add(Macaron("Rose", 25, 1004, "Pink"))
            fruit.add(Macaron("Mint", 15, 1005, "Mint"))
            fruit.add(Macaron("Coffee", 50, 1006, "Black"))
            fruit.add(Macaron("Matcha", 60, 1007, "Green"))
            return fruit
        }

        fun createIcecreamList(): ArrayList<Macaron>{
            val icecream = ArrayList<Macaron>()
            icecream.add(Macaron("Lemon", 45, 1001, "Yellow"))
            icecream.add(Macaron("Red Velvet", 55, 1002, "Red"))
            icecream.add(Macaron("Earl grey", 35, 1003, "Blue"))
            icecream.add(Macaron("Rose", 25, 1004, "Pink"))
            icecream.add(Macaron("Mint", 15, 1005, "Mint"))
            icecream.add(Macaron("Coffee", 50, 1006, "Black"))
            icecream.add(Macaron("Matcha", 60, 1007, "Green"))
            return icecream
        }

    }

}
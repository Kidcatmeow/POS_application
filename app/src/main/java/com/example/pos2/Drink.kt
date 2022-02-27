package com.example.pos2

class Drink (Name: String, Price: Int, ID: Long, Color: String) : Product() {

    override val name : String = Name
    override val price: Int = Price
    override val id : Long = ID
    val color : String = Color

    companion object {
        fun createDrinkList(): ArrayList<Drink>{
            val drinks = ArrayList<Drink>()

            drinks.add(Drink("Lemon",45,1001,"Yellow"))
            drinks.add(Drink("Apple",55,1002,"Red"))
            drinks.add(Drink("Earl grey",35,1003,"Blue"))
            drinks.add(Drink("Rose",25,1004,"Pink"))
            drinks.add(Drink("Mint",15,1005,"Mint"))
            drinks.add(Drink("Coffee",50,1006,"Black"))
            drinks.add(Drink("Matcha",60,1007,"Green"))



            return drinks
        }
    }

}
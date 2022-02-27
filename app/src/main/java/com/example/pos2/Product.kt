package com.example.pos2

import androidx.room.*

abstract class Product {
    open val name : String = "PRODUCT_NAME"
    open val price : Int = 0
    open val id : Long = 0
}

@Entity(tableName = "productTb1")
data class ProductDB (
    @PrimaryKey(autoGenerate = true) var uid: Long?,
    @Columninfo (name = "name") var name : String,
    @Columninfo (name = "price") var price: Int,
)
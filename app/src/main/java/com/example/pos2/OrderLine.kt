package com.example.pos2

import androidx.room.*

@Entity (tableName = "orderLineTb1")
data class OrderLine (
    @PrimaryKey (autoGenerate = true) var uid:Long?,
    @ColumnInfo (name="order_id") var orderID:Long,
    @ColumnInfo (name="product_id") var productID:Long,
    @ColumnInfo (name="price") var price:Int,
    @ColumnInfo (name="quantity") var quantity:Int,
)


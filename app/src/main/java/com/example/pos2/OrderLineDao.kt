package com.example.pos2


import androidx.room.*

@Dao
interface OrderLineDao {
    @Query("SELECT * FROM orderLineTb1")
    fun getAll() : List<OrderLine>

    @Query("SELECT * FROM orderLineTb1 WHERE uid LIKE :id LIMIT 1")
    fun findByID(id:Long):OrderLine

    @Insert
    fun insertAll (vararg orderLine:OrderLine)

    @Delete
    fun delete(orderLine: OrderLine)

    @Query("DELETE FROM orderLineTb1")
    fun deleteAll()

    @Query("UPDATE orderLineTb1 SET price=:price,quantity=:quantity WHERE product_id LIKE :productid")
    fun update(productid:Long,price:Int,quantity:Int)

    @Update
    fun updateorderline(orderLine: OrderLine)
}
package com.example.pos2

import androidx.room.*

@Dao
interface OrderDao {
    @Query("SELECT * FROM orderTb1")
    fun getAll() : List<Order>

    @Query("SELECT * FROM orderTb1 WHERE uid LIKE :id LIMIT 1")
    fun findByID(id:Long):Order

    @Insert
    fun insert (order:Order): Long

    @Delete
    fun delete(order: Order)

    @Query ("DELETE FROM orderTb1")
    fun deleteAll()
}
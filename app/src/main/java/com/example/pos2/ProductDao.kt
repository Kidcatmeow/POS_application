package com.example.pos2

import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM productTb1")
    fun getAll() : List<ProductDB>

    @Query("SELECT * FROM productTb1 WHERE uid = :id")
    fun loadAllByIds(id:IntArray):List <ProductDB>

    @Query("SELECT * FROM productTb1 WHERE uid LIKE :id LIMIT 1")
    fun findByID (id : Long): ProductDB

    @Insert
    fun insertAll (vararg products: ProductDB)

    @Delete
    fun delete(product : ProductDB)
}
package com.example.pos2


import androidx.room.*

@Entity (tableName = "orderTb1")
data class Order (
    @PrimaryKey (autoGenerate = true) val uid: Long?,
    @Columninfo (name = "branch_id") val branchID : Long,
    @Columninfo (name = "staff_id") val staffID: Long,
)

annotation class Columninfo(val name: String)







package com.massimoregoli.myproverbs.db

import androidx.room.*

@Dao
interface DaoProverb {
    @Insert
    fun insertAll(proverbs: List<Proverb>)

    @Insert
    fun insert(proverb: Proverb)

    @Update
    fun update(proverb: Proverb)

    @Delete
    fun delete(proverb: Proverb)

    @Query(
        """
        SELECT * FROM Proverb
            WHERE favorite = :favorite OR (:favorite = 0) 
            ORDER BY RANDOM()
            LIMIT 1
    """
    )
    fun loadRandomProverb(favorite: Int): Proverb?
}
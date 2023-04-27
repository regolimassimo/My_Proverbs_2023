package com.massimoregoli.myproverbs.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Proverb(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var text: String,
    var lang: String,
    var category: Int,
    var favorite: Int
)
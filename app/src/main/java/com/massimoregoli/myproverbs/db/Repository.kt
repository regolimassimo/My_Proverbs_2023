package com.massimoregoli.myproverbs.db

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository(private val dao: DaoProverb) {
    fun readNext(favorite: Int): Proverb? {
        return dao.loadRandomProverb(favorite)
    }

    fun update(proverb: Proverb) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.update(proverb)
        }
    }
}
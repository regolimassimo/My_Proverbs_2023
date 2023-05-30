package com.massimoregoli.myproverbs.contracts

import android.database.ContentObserver
import android.os.Handler
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ResolverHelper(private val context: ComponentActivity, handler: Handler?) {
    private val _titleLiveData: MutableLiveData<List<String>> = MutableLiveData(listOf())

    val titleLiveData: LiveData<List<String>>
        get() = _titleLiveData

    private val _motd: MutableLiveData<String> = MutableLiveData("")

    val motd: LiveData<String>
        get() = _motd


    init {
        context.lifecycleScope.launch {
            context.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                _titleLiveData.value = getAllDomains()
                context.contentResolver.registerContentObserver(MyContract.DomainEntry.DOMAIN_URI,
                    true,
                    object : ContentObserver(handler) {
                        override fun onChange(selfChange: Boolean) {
                            Log.w("PROVERBS", "Change DB")
                            context.lifecycleScope.launch {
                                _titleLiveData.value = getAllDomains()
                            }
                        }
                    })

                _motd.value = getById(657)
                context.contentResolver.registerContentObserver(MyContract.DomainEntry.DOMAIN_URI,
                    true,
                    object : ContentObserver(handler) {
                        override fun onChange(selfChange: Boolean) {
                            Log.w("PROVERBS", "Change DB")
                            context.lifecycleScope.launch {
                                _motd.value = getById(657)
                            }
                        }
                })
            }
        }
    }

    suspend fun getAllDomains(): List<String> {
        return withContext(Dispatchers.IO) {
            val titles = mutableListOf<String>()

            val cursor = context.contentResolver!!.query(
                MyContract.DomainEntry.DOMAIN_URI,
                null,
                null,
                null,
                null
            )
            cursor?.let {
                val titleIndex = it.getColumnIndex(MyContract.DomainEntry.TITLE)
                while (it.moveToNext()) {
                    titles.add(it.getString(titleIndex))
                }
            }
            cursor?.close()
            return@withContext titles
        }
    }

    suspend fun getById(id: Int) : String {
        return withContext(Dispatchers.IO) {
            var title = ""

            val cursor = context.contentResolver!!.query(
                MyContract.DomainEntry.DOMAIN_URI,
                null,
                "id=?",
                arrayOf("$id"),
                null
            )
            cursor?.let {
                val titleIndex = it.getColumnIndex(MyContract.DomainEntry.TITLE)
                it.moveToNext()
                title = it.getString(titleIndex)

            }
            cursor?.close()
            return@withContext title
        }
    }
}
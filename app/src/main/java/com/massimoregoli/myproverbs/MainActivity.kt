package com.massimoregoli.myproverbs

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.massimoregoli.myproverbs.db.DbProverb
import com.massimoregoli.myproverbs.db.Repository
import com.massimoregoli.myproverbs.ui.theme.MyProverbsTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.massimoregoli.myproverbs.contracts.ResolverHelper
import com.massimoregoli.myproverbs.db.Proverb
import com.massimoregoli.myproverbs.screens.MainView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val resolverHelper = ResolverHelper(this, Handler(Looper.getMainLooper()))
        resolverHelper.titleLiveData.observe(this) {
            Log.w("PROVERBS", "Size: ${it.size}")
        }

        resolverHelper.motd.observe(this) {
            Log.w("PROVERBS", it)
        }

        setContent {
            val context = LocalContext.current
            val db = DbProverb.getInstance(context)
            val repository = Repository(db.proverbDao())
            var proverb: Proverb? = null

            var text by rememberSaveable {
                mutableStateOf("")
            }
            var id by rememberSaveable {
                mutableStateOf(0)
            }
            var favorite by rememberSaveable {
                mutableStateOf(0)
            }
            var onlyFavorite by rememberSaveable {
                mutableStateOf(0)
            }

            MyProverbsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainView(id, text, favorite, onlyFavorite,
                        {
                            onlyFavorite = 1 - onlyFavorite
                        },
                        {
                            CoroutineScope(Dispatchers.IO).launch {
                                proverb = repository.readNext(onlyFavorite)
                                if (proverb != null) {
                                    text = proverb!!.text
                                    id = proverb!!.id
                                    favorite = proverb!!.favorite
                                } else {
                                    text = getString(R.string.no_fav)
                                    id = 0
                                    favorite = 0
                                }
                            }
                        },
                        {
                            if (proverb != null) {
                                favorite = 1 - favorite
                                proverb!!.favorite = favorite
                                repository.update(proverb!!)
                            }
                        })
                }
            }
        }
    }
}

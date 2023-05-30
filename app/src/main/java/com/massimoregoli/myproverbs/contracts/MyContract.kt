package com.massimoregoli.myproverbs.contracts

import android.net.Uri
import android.provider.BaseColumns

object MyContract {
    const val AUTHORITY = "com.massimoregoli.myproverbs"
    val AUTHORITY_URI = Uri.parse("content://$AUTHORITY")!!

    object DomainEntry : BaseColumns {
        val TABLE_NAME = "proverb"
        val DOMAIN_URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE_NAME)
        val TITLE = "text"
    }
}
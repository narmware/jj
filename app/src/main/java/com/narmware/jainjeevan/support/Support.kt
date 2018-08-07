package com.narmware.jainjeevan.support

import android.content.Context
import android.widget.Toast

/**
 * Created by rohitsavant on 07/08/18.
 */
class Support {
    companion object {
        public fun mt(context: Context, text: String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}
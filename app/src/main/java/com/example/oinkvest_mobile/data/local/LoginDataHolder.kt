package com.example.oinkvest_mobile.data.local

object LoginDataHolder {
    var email: String? = null
    var password: String? = null

    fun clear() {
        email = null
        password = null
    }
}
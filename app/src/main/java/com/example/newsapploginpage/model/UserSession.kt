package com.example.newsapploginpage

object UserSession {
    var userId: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null

    fun setUserData(userId: String, firstName: String, lastName: String, email: String) {
        this.userId = userId
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
    }

    fun clearSession() {
        userId = null
        firstName = null
        lastName = null
        email = null
    }
}



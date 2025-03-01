package com.stp.pacientes.api

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.util.Base64

class BasicAuthenticator(private val username: String, private val password: String) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val credentials = "$username:$password"
        val authHeader = "Basic " + Base64.getEncoder().encodeToString(credentials.toByteArray())
        return response.request().newBuilder()
            .header("Authorization", authHeader)
            .build()
    }
}

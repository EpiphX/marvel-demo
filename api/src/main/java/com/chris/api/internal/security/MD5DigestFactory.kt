package com.chris.api.internal.security

import java.security.MessageDigest

/**
 * The MD5DigestFactory is based on the server to server authentication documented in
 * [Marvel Developer Authorization Documentation](https://developer.marvel.com/documentation/authorization).
 *
 * Ideally, in production we would not have the private api key as part of the mobile application or lib, but I believe for the sake of the demo
 * this was the intention based on the assignment details.
 */
interface MD5DigestFactory {
    data class MD5DigestInfo(val ts: String, val apiKey: String, val hash: String)

    fun create(ts: String, privateApiKey: String, publicApiKey: String): MD5DigestInfo
}

class MD5DigestFactoryImpl : MD5DigestFactory {
    private val md5 = MessageDigest.getInstance("MD5")

    override fun create(
        ts: String,
        privateApiKey: String,
        publicApiKey: String
    ): MD5DigestFactory.MD5DigestInfo {
        // Creates the key as per documentation guidelines in developer.marvel.com.
        val key = ts + privateApiKey + publicApiKey
        // Transforms into hexString
        val hash = md5.digest(key.toByteArray()).joinToString("") { "%02x".format(it) }
        return MD5DigestFactory.MD5DigestInfo(ts, publicApiKey, hash)
    }
}
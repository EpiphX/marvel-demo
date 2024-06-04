package com.chris.api.internal.security

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.security.MessageDigest

class MD5DigestFactoryImplTest {
    @Test
    fun `test MD5 Digest`() {
        val md5 = MessageDigest.getInstance("MD5")
        val apiKey = "1234"
        val privateKey = "abcd"
        val ts = "1"

        val hashedKey = ts + privateKey + apiKey
        val hash = md5.digest(hashedKey.toByteArray())

        val hexString = hash.joinToString("") { "%02x".format(it) }
        Assertions.assertEquals("ffd275c5130566a2916217b101f26150", hexString)
    }
}
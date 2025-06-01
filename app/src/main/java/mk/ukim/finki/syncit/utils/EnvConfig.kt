package mk.ukim.finki.syncit.utils

import android.content.Context
import mk.ukim.finki.syncit.R

object EnvConfig {
    private lateinit var smtpEmail: String
    private lateinit var smtpPassword: String

    fun init(context: Context) {
        smtpEmail = context.getString(R.string.SMTP_EMAIL)
        smtpPassword = context.getString(R.string.SMTP_PASSWORD)
    }

    fun getEmail(): String = smtpEmail
    fun getPassword(): String = smtpPassword
}
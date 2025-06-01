package mk.ukim.finki.syncit.utils

import java.util.Properties
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object EmailSender {

    fun sendEmail(toEmail: String, subject: String, body: String) {
        val props = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", "smtp.gmail.com")
            put("mail.smtp.port", "587")
        }

        val username = EnvConfig.getEmail()
        val password = EnvConfig.getPassword()

        val session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })

        try {
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(username))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail))
                setSubject(subject)
                setContent(body, "text/html; charset=utf-8")
            }

            Transport.send(message)

            println("Email sent successfully to $toEmail")

        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }
}
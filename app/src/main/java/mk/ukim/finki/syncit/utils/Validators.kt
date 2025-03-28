package mk.ukim.finki.syncit.utils

object ValidationUtils {
    private val nameRegex = "^[A-Za-z]+$".toRegex()

    fun isValidName(name: String): Boolean {
        return nameRegex.matches(name)
    }

    fun isValidEmail(email: String): Boolean {
        return "@" in email
    }

    fun isValidPhoneNumber(value: String?): String? {
        return when {
            value.isNullOrBlank() -> "Enter a phone number"
            value.startsWith("07") && value.length == 9 -> ""
            value.startsWith("+3897") && value.length == 12 -> ""
            else -> "Valid format: 07X XXX XXX or +389 7X XXX XXX"
        }
    }

    fun isValidPassword(value: String?): List<String> {
        val errors = mutableListOf<String>()

        if (value.isNullOrBlank()) {
            errors.add("Password is required")
        } else {
            if (!value.any { it.isLowerCase() }) {
                errors.add("Password must contain at least one lowercase letter")
            }
            if (!value.any { it.isUpperCase() }) {
                errors.add("Password must contain at least one uppercase letter")
            }
            if (!value.any { it.isDigit() }) {
                errors.add("Password must contain at least one number")
            }
            if (!value.any { it in "!@#\$%^&*(),.?\":{}|<>" }) {
                errors.add("Password must contain at least one special character")
            }
            if (value.length < 8) {
                errors.add("Password must be at least 8 characters long")
            }
        }

        return errors
    }
}
package mk.ukim.finki.syncit.utils

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.util.Base64
import java.io.ByteArrayOutputStream

object QRCodeGenerator {

    fun generateQrCode(content: String, size: Int = 512): Bitmap? {
        val writer = QRCodeWriter()
        return try {
            val bitMatrix: BitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size)
            val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
            for (x in 0 until size) {
                for (y in 0 until size) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

    fun generateUniqueCode(): String {
        val now = LocalDateTime.now()
        val datePart = now.format(DateTimeFormatter.ofPattern("dd-MM"))
        val randomPart = (1..16)
            .map { ('A'..'Z') + ('0'..'9') }
            .flatten()
            .shuffled()
            .take(24)
            .joinToString("")

        return "$datePart-$randomPart"
    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }

    fun generateQRCodeBase64(content: String, size: Int = 512): String? {
        val bitmap = generateQrCode(content, size) ?: return null
        return bitmapToBase64(bitmap)
    }
}


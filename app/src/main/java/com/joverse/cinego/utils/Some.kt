package com.joverse.cinego.utils

import android.graphics.Bitmap
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import kotlin.random.Random


fun addDurationToTime(time: String?, duration: Int): String {
    val localTime = LocalTime.parse(time)
    val updatedTime = localTime.plusMinutes(duration.toLong())
    return "$updatedTime"
}

fun formatDate(dateStr: String?): String {
    val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val date = LocalDate.parse(dateStr, inputFormatter)
    val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return "$dayOfWeek, ${date.format(outputFormatter)}"
}

fun generateQRCode(data: String, imageView: ImageView) {
    try {
        val writer = QRCodeWriter()
        val bitMatrix: BitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 500, 500)

        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }

        imageView.setImageBitmap(bitmap)

    } catch (e: WriterException) {
        e.printStackTrace()
    }
}

fun generateRandomCode(): String {
    val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val randomCode = StringBuilder()

    for (i in 1..8) {
        val randomChar = characters[Random.nextInt(characters.length)]
        randomCode.append(randomChar)
    }

    return randomCode.toString().uppercase()
}
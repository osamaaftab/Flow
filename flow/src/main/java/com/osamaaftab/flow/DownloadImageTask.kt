package com.osamaaftab.flow

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection


class DownloadImageTask(
    private val context: Context,
    private val url: String,
    private val imageView: com.osamaaftab.flow.CircularImageView,
    private val cache: com.osamaaftab.flow.CacheRepository
) : DownloadTask<Bitmap?>() {

    override fun download(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val increment: Int
            val data: ByteArray
            var ist: InputStream? = null
            val response: Int
            val url = URL(url)
            val conn: URLConnection = url.openConnection() as? HttpURLConnection
                ?: throw IOException("Not an HTTP connection")
            try {
                val httpConn = conn as HttpURLConnection
                httpConn.instanceFollowRedirects = true
                httpConn.requestMethod = "GET"
                httpConn.connect()
                response = httpConn.responseCode
                if (response == HttpURLConnection.HTTP_OK) {
                    ist = httpConn.inputStream
                }
                val length = httpConn.contentLength
                data = ByteArray(length)
                increment = length / 100
                val outStream = ByteArrayOutputStream()
                var count = -1
                var progress = 0
                while (ist!!.read(data, 0, increment).also { count = it } != -1) {
                    progress += count
                    updateProgress(imageView, ((progress * 100) / length))
                    Log.d("progress", ((progress * 100) / length).toString())
                    outStream.write(data, 0, count)
                }
                bitmap = BitmapFactory.decodeByteArray(
                    outStream.toByteArray(), 0, data.size
                )
                outStream.flush();
                outStream.close()
                ist.close()
                conn.disconnect()
            } catch (ex: Exception) {
                Log.d("Networking", ex.localizedMessage)
                throw IOException("Error connecting")
            }
        } catch (e: Exception) {
            Log.e("Error: ", e.message)
        }
        return bitmap
    }


    private val uiHandler = Handler(Looper.getMainLooper())

    override fun call(): Bitmap? {
        val bitmap = download(url)
        bitmap?.let {
            if (imageView.tag == url) {
                updateImageView(imageView, it)
            }
            cache.put(url, it)
        }
        return bitmap
    }

    private fun updateProgress(imageView: com.osamaaftab.flow.CircularImageView, progress: Int) {
        uiHandler.post {
            imageView.setValue(progress.toFloat())
        }
    }

    private fun updateImageView(imageView: com.osamaaftab.flow.CircularImageView, bitmap: Bitmap) {
        uiHandler.post {
            imageView.setImageBitmap(bitmap)
        }
    }
}
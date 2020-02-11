package com.osamaaftab.flow

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

import java.util.concurrent.Executors
import java.util.concurrent.Future


class Flow private constructor(val context: Context, cacheSize: Int) {
    private val cache =
        CacheRepository(context, cacheSize)
    private val executorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    private val mRunningDownloadList: HashMap<String, Future<Bitmap?>> = hashMapOf()


    fun displayImage(
        url: String,  imageview: CircularImageView, placeholder:
        Int
    ) {
        val bitmap = cache.get(url)
        bitmap?.let {
            imageview.setImageBitmap(bitmap)
            return
        }
            ?: run {
                imageview.tag = url
                if (placeholder != null) {
                    val bitmapDrawable = getBitmapFromVectorDrawable(context, placeholder)
                    imageview.setImageBitmap(bitmapDrawable)
                }
                addDownloadImageTask(url,
                    DownloadImageTask(
                        context,
                        url,
                        imageview,
                        cache
                    )
                )
            }
    }

    private fun getBitmapFromVectorDrawable(
        context: Context?,
        drawableId: Int
    ): Bitmap? {
        var drawable =
            ContextCompat.getDrawable(context!!, drawableId)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable!!).mutate()
        }
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
    }

    private fun addDownloadImageTask(url: String, downloadTask: DownloadTask<Bitmap?>) {

        mRunningDownloadList.put(url, executorService.submit(downloadTask))
    }


    fun clearcache() {
        cache.clear()
    }

    fun cancelTask(url: String) {
        synchronized(this) {
            mRunningDownloadList.forEach {
                if (it.key == url && !it.value.isDone)
                    it.value.cancel(true)
            }
        }
    }

    fun cancelAll() {
        synchronized(this) {
            mRunningDownloadList.forEach {
                if (!it.value.isDone)
                    it.value.cancel(true)
            }
            mRunningDownloadList.clear()
        }
    }


    companion object {
        private val INSTANCE: Flow? = null
        @Synchronized
        fun getInstance(context: Context, cacheSize: Int = Config.defaultCacheSize): Flow {
            return INSTANCE?.let { return INSTANCE }
                ?: run {
                    return Flow(context, cacheSize)
                }
        }
    }
}
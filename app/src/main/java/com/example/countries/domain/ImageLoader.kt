package com.example.countries.domain

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class ImageLoader @Inject constructor(
    val context: Context
) {

    private fun loadImageAndReturnFileName(url: String): Single<String> =
        Single.create { emitter ->
            val futureTarget =
                Glide.with(context).load(url)
                    .listener(listener)
                    .downloadOnly(1000, 800)
            val name = url.getFileName()

            createFile(futureTarget.get(), name)

            emitter.onSuccess(name)
        }

    private fun createFile(file: File, name: String) =
        context.openFileOutput(name, Context.MODE_PRIVATE).use { stream ->
            stream.write(file.readBytes())
        }

    private fun String.getFileName(): String =
            substring(lastIndexOf("/" + 1))

    private val listener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }


    }

}
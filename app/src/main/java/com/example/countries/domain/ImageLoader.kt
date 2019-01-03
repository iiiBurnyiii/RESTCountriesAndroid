package com.example.countries.domain

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toCompletable
import io.reactivex.schedulers.Schedulers
import java.io.File

class ImageLoader(
    private val urlList: List<String>,
    private val context: Context
) {

    fun start(
        doSomeOnNext: (Int) -> Unit,
        doSomeOnComplete: () -> Unit,
        doSomeOnError: (Throwable) -> Unit): Disposable =
        loadImageAndEmitItemPosition()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onNext = { doSomeOnNext(it) },
                onComplete = { doSomeOnComplete() },
                onError = { doSomeOnError(it) }
            )

    fun clear(
        doSomeOnComplete: () -> Unit,
        doSomeOnError: (Throwable) -> Unit): Disposable =
        removeFlagFiles()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onComplete = { doSomeOnComplete() },
                onError = { doSomeOnError(it) }
            )

    private fun loadImageAndEmitItemPosition() =
        Observable.create<Int> { emitter ->
            var itemPosition = 0
            urlList.forEach { url ->
                val futureTarget: FutureTarget<File> =
                    Glide.with(context)
                        .asFile()
                        .load(url)
                        .submit()
                val name = url.substring(url.lastIndexOf("/") + 1)

                createFile(futureTarget.get(), name)

                emitter.onNext(itemPosition++)

                if (itemPosition == urlList.size - 1) {
                    emitter.onComplete()
                }
            }
        }

    private fun removeFlagFiles() = Action {
        urlList.forEach { flagName ->
            deleteFile(flagName)
        }
    }.toCompletable()

    private fun deleteFile(name: String) {
        val fileList = context.fileList()
        if (name in fileList) {
            context.deleteFile(name)
        }
    }

    private fun createFile(file: File, name: String) =
        context.openFileOutput(name, Context.MODE_PRIVATE).use { stream ->
            stream.write(file.readBytes())
        }






}
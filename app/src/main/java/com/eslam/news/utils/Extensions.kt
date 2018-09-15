package com.eslam.news.utils

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.uiSubscribe() =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


fun <T> Observable<T>.ioSubscribe() =
    subscribeOn(Schedulers.io()).observeOn(Schedulers.io())

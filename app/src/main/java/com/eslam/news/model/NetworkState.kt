package com.eslam.news.model

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(val status: Status, val msg: String? = null) {
    enum class Status { LOADING, ERROR, SUCCESS }
    companion object {
        fun loading() = NetworkState(Status.LOADING)
        fun success() = NetworkState(Status.SUCCESS)
        fun error(msg: String) = NetworkState(Status.ERROR, msg)
    }
}

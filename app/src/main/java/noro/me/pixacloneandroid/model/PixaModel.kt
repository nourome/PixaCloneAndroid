package noro.me.pixacloneandroid.model

import java.io.Serializable


enum class ResponseStatus {
    Start,
    Success,
    Cached,
    Failed
}

data class PixaResponse(
        val total: Int,
        val totalHits: Int,
        val hits: ArrayList<PixaPhotoModel>?
)


data class PixaPhotoModel(
        val id: Int,
        val tags: String?,
        val previewURL: String?,
        val previewWidth: Int,
        val previewHeight: Int,
        val webformatURL: String?,
        val webformatWidth: Int,
        val webformatHeight: Int

): Serializable

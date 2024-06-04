package com.chris.api.data

data class Image(private val extension: String, private val rawUrl: String) {
    enum class ImageVariants(val key: String) {
        PORTRAIT_SMALL("portrait_small"),
        PORTRAIT_MEDIUM("portrait_medium"),
        PORTRAIT_XLARGE("portrait_xlarge"),
        PORTRAIT_FANTASTIC("portrait_fantastic"),
        PORTRAIT_UNCANNY("portrait_uncanny"),
        PORTRAIT_INCREDIBLE("portrait_incredible")
        // TODO: Add other types eventually if this was to be used in production.
    }

    fun getUrl(imageVariant: ImageVariants): String {
        return rawUrl + "/" + imageVariant.key + "." + extension
    }
}

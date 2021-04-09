package com.abhishek101.core.utils

enum class ImageSize {
    t_720p,
    t_1080p
}

enum class ImageType {
    JPEG,
    PNG
}

@OptIn(ExperimentalStdlibApi::class)
fun buildImageString(
    imageId: String,
    imageSize: ImageSize = ImageSize.t_720p,
    imageType: ImageType = ImageType.PNG
) = "https://images.igdb.com/igdb/image/upload/${imageSize.name}/$imageId.${imageType.name.lowercase()}"
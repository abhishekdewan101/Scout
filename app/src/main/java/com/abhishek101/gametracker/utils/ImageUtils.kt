package com.abhishek101.gametracker.utils

enum class ImageSize {
    t_720p,
    t_1080p
}

enum class ImageType {
    JPEG,
    PNG
}

@OptIn(ExperimentalStdlibApi::class)
fun buildImageString(imageId: String, imageSize: ImageSize, imageType: ImageType) =
    "https://images.igdb.com/igdb/image/upload/${imageSize.name}/$imageId.${imageType.name.lowercase()}"

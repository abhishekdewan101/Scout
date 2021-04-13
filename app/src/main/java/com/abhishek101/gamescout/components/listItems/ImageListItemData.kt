package com.abhishek101.gamescout.components.listItems

import com.abhishek101.core.db.Platform

data class ImageListItemData(val title: String, val imageUrl: String)

fun Platform.toImageListItemData(): ImageListItemData {
    return ImageListItemData(name, imageId)
}

val previewCircularImageListItemData = ImageListItemData("Preview Title", "Url")

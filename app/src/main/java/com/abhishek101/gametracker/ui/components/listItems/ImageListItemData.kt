package com.abhishek101.gametracker.ui.components.listItems

import com.abhishek101.core.db.Platform

data class ImageListItemData(val title: String, val imageUrl: String)

fun Platform.toImageListItemData(): ImageListItemData {
    return ImageListItemData(name, logoUrl)
}

val previewCircularImageListItemData = ImageListItemData("Preview Title", "Url")

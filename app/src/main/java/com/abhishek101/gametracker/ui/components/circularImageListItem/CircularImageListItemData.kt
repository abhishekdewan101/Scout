package com.abhishek101.gametracker.ui.components.circularImageListItem

import com.abhishek101.core.db.Platform

data class CircularImageListItemData(val title: String, val imageUrl: String)

fun Platform.toCircularImageListItemData(): CircularImageListItemData {
    return CircularImageListItemData(name, logoUrl)
}

val previewCircularImageListItemData = CircularImageListItemData("Preview Title", "Url")

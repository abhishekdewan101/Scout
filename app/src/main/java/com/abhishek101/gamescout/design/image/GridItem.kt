package com.abhishek101.gamescout.design.image

import com.abhishek101.core.db.LibraryGame
import com.abhishek101.core.models.IgdbGame

data class GridItem(val slug: String, val name: String, val coverUrl: String)

fun IgdbGame.toGridItem() = GridItem(slug = slug, name = name, coverUrl = cover!!.qualifiedUrl)
fun LibraryGame.toGridItem() = GridItem(slug = slug, name = name, coverUrl = coverUrl)

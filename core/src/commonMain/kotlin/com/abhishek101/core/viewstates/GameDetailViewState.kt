package com.abhishek101.core.viewstates

import com.abhishek101.core.models.IgdbGameDetail

data class GameDetailViewState(
    val slug: String,
    val name: String,
    val cover: String,
    val developer: Map<String, String>?,
    val rating: Float?,
    val releaseDate: String,
    val platforms: List<Pair<String, String>>?,
    val excerpt: String?,
    val images: List<String>,
    val videos: List<Triple<String, String, String>>,
    val similarGames: List<Pair<String, String>>?,
    val dlcs: List<Pair<String, String>>?
)

fun IgdbGameDetail.toViewState(): GameDetailViewState {
    val developer = involvedCompanies?.find { it.developer }?.company
    val platforms = platform?.map { Pair(it.slug, it.name) }?.toList()
    val combinedExcerpt = if (summary != null && storyline != null) {
        summary + "\n" + storyline
    } else {
        summary ?: storyline
    }
    val imageList = mutableListOf<String>()
    screenShots?.let { list -> imageList.addAll(list.map { it.qualifiedUrl }) }
    artworks?.let { list -> imageList.addAll(list.map { it.qualifiedUrl }) }

    val videoList = mutableListOf<Triple<String, String, String>>()
    videos?.let { list ->
        videoList.addAll(list.map {
            Triple(
                it.name,
                it.screenShotUrl,
                it.youtubeUrl
            )
        })
    }

    val similarGamesList =
        similarGames?.filter { it.cover != null }?.map { Pair(it.slug, it.cover!!.qualifiedUrl) }

    val dlcsList = dlc?.filter { it.cover != null }?.map { Pair(it.slug, it.cover!!.qualifiedUrl) }
    
    return GameDetailViewState(
        slug = slug,
        name = name,
        cover = cover!!.qualifiedUrl, //we shouldn't be able to get to the detail page of a game without a cover.
        developer = developer?.let { mapOf(developer.slug to developer.name) },
        rating = totalRating,
        releaseDate = humanReadableFirstReleaseDate,
        platforms = platforms,
        excerpt = combinedExcerpt,
        images = imageList,
        videos = videoList,
        similarGames = similarGamesList,
        dlcs = dlcsList
    )
}
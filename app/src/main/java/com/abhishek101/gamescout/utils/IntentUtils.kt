package com.abhishek101.gamescout.utils

import android.content.Intent
import android.net.Uri

fun buildYoutubeIntent(youtubeUrl: String): Intent {
    return Intent(
        Intent.ACTION_VIEW,
        Uri.parse(youtubeUrl)
    )
}

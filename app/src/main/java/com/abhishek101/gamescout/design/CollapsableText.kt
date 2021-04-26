package com.abhishek101.gamescout.design

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun CollapsableText(data: String, maxLines: Int = 5, fontSize: Int) {
    val collapsedState = remember { mutableStateOf(true) }
    val needsCollapsing = remember { mutableStateOf(false) }
    Row(verticalAlignment = if (collapsedState.value) Alignment.Top else Alignment.Bottom) {
        Text(
            data,
            color = Color.White,
            fontSize = fontSize.sp,
            maxLines = if (collapsedState.value) maxLines else 100,
            modifier = Modifier.weight(8f),
            overflow = TextOverflow.Ellipsis,
            onTextLayout = {
                if (collapsedState.value) {
                    needsCollapsing.value = it.hasVisualOverflow
                }
            }
        )
        if (needsCollapsing.value) {
            if (collapsedState.value) {
                Icon(Icons.Outlined.ExpandMore, "", tint = Color.White, modifier = Modifier
                    .weight(1f)
                    .clickable { collapsedState.value = false })
            } else {
                Icon(Icons.Outlined.ExpandLess, "", tint = Color.White, modifier = Modifier
                    .weight(1f)
                    .clickable { collapsedState.value = true })
            }
        }
    }
}

@Preview
@Composable
fun PreviewCollapsableText() {
    CollapsableText(
        data = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque sagittis felis maximus elit interdum ultrices. Nullam at risus id tellus laoreet pretium. Aenean eget elementum lectus, vel elementum tortor. Vivamus in leo sapien. Nulla at imperdiet elit. Proin et dictum ante. Donec faucibus ullamcorper pellentesque. Etiam ut lacus vel justo auctor tincidunt sit amet egestas nunc. Nulla rutrum est in odio venenatis euismod. Donec consectetur congue suscipit. Integer imperdiet lacus sed mauris molestie porta. Nulla rhoncus pretium neque in varius. Sed interdum metus nec odio tristique, et imperdiet nibh porttitor. Proin ut hendrerit mi. Pellentesque elementum lorem nunc, eget tristique justo accumsan sed.\n" +
            "\n" +
            "Fusce fringilla nisl dui, nec egestas velit semper at. Morbi feugiat risus non laoreet convallis. In nec posuere orci, non porttitor nulla. Pellentesque justo ipsum, porttitor ac leo sed, placerat iaculis metus. In feugiat erat sed ipsum dapibus, id scelerisque libero dapibus. Nulla et auctor mauris. Quisque tempor nisl non urna pharetra faucibus. Fusce sed nisi et nibh mattis luctus ac ac turpis. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.\n" +
            "\n" +
            "Cras vel ornare orci. Nam est metus, rhoncus in neque vitae, hendrerit suscipit est. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nulla pharetra tellus vel nisi gravida, non condimentum leo interdum. Pellentesque eget nisi elit. Suspendisse mattis neque eu lorem commodo, vitae posuere enim ornare. Nunc gravida vulputate arcu, at lobortis sapien vestibulum eget. Fusce sollicitudin semper viverra. In ut mi tempor purus consequat maximus at eu ex. Fusce eros diam, pretium in dolor ac, porttitor posuere orci. Morbi in justo aliquam, consequat quam ut, vulputate dui.\n" +
            "\n" +
            "Aliquam nec libero mattis, interdum metus quis, imperdiet purus. Maecenas mattis a lorem ut suscipit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aliquam consequat dignissim velit, non dictum enim. Sed a purus non libero posuere mollis nec ac risus. Nunc ultricies, ligula vel tempor feugiat, nisl turpis euismod augue, rhoncus hendrerit dui orci eget orci. In hac habitasse platea dictumst. Phasellus quis erat vitae leo laoreet vulputate vel vitae felis. In mattis fermentum nisi, in rutrum tortor dignissim nec. Nullam pellentesque nec lectus non semper. Curabitur condimentum, tellus eu laoreet convallis, lorem ex pulvinar sem, vitae consectetur ligula felis non elit. Phasellus sit amet neque condimentum, pellentesque turpis vel, eleifend tellus. Duis fermentum imperdiet lectus vitae laoreet. Vestibulum pretium ornare massa, vel finibus nisi interdum in.\n" +
            "\n" +
            "Etiam ornare massa sed turpis ultrices mattis. Nunc aliquet neque nisl, et viverra ante feugiat ornare. Maecenas in lectus mattis elit elementum convallis. Phasellus euismod bibendum nibh, vitae imperdiet lacus pharetra quis. In dui ipsum, hendrerit quis tempus non, sagittis hendrerit massa. Nullam molestie quam ut pharetra vulputate. Nunc laoreet risus arcu, ac sodales sapien hendrerit eu. Sed urna orci, placerat vel efficitur et, interdum at lacus. Integer sodales libero eget felis tempus, vitae imperdiet odio tincidunt. Mauris suscipit accumsan auctor. Ut augue sapien, dapibus vitae congue vitae, facilisis imperdiet elit.",
        5, 16
    )
}
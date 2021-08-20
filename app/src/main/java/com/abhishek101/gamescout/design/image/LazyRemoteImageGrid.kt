import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abhishek101.gamescout.design.image.GridItem
import com.abhishek101.gamescout.design.image.RemoteImage

@Composable
fun LazyRemoteImageGrid(
    data: List<GridItem>,
    columns: Int,
    preferredWidth: Dp,
    preferredHeight: Dp,
    modifier: Modifier = Modifier,
    addBottomPadding: Boolean = true,
    bottomPadding: Dp = 72.dp,
    onTap: (String) -> Unit
) {
    val rows = data.chunked(columns)
    LazyColumn(modifier = modifier) {
        rows.forEachIndexed { index, row ->
            item {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = if (index == rows.size - 1) 0.dp else 5.dp)
                ) {
                    row.forEachIndexed { index, game ->
                        Box(
                            modifier = Modifier
                                .width(preferredWidth)
                                .height(preferredHeight)
                                .padding(end = if (index == row.size - 1) 0.dp else 5.dp)
                                .clickable { onTap(game.slug) }
                        ) {
                            RemoteImage(
                                data = game.coverUrl,
                                contentDescription = game.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.medium)
                            )
                        }
                    }
                }
            }
        }
        if (addBottomPadding) {
            item {
                Spacer(modifier = Modifier.height(bottomPadding))
            }
        }
    }
}

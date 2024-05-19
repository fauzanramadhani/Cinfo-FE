package com.ndc.core.ui.component.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ndc.core.R
import com.ndc.core.ui.theme.CinfoTheme
import com.ndc.core.utils.limitLength

@Composable
fun RoomItem(
    modifier: Modifier = Modifier,
    title: String = "",
    additional: String = "",
    backgroundImage: Int = R.drawable.background_1,
    onClick: () -> Unit = {}
) {
    val typography = MaterialTheme.typography
    val color = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .height(111.dp)
    ) {
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.5f))
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text(
                text = title.limitLength(70),
                style = typography.titleMedium,
                color = color.background
            )
            Text(
                text = additional,
                style = typography.bodySmall,
                color = color.background
            )
        }
    }
}

@Preview
@Composable
fun RoomItemPreview() {
    CinfoTheme {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 48.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(5) {
                RoomItem(
                    title = "Announcement $it",
                    additional = "202$it Generation"
                )
            }
        }
    }
}
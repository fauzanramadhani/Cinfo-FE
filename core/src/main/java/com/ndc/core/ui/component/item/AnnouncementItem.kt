package com.ndc.core.ui.component.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ndc.core.utils.limitLength
import com.ndc.core.R

@Composable
fun AnnouncementItem(
    modifier: Modifier = Modifier,
    title: String = "",
    createdAt: String = "",
    onClick: () -> Unit = {}
) {
    val typography = MaterialTheme.typography
    val color = MaterialTheme.colorScheme

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 0.5.dp,
                color = color.outline,
                shape = RoundedCornerShape(8.dp)
            )
            .background(color.surface)
            .fillMaxWidth()
            .height(92.dp)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.9f)
        ) {
            Text(
                text = title.limitLength(70),
                style = typography.titleSmall,
                color = color.onSurface
            )
            Text(
                text = createdAt,
                style = typography.bodySmall,
                color = color.secondary
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right_secondary),
            contentDescription = "",
            tint = color.secondary,
            modifier = Modifier
                .weight(0.1f)
                .size(24.dp)
        )
    }
}
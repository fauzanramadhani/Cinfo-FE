package com.ndc.core.ui.component.item

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import com.ndc.core.R

@Composable
fun MemberItem(
    modifier: Modifier = Modifier,
    memberEmail: String,
    onClick: () -> Unit = {}
) {
    val typography = MaterialTheme.typography
    val color = MaterialTheme.colorScheme

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                width = 0.5.dp,
                shape = RoundedCornerShape(8.dp),
                color = color.outline
            )
            .padding(12.dp)
    ) {
        Text(
            text = memberEmail,
            style = typography.bodyMedium,
            color = color.onBackground
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_dots_vertical),
            contentDescription = "",
            tint = color.primary,
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = onClick)
        )
    }
}
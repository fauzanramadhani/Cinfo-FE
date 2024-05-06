package com.ndc.cinfo.ui.screen.detail_announcement

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.ndc.core.R
import com.ndc.cinfo.utils.toDateString

@Composable
fun DetailAnnouncementScreen(
    navHostController: NavHostController
) {
    val ctx = LocalContext.current
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val darkTheme: Boolean = isSystemInDarkTheme()
    val view = LocalView.current
    val window = (view.context as Activity).window

    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color.background)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(12.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            contentDescription = "",
            tint = color.primary,
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    navHostController.navigateUp()
                }
        )
        Spacer(modifier = Modifier.padding(top = 24.dp))
        Text(
            text = "Pemberitahuan Jadwal Pelaksanaan UAS Semester 6",
            style = typography.titleLarge,
            color = color.onBackground
        )
        Spacer(modifier = Modifier.padding(top = 12.dp))
        Text(
            text = "Diterbitkan pada ${System.currentTimeMillis().toDateString("dd MMMM yyyy")}",
            style = typography.bodyMedium,
            color = color.secondary
        )
        Spacer(modifier = Modifier.padding(top = 24.dp))
        Divider(
            thickness = 1.dp,
            color = color.outline
        )
        Spacer(modifier = Modifier.padding(top = 24.dp))
        Text(
            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            style = typography.bodyMedium,
            color = color.onBackground
        )
    }
}
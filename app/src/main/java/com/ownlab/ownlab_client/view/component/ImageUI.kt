package com.ownlab.ownlab_client.view.component

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ownlab.ownlab_client.R
import java.io.File

@Composable
fun CenteredImageSelector(fileState: MutableState<File?>) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val file = File(context.cacheDir, context.contentResolver.getFileName(uri))
            context.contentResolver.openInputStream(uri)?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            fileState.value = file
        }
    }

    val painter = if (fileState.value != null) {
        rememberAsyncImagePainter(fileState.value)
    } else {
        painterResource(R.drawable.icon_resume_image_upload)
    }

    CenteredImage(
        painter = painter,
        contentDescription = "resume image upload",
        modifier = Modifier.clickable { launcher.launch("image/*") },
        isCircular = fileState.value != null
    )
}

// 안드로이드에서 ContentResolver를 사용하여 주어진 Uri의 파일 이름을 가져오는 함수
fun ContentResolver.getFileName(uri: Uri): String {
    var name = ""
    val returnCursor = query(uri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}


@Composable
fun CenteredImage(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    imageSize: Dp = 110.dp,
    isCircular: Boolean = false
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(148.dp)
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier
                .width(imageSize)
                .height(imageSize)
                .then(if (isCircular) Modifier.clip(CircleShape) else Modifier),
            contentScale = ContentScale.Crop
        )
    }
}
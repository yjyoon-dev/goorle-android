package dev.yjyoon.goorle.ui.post

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.HorizontalPagerIndicator
import dev.yjyoon.goorle.R
import dev.yjyoon.goorle.ui.component.GoorleComment
import dev.yjyoon.goorle.ui.model.FilterType
import dev.yjyoon.goorle.ui.model.Post
import dev.yjyoon.goorle.ui.theme.GoorleBlue
import dev.yjyoon.goorle.ui.theme.GoorleGray75
import dev.yjyoon.goorle.ui.theme.GoorleGray9E
import dev.yjyoon.goorle.ui.theme.GoorleGrayD9
import dev.yjyoon.goorle.ui.theme.GoorleGrayE0

@Composable
fun DetailScreen(
    post: Post,
    onBack: () -> Unit
) {
    val pagerState = rememberPagerState()
    val scrollState = rememberScrollState()
    var comment by remember { mutableStateOf("") }
    
    BackHandler {
        onBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3 / 2f)
                .clip(RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp))
        ) {
            HorizontalPager(
                pageCount = post.images.size,
                state = pagerState,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .aspectRatio(3 / 2f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(3 / 2f)
                ) {
                    Surface(
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                        color = GoorleGray9E,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(3 / 2f)
                    ) {}
                    AsyncImage(
                        model = post.images[it],
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(3 / 2f)
                    )
                }
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                pageCount = post.images.size,
                activeColor = Color.White,
                inactiveColor = GoorleGrayD9,
                spacing = 5.dp,
                indicatorWidth = 4.dp,
                indicatorHeight = 4.dp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 36.dp, horizontal = 16.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = null
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
            }
        }
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = post.title, style = MaterialTheme.typography.headlineSmall)
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                post.tags.forEach {
                    Text(
                        text = "#${stringResource(id = it.tagRes)}",
                        style = MaterialTheme.typography.labelMedium,
                        color = GoorleGray75
                    )
                }
            }
            Text(
                text = post.location,
                style = MaterialTheme.typography.titleMedium,
                color = GoorleGray75
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FilterType.values().take(4).forEach {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = it.iconRes),
                        contentDescription = null,
                        tint = if (it in post.filters) GoorleBlue else GoorleGray75,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(id = it.stringRes),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (it in post.filters) GoorleBlue else GoorleGray75
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FilterType.values().takeLast(4).forEach {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = it.iconRes),
                        contentDescription = null,
                        tint = if (it in post.filters) GoorleBlue else GoorleGray75,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(id = it.stringRes),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (it in post.filters) GoorleBlue else GoorleGray75
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            color = Color(0xFFECECF1)
        ) { }
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.comment, 4),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    value = comment,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    onValueChange = {},
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White,
                            border = BorderStroke(width = 1.dp, color = GoorleGrayE0)
                        ) {
                            Row(
                                modifier = Modifier.padding(
                                    horizontal = 16.dp,
                                    vertical = 8.dp
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(modifier = Modifier.weight(1f)) {
                                    if (comment.isEmpty()) {
                                        Text(
                                            stringResource(id = R.string.comment_placeholder),
                                            color = GoorleGray75,
                                            style = MaterialTheme.typography.bodyMedium,
                                            maxLines = 1
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = stringResource(id = R.string.post),
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            post.comments.forEach {
                GoorleComment(
                    modifier = Modifier.padding(bottom = 24.dp),
                    comment = it
                )
            }
        }
    }
}
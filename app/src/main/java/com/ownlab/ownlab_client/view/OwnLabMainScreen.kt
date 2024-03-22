package com.ownlab.ownlab_client.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.gray100
import com.ownlab.ownlab_client.gray200
import com.ownlab.ownlab_client.gray500
import com.ownlab.ownlab_client.gray600
import com.ownlab.ownlab_client.models.PostItem
import com.ownlab.ownlab_client.subTextColor
import com.ownlab.ownlab_client.textPrimaryColor
import kotlinx.coroutines.delay

class OwnLabMainScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                JobPortalMainUI()
            }
        }
    }
}

@Composable
fun InformationViewPager() {
    val images = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3
    )

    var currentPage by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000) // 3초마다 페이지 전환
            currentPage = (currentPage + 1) % images.size
        }
    }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(images) { index, image ->
                    ViewPagerItem(
                        image = image,
                        currentPage = currentPage,
                        index = index
                    )
                }
            }
            DotIndicator(
                modifier = Modifier.align(Alignment.BottomCenter),
                itemCount = images.size,
                currentPage = currentPage
            )
        }
    }
}

@Composable
fun ViewPagerItem(image: Int, currentPage: Int, index: Int) {
    if (currentPage == index) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun DotIndicator(
    modifier: Modifier = Modifier,
    itemCount: Int,
    currentPage: Int,
    indicatorSize: Dp = 8.dp,
    indicatorSpacing: Dp = 8.dp,
    indicatorShape: RoundedCornerShape = CircleShape,
    indicatorColor: androidx.compose.ui.graphics.Color = gray100,
    selectedIndicatorColor: androidx.compose.ui.graphics.Color = textPrimaryColor
) {
    Row(
        modifier = modifier.padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(indicatorSpacing)
    ) {
        repeat(itemCount) { index ->
            val color = if (index == currentPage) selectedIndicatorColor else indicatorColor
            Box(
                modifier = Modifier
                    .size(indicatorSize)
                    .background(
                        color = color,
                        shape = indicatorShape
                    )
                    .padding(horizontal = indicatorSpacing / 2)
            )
        }
    }
}

@Composable
fun ImageList() {
    val modifier = Modifier
        .padding(all = 20.dp)
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        ImageWithText(
            imageRes = R.drawable.icon_quick_01,
            text = "맞춤",
            modifier = modifier
                .weight(1f)
        )
        ImageWithText(
            imageRes = R.drawable.icon_quick_02,
            text = "지역",
            modifier = modifier
                .weight(1f)
        )
        ImageWithText(
            imageRes = R.drawable.icon_quick_03,
            text = "지도",
            modifier = modifier
                .weight(1f)
        )
        ImageWithText(
            imageRes = R.drawable.icon_quick_04,
            text = "단기",
            modifier = modifier
                .weight(1f)
        )
        ImageWithText(
            imageRes = R.drawable.icon_quick_05,
            text = "급구",
            modifier = modifier
                .weight(1f)
        )
    }
}


@Composable
fun ImageWithText(
    imageRes: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.aspectRatio(1f)
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp),
            color = subTextColor,
            fontSize = 10.sp
        )
    }
}


@Composable
fun LocalSelection() {
    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent, shape = RoundedCornerShape(10.dp))
                .border(border = BorderStroke(1.dp, gray200), shape = RoundedCornerShape(10.dp))
                .padding(12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.icon_map_mark),
                    contentDescription = "map icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("지역", color = textPrimaryColor)
                Image(
                    painter = painterResource(id = R.drawable.icon_arrow_right),
                    contentDescription = "map icon",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("상세 지역을 선택해 주세요", color = subTextColor)
            }
        }
    }
}

@Composable
fun TitleText() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = "선택하신 지역에서\n가장 최근 등록된 공고예요!",
                color = textPrimaryColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun NoContentUI() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 120.dp, start = 16.dp, end = 16.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .width(60.dp)
                .height(60.dp),
            painter = painterResource(id = R.drawable.emoji_speech_balloon_empty),
            contentDescription = "등록된 내역이 없습니다."
        )
        Text(
            modifier = Modifier.padding(top = 26.dp),
            text = "등록된 내역이 없습니다.",
            color = subTextColor,
            fontSize = 14.sp,
        )
    }
}

@Composable
fun BoxContent(index: Int) {
    Text(text = "Box $index", modifier = Modifier.padding(8.dp))
}

@Composable
fun JobPostingBox() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
    ) {
        items(10) { index ->
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(187.dp)
                    .padding(end = 8.dp)
                    .background(color = Color.Transparent, shape = RoundedCornerShape(10.dp))
                    .border(BorderStroke(1.dp, gray200), shape = RoundedCornerShape(10.dp))
            ) {
                BoxContent(index = index)
            }
        }
    }
}

@Composable
fun CompanyDetailsFooter() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "(주)온랩",
                color = subTextColor,
                fontSize = 12.sp,
            )
            Spacer(modifier = Modifier.padding(start = 8.dp))
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.icon_arrow_down),
                contentDescription = "등록된 내역이 없습니다.",
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "이용약관",
                color = gray100,
                fontSize = 12.sp,
            )
            Spacer(modifier = Modifier.padding(start = 16.dp))
            Text(
                text = "개인정보처리방침",
                color = gray100,
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
fun CompanyDetailsFooterBoxText(keyTitle: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 7.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        {
            Text(
                text = keyTitle,
                color = gray600,
                fontSize = 12.sp,
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        ) {
            Text(
                text = value,
                color = gray600,
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
fun CompanyDetailsFooterBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(165.dp)
            .padding(start = 16.dp, end = 16.dp, bottom = 30.dp)
            .background(
                color = gray500, shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column(modifier = Modifier.padding(top = 8.dp)) {
            CompanyDetailsFooterBoxText(keyTitle = "대표자명", value = "조용준")
            CompanyDetailsFooterBoxText(keyTitle = "주소", value = "대전시 유성구 동서대로 125(한밭인큐베이터타운 203호")
            CompanyDetailsFooterBoxText(keyTitle = "사업자등록번호", value = "371-87-03031")
            CompanyDetailsFooterBoxText(keyTitle = "개인정보보호 및 고객센터", value = "01044394000")
        }
    }
}


@Composable
fun JobPortalMainUI() {

    LazyColumn {
        item { InformationViewPager() }
        item { ImageList() }
        item { LocalSelection() }
        item { TitleText() }
        item { NoContentUI() }
        item { CompanyDetailsFooter() }
        item { CompanyDetailsFooterBox() }
    }
}

@Preview
@Composable
fun OwnLabMainScreenPreview() {
    JobPortalMainUI()
}
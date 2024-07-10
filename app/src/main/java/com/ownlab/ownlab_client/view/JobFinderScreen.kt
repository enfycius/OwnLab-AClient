package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ownlab.ownlab_client.blue
import com.ownlab.ownlab_client.view.component.ArrowLeftIcon
import com.ownlab.ownlab_client.view.component.BoldHeaderText
import com.ownlab.ownlab_client.view.component.BorderedText
import com.ownlab.ownlab_client.view.component.CancleIcon
import com.ownlab.ownlab_client.view.component.EmptyContentMessage
import com.ownlab.ownlab_client.view.component.HorizontalDivider
import com.ownlab.ownlab_client.view.component.SearchInputField
import com.ownlab.ownlab_client.viewmodels.SearchViewModel
import com.ownlab.ownlab_client.viewmodels.SearchViewModelFactory

class JobFinderScreen : Fragment() {
    private val viewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val navController = findNavController()
                JobFinderMain(navController = navController, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun RecentSearchesList(recentSearches: List<String>) {
    LazyRow(
        modifier = Modifier.padding(top=8.dp, bottom = 20.dp)
    ) {
        items(recentSearches) { search ->
            BorderedText(text = search)
        }
    }
}

@Composable
fun BorderedText(text: String) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White)
            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
    ) {
        Row(){
            Text(
                text = text,
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentWidth()
                    .wrapContentHeight(),
                color = Color.Black,
                fontSize = 12.sp
            )
            CancleIcon {

            }
        }

    }
}

@Composable
fun RecommendedSearches(recommendedSearches: List<String>) {
    Column() {

        // 추천 검색어들을 나란히 표시하는 Row
        var currentRowItems = mutableListOf<String>()
        recommendedSearches.forEachIndexed { index, search ->
            currentRowItems.add(search)
            // 한 줄에 최대 5개까지 표시하도록 설정
            if (currentRowItems.size == 5 || index == recommendedSearches.size - 1) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    currentRowItems.forEach { item ->
                        RecommendedSearchItem(item = item)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                currentRowItems.clear()
            }
        }
    }
}

@Composable
fun RecommendedSearchItem(item: String) {
    Box(
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(top= 12.dp)
            .border(1.dp, blue, RoundedCornerShape(16.dp))
    ) {
        Text(
            text = item,
            color = blue,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun JobFinderMain(
    color: Color = Color.Gray,
    navController: NavController,
    viewModel: SearchViewModel= androidx.lifecycle.viewmodel.compose.viewModel()
)  {
    val recentSearches by viewModel.recentSearches.observeAsState(emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier=Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            ArrowLeftIcon()
            Spacer(modifier = Modifier.padding(start = 4.dp))
            SearchInputField { query ->
                viewModel.saveSearchQuery(query)
            }
        }
        Box(modifier=Modifier.padding(top = 16.dp)){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BoldHeaderText(text = "최근 검색어")
                Spacer(modifier = Modifier.weight(1f))
                BorderedText(text = "전체삭제") {
                    
                }
            }
        }
        HorizontalDivider()

        if (recentSearches.isEmpty()) {
            EmptyContentMessage()
        } else {
            RecentSearchesList(recentSearches)
        }
        Spacer(modifier = Modifier.height(30.dp))
        BoldHeaderText(text = "추천 검색어")
        HorizontalDivider()
        val recommendedSearches = listOf("매장", "매장관리", "백화점", "옷가게", "의류판매", "마트캐셔", "백화점캐셔", "카운터")
        RecommendedSearches(recommendedSearches = recommendedSearches)
    }
}

@Preview
@Composable
fun JobFinderScreenPreview() {
    JobFinderMain(navController = NavController(LocalContext.current))
}
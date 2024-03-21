package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.FragmentBoardBinding
import com.ownlab.ownlab_client.databinding.RecyclerviewPostItemBinding
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.view.adapter.BoardAdapter
import com.ownlab.ownlab_client.view.adapter.MainAdapter
import com.ownlab.ownlab_client.viewmodels.BoardViewModel
import com.ownlab.ownlab_client.viewmodels.TokenViewModel
import com.ownlab.ownlab_client.viewmodels.`interface`.CoroutinesErrorHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class BoardFragment : Fragment() {
    private var _binding: FragmentBoardBinding? = null
    private val binding get() = _binding!!

    private val tokenViewModel: TokenViewModel by activityViewModels()
    private val boardViewModel: BoardViewModel by viewModels()

    private lateinit var navController: NavController
    private var token: String? = null

    private var boardAdapter: BoardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBoardBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.seekerRadioButton -> {
                    // 채용공고 라디오 버튼 선택 시
                    // 채용공고에 해당하는 화면을 보여줌
                    // 예를 들어 다른 프래그먼트로 이동하거나 다른 레이아웃을 inflate할 수 있음
                }

                R.id.companyRadioButton -> {
                    binding.recyclerView.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                }
            }
        }
        binding.fab.setOnClickListener {
            navController.navigate(R.id.board_2_board_register)
        }
        return binding.root
    }

    private fun showCompanyView() {
        // 지원자에 해당하는 다른 화면을 보여주는 코드를 작성
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        CoroutineScope(Dispatchers.Main).launch {
            boardViewModel.getPostItems(token, object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    if (message.contains("IllegalStateException")) {
                        Log.d("Report", "데이터 없음"); return; }

                    try {
                        val action = BoardFragmentDirections.board2ChkDialog("네트워크 연결을 확인해주세요.")
                        navController.navigate(action)
                    } catch (e: IllegalArgumentException) {
                    }
                }
            })
        }

        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
            this.token = token

            if (token == null) {
                try {
                    navController.navigate(R.id.main_2_login)
                } catch (e: IllegalArgumentException) {
                }
            }
        }

        boardViewModel.postItemResponse.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Failure -> {
                    try {
                        val action = BoardFragmentDirections.board2ChkDialog("네트워크 연결을 확인해주세요.")
                        navController.navigate(action)
                    } catch (e: IllegalArgumentException) {
                    }
                }

                is ApiResponse.Success -> {
                    boardAdapter = BoardAdapter(it.data.postItems); binding.recyclerView.adapter =
                        boardAdapter
                }

                else -> {}
            }
        }
    }
}
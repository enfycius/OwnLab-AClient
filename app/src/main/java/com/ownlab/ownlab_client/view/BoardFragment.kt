package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.FragmentBoardBinding
import com.ownlab.ownlab_client.models.ApplyPostRequest
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.view.adapter.BoardAdapter
import com.ownlab.ownlab_client.view.interfaces.OnItemClick
import com.ownlab.ownlab_client.viewmodels.BoardViewModel
import com.ownlab.ownlab_client.viewmodels.TokenViewModel
import com.ownlab.ownlab_client.viewmodels.interfaces.CoroutinesErrorHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BoardFragment : Fragment(), OnItemClick {
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
                }

                R.id.companyRadioButton -> {
                    navController.navigate(R.id.action_boardFragment_to_applicantManagementFragment2)

                }
            }
        }

        binding.fab.setOnClickListener {
            navController.navigate(R.id.board_2_board_register)
        }

        return binding.root
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

        boardViewModel.registerResponse.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Failure -> {
                    try {
                        val action = BoardFragmentDirections.board2ChkDialog("네트워크 연결을 확인해주세요.")
                        navController.navigate(action)
                    } catch (e: IllegalArgumentException) {
                    }
                }

                is ApiResponse.Success -> {
                    Log.d("Test", it.data.message)
                    if (it.data.message.contains("Already applied")) {
                        try {
                            val action = BoardFragmentDirections.board2ChkDialog("이미 지원하셨습니다.")
                            navController.navigate(action)
                        } catch (e: IllegalArgumentException) {
                        }
                    } else if (it.data.message.contains("Already fulfilled")) {
                        try {
                            val action = BoardFragmentDirections.board2ChkDialog("모집인원이 모두 찼습니다.")
                            navController.navigate(action)
                        } catch (e: IllegalArgumentException) {
                        }
                    } else if (it.data.message.contains("success")) {
                        try {
                            val action = BoardFragmentDirections.board2ChkDialog("지원 완료하였습니다.")
                            navController.navigate(action)
                        } catch (e: IllegalArgumentException) {
                        }
                    }

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
                    boardAdapter = BoardAdapter(context, it.data.postItems, this); binding.recyclerView.adapter =
                        boardAdapter
                }

                else -> {}
            }
        }
    }

    override fun applyInfo(id: Int, assignee: String) {
        val applyPostRequest = ApplyPostRequest(id, assignee)

        boardViewModel.applyPostItem(token, applyPostRequest, object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d("Report", message)

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
}
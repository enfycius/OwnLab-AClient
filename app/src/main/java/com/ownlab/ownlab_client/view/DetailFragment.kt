package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.FragmentDetailBinding
import com.ownlab.ownlab_client.models.ApplyPostRequest
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.viewmodels.BoardViewModel
import com.ownlab.ownlab_client.viewmodels.TokenViewModel
import com.ownlab.ownlab_client.viewmodels.interfaces.CoroutinesErrorHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()

    private val tokenViewModel: TokenViewModel by activityViewModels()
    private val boardViewModel: BoardViewModel by viewModels()

    private var token: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postItem = args.postItem

        binding.apply {
            title.text = postItem.title
            recruitDate.text = "${postItem.start_date} ~ ${postItem.end_date}"
            registrationDate.text = "${postItem.registration_date}"
            assignee.text = "${postItem.assignee}"
            contacts.text = "${postItem.contacts}"
            email.text = "${postItem.email}"
            registrationMethod.text = "${postItem.registration_method}"
            address.text = "${postItem.address}"
            detailedLink.text = "${postItem.detailed_link}"
            settingBtn.setOnClickListener{
                onApplyButtonClick(postItem.id, postItem.assignee)
            }
        }
        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
            this.token = token
            if (token == null) {
                try {
                    findNavController().navigate(R.id.main_2_login)
                } catch (e: IllegalArgumentException) {
                }
            }
        }

        boardViewModel.registerResponse.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Failure -> {
                    try {
                        val action = DetailFragmentDirections.actionDetailFragmentToChkDialogNav("네트워크 연결을 확인해주세요.")
                        findNavController().navigate(action)
                    } catch (e: IllegalArgumentException) {
                    }
                }

                is ApiResponse.Success -> {
                    Log.d("Test", it.data.message)
                    if (it.data.message.contains("Already applied")) {
                        try {
                            val action = DetailFragmentDirections.actionDetailFragmentToChkDialogNav("이미 지원하셨습니다.")
                            findNavController().navigate(action)
                        } catch (e: IllegalArgumentException) {
                        }
                    } else if (it.data.message.contains("Already fulfilled")) {
                        try {
                            val action = DetailFragmentDirections.actionDetailFragmentToChkDialogNav("모집인원이 모두 찼습니다.")
                            findNavController().navigate(action)
                        } catch (e: IllegalArgumentException) {
                        }
                    } else if (it.data.message.contains("success")) {
                        try {
                            val action = DetailFragmentDirections.actionDetailFragmentToChkDialogNav("지원 완료하였습니다.")
                            findNavController().navigate(action)
                        } catch (e: IllegalArgumentException) {
                        }
                    }
                }
            }
        }
    }

    private fun onApplyButtonClick(postId: Int, assignee: String) {
        applyInfo(postId, assignee)
    }

    private fun applyInfo(id: Int, assignee: String) {
        val applyPostRequest = ApplyPostRequest(id, assignee)
        boardViewModel.applyPostItem(token, applyPostRequest, object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d("Report", message)
                if (message.contains("IllegalStateException")) {
                    Log.d("Report", "데이터 없음")
                    return
                }
                try {
                    val action = DetailFragmentDirections.actionDetailFragmentToChkDialogNav("네트워크 연결을 확인해주세요.")
                    findNavController().navigate(action)
                } catch (e: IllegalArgumentException) {
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
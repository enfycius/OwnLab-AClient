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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.R.*
import com.ownlab.ownlab_client.databinding.FragmentApplicantManagementBinding
import com.ownlab.ownlab_client.models.ApplyPostRequest
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.view.ApplicantManagementFragmentDirections.Companion.applicant2ChkDialog
import com.ownlab.ownlab_client.view.adapter.ApplicantManagementAdapter
import com.ownlab.ownlab_client.view.adapter.BoardAdapter
import com.ownlab.ownlab_client.view.interfaces.OnItemClick
import com.ownlab.ownlab_client.viewmodels.ApplicantManagementViewModel
import com.ownlab.ownlab_client.viewmodels.TokenViewModel
import com.ownlab.ownlab_client.viewmodels.interfaces.CoroutinesErrorHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApplicantManagementFragment : Fragment() {
    private var _binding: FragmentApplicantManagementBinding? = null
    private val binding get() = _binding!!

    private val tokenViewModel: TokenViewModel by activityViewModels()
    private val applicantManagementViewModel: ApplicantManagementViewModel by viewModels()

    private lateinit var navController: NavController
    private var token: String? = null

    private var applicantManagementAdapter: ApplicantManagementAdapter? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApplicantManagementBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.seekerRadioButton -> {
                    navController.navigate(R.id.action_applicantManagementFragment2_to_boardFragment)
                }

                R.id.companyRadioButton -> {
                    navController.navigate(R.id.action_boardFragment_to_applicantManagementFragment2)
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        CoroutineScope(Dispatchers.Main).launch {
            applicantManagementViewModel.getApplicantInfo(token, object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    if (message.contains("IllegalStateException")) {
                        Log.d("Report", "데이터 없음"); return; }

                    try {
                        val action = ApplicantManagementFragmentDirections.applicant2ChkDialog("네트워크 연결을 확인해주세요.")
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

        applicantManagementViewModel.applicantInfoResponse.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Failure -> {
                    try {
                        val action = ApplicantManagementFragmentDirections.applicant2ChkDialog("네트워크 연결을 확인해주세요.")
                        navController.navigate(action)
                    } catch (e: IllegalArgumentException) {
                    }
                }

                is ApiResponse.Success -> {
                    Log.d("Test", it.data.applicantInfo.toString())
                    applicantManagementAdapter = ApplicantManagementAdapter(context, it.data.applicantInfo); binding.recyclerView.adapter =
                        applicantManagementAdapter

                    binding.wholeApplicant.text = it.data.applicantInfo.size.toString()
                }

                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
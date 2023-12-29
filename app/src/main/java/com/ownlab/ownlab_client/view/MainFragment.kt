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
import com.ownlab.ownlab_client.databinding.FragmentMainBinding
import com.ownlab.ownlab_client.models.RadarData
import com.ownlab.ownlab_client.models.RadarResponse
import com.ownlab.ownlab_client.models.RadarType
import com.ownlab.ownlab_client.models.SurveyResultRequest
import com.ownlab.ownlab_client.models.findEnumByKey
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.view.adapter.MainAdapter
import com.ownlab.ownlab_client.viewmodels.MainViewModel
import com.ownlab.ownlab_client.viewmodels.TokenViewModel
import com.ownlab.ownlab_client.viewmodels.`interface`.CoroutinesErrorHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.reflect.full.memberProperties

@AndroidEntryPoint
class MainFragment: Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val tokenViewModel: TokenViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController
    private var token: String? = null

    private var mainAdapter: MainAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        CoroutineScope(Dispatchers.Main).launch {
            mainViewModel.getSurveyItems(token, object: CoroutinesErrorHandler {
                override fun onError(message: String) {
                    try {
                        val action = MainFragmentDirections.main2ChkDialog("네트워크 연결을 확인해주세요.")
                        navController.navigate(action)
                    } catch (e: IllegalArgumentException) { }
                }
            })
        }

        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
            this.token = token

            if (token == null) {
                try {
                    navController.navigate(R.id.main_2_login)
                } catch (e: IllegalArgumentException) { }
            }
        }

        mainViewModel.surveyQuestionsResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    try {
                        val action = MainFragmentDirections.main2ChkDialog("네트워크 연결을 확인해주세요.")
                        navController.navigate(action)
                    } catch (e: IllegalArgumentException) { }
                }
                is ApiResponse.Success -> { mainAdapter = MainAdapter(it.data.surveyItems); binding.recyclerView.adapter = mainAdapter }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        mainViewModel.userResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    try {
                        val action = MainFragmentDirections.main2ChkDialog("네트워크 연결을 확인해주세요.")
                        navController.navigate(action)
                    } catch (e: IllegalArgumentException) { }
                }
                is ApiResponse.Success -> { }
            }
        }

        mainViewModel.surveyQuestionsResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    try {
                        val action = MainFragmentDirections.main2ChkDialog("네트워크 연결을 확인해주세요.")
                        navController.navigate(action)
                    } catch (e: IllegalArgumentException) { }
                }
                is ApiResponse.Success -> { }
            }
        }

        mainViewModel.radarResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> {
                    try {
                        val action = MainFragmentDirections.main2ChkDialog("네트워크 연결을 확인해주세요.")
                        navController.navigate(action)
                    } catch (e: IllegalArgumentException) { }
                }
                is ApiResponse.Success -> {
                    val radarData: ArrayList<RadarData> = ArrayList<RadarData>()

                    CoroutineScope(Dispatchers.Main).launch {
                        for (prop in RadarResponse::class.memberProperties) {
                            val foundEnum = findEnumByKey<RadarType>(prop.name)

                            if (foundEnum != null) {
                                radarData.add(RadarData(foundEnum, prop.get(it.data).toString().toDouble().toInt()))
                            }
                        }

                        try {
                            val action = MainFragmentDirections.main2Radar(radarData.toTypedArray())
                            navController.navigate(action)
                        } catch(e: IllegalArgumentException) { }
                    }
                }

            }
        }

        binding.submitBtn.setOnClickListener {
            try {
                mainViewModel.getModelResults(
                    SurveyResultRequest(
                        mainAdapter!!.getSurveyResults(),
                        binding.workTimeField.text.toString().toInt()
                    ), object : CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            try {
                                val action =
                                    MainFragmentDirections.main2ChkDialog("네트워크 연결을 확인해주세요.")
                                navController.navigate(action)
                            } catch (e: IllegalArgumentException) { }
                        }
                    })

            } catch (e: NumberFormatException) {
                try {
                    val action =
                        MainFragmentDirections.main2ChkDialog("작업시간을 숫자형식 \n(예: 10시간 -> 10)\n으로 입력해주세요.")
                    navController.navigate(action)
                } catch (e: IllegalArgumentException) { }
            }
        }
    }

    override fun onStop() {
        super.onStop()

        tokenViewModel.delete()
    }

    override fun onDestroy() {
        super.onDestroy()

        tokenViewModel.delete()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        tokenViewModel.delete()
    }
}
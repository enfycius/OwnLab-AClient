package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.FragmentMainBinding
import com.ownlab.ownlab_client.models.SurveyResultRequest
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.view.adapter.MainAdapter
import com.ownlab.ownlab_client.viewmodels.MainViewModel
import com.ownlab.ownlab_client.viewmodels.TokenViewModel
import com.ownlab.ownlab_client.viewmodels.`interface`.CoroutinesErrorHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                    Toast.makeText(activity, "Error $message", Toast.LENGTH_LONG).show()
                }
            })
        }

        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
            this.token = token

            if (token == null) {
                navController.navigate(R.id.main_2_login)
            }
        }

        mainViewModel.surveyQuestionsResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> Toast.makeText(activity, "Failed", Toast.LENGTH_LONG).show()
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
                is ApiResponse.Failure -> Toast.makeText(activity, "Failed", Toast.LENGTH_LONG).show()
                is ApiResponse.Success -> Toast.makeText(activity, "${it.data.email}", Toast.LENGTH_LONG).show()
            }
        }

        mainViewModel.surveyQuestionsResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> Toast.makeText(activity, "Failed", Toast.LENGTH_LONG).show()
                is ApiResponse.Success -> Toast.makeText(activity, "${it.data.surveyItems[0]}", Toast.LENGTH_LONG).show()
            }
        }

        mainViewModel.radarResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> Toast.makeText(activity, "Failed", Toast.LENGTH_LONG).show()
                is ApiResponse.Success -> Toast.makeText(activity, "${it.data.responsibility}", Toast.LENGTH_LONG).show()
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
                            Toast.makeText(activity, "Error $message", Toast.LENGTH_LONG).show()
                        }
                    })

            } catch (e: NumberFormatException) {
                Toast.makeText(activity, "Error $e", Toast.LENGTH_LONG).show()
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
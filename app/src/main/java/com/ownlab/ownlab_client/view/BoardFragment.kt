package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.FragmentBoardBinding
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
class BoardFragment: Fragment() {
    private var _binding: FragmentBoardBinding? = null
    private val binding get() = _binding!!

    private val tokenViewModel: TokenViewModel by activityViewModels()

    private lateinit var navController: NavController
    private var token: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBoardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
            this.token = token

            Log.d("Test2", this.token.toString())

            if (token == null) {
                try {
                    navController.navigate(R.id.main_2_login)
                } catch (e: IllegalArgumentException) { }
            }
        }
    }
}
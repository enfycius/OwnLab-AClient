package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.ownlab.ownlab_client.databinding.FragmentRadarBinding
import com.ownlab.ownlab_client.models.RadarData
import com.ownlab.ownlab_client.viewmodels.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RadarFragment: Fragment() {
    private var _binding: FragmentRadarBinding? = null
    private val binding get() = _binding!!

    val args: RadarFragmentArgs by navArgs()

    private val tokenViewModel: TokenViewModel by activityViewModels()

    private lateinit var navController: NavController
    private var token: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRadarBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.radarChart.setRadarData(args.radarData!!.toList() as ArrayList<RadarData>)
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
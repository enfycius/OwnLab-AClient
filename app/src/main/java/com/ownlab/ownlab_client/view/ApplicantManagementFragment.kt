package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.R.*
import com.ownlab.ownlab_client.databinding.FragmentApplicantManagementBinding

class ApplicantManagementFragment : Fragment() {
    private var _binding: FragmentApplicantManagementBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApplicantManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.FragmentUserAgreementBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAgreementFragment: Fragment() {
    private var _binding: FragmentUserAgreementBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserAgreementBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val lastSelectedRadioButtonName = arguments?.getString("lastSelectedRadioButtonName") ?: ""

        binding.userAgreementNextBtn.setOnClickListener {
            if (binding.userAgreementCheckBox1.isChecked && binding.userAgreementCheckBox2.isChecked) {
                if(lastSelectedRadioButtonName == "seekerRadioButton"){
                    navController.navigate(R.id.login_2_register)
                }else{
                    navController.navigate(R.id.action_userAgreementFragment_to_register_nav)

                }
            } else {
                binding.allAgreeAlarm.visibility = View.VISIBLE
                Handler().postDelayed({
                    binding.allAgreeAlarm.visibility=View.GONE
                }, 1000)
            }
        }
        binding.allAgreeCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.userAgreementCheckBox1.isChecked = isChecked
            binding.userAgreementCheckBox2.isChecked = isChecked
            binding.userAgreementCheckBox3.isChecked = isChecked
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
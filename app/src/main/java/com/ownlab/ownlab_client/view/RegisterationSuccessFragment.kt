package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.FragmentRegistrationSuccessBinding

class RegisterationSuccessFragment: Fragment() {
    private var _binding: FragmentRegistrationSuccessBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRegistrationSuccessBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.loginButton.setOnClickListener {
            navController.navigate(R.id.register_2_login)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
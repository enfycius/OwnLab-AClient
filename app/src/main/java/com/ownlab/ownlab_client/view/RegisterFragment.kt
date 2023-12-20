package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.FragmentLoginBinding
import com.ownlab.ownlab_client.databinding.FragmentRegisterBinding
import com.ownlab.ownlab_client.models.Id
import com.ownlab.ownlab_client.utils.ApiResponse

import com.ownlab.ownlab_client.viewmodels.RegisterViewModel
import com.ownlab.ownlab_client.viewmodels.`interface`.CoroutinesErrorHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment: Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        registerViewModel.idChkResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    if (it.data.message == "Failed") {
                        Toast.makeText(activity, it.data.message, Toast.LENGTH_LONG).show()

                        val action = RegisterFragmentDirections.register2RegisterIdChk("아이디가 이미 존재합니다.")
                        navController.navigate(action)
                    }

                }
                is ApiResponse.Failure -> {
                    Toast.makeText(activity, "API Failed", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.idChkBtn.setOnClickListener {
            registerViewModel.idChk(Id(binding.idField.text.toString()), object: CoroutinesErrorHandler {
                override fun onError(m : String) {
                    Toast.makeText(activity, "Error $m", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.FragmentLoginBinding
import com.ownlab.ownlab_client.models.Auth
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.viewmodels.LoginViewModel
import com.ownlab.ownlab_client.viewmodels.TokenViewModel
import com.ownlab.ownlab_client.viewmodels.interfaces.CoroutinesErrorHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    private lateinit var navController: NavController
    private var lastSelectedRadioButtonName: String = "seekerRadioButton"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        tokenViewModel.token.observe(viewLifecycleOwner) {
            if (it != null) {
                navController.navigate(R.id.login_2_main)
            } else {
            }
        }

        loginViewModel.loginResponse.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Success -> {
                    tokenViewModel.save(it.data.token)
                }
                is ApiResponse.Failure -> {
                    val action = LoginFragmentDirections.login2ChkDialog("아이디 또는 비밀번호를 잘못 입력하셨습니다.")
                    navController.navigate(action)
                }
            }
        }

        binding.loginBtn.setOnClickListener {
            val id: String = binding.idField.text.toString()
            val password: String = binding.passwordField.text.toString()

            loginViewModel.login(Auth(id, password), object : CoroutinesErrorHandler {
                override fun onError(m: String) {
                }
            })
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            lastSelectedRadioButtonName = resources.getResourceEntryName(checkedId)
        }

        binding.registerBtn.setOnClickListener {
            val bundle = Bundle().apply {
                putString("lastSelectedRadioButtonName", lastSelectedRadioButtonName)
            }
            navController.navigate(R.id.login_2_userAgreement, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
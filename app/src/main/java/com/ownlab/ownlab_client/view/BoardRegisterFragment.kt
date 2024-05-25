package com.ownlab.ownlab_client.view

import android.app.DatePickerDialog
import android.icu.util.Calendar
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
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.FragmentBoardRegisterBinding
import com.ownlab.ownlab_client.models.PostItemRequest
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.viewmodels.BoardViewModel
import com.ownlab.ownlab_client.viewmodels.TokenViewModel
import com.ownlab.ownlab_client.viewmodels.interfaces.CoroutinesErrorHandler
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat

@AndroidEntryPoint
class BoardRegisterFragment: Fragment() {
    private var _binding: FragmentBoardRegisterBinding? = null
    private val binding get() = _binding!!

    private val tokenViewModel: TokenViewModel by activityViewModels()
    private val boardViewModel: BoardViewModel by viewModels()

    private lateinit var navController: NavController
    private var token: String? = null

    private fun checkDates(dateString: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy.M.d")
        dateFormat.isLenient = false

        return try {
            dateFormat.parse(dateString)
            true
        } catch (e: ParseException) {
            false
        }
    }

    private fun compareDates(startDate: String, endDate: String): Int {
        return startDate.compareTo(endDate)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBoardRegisterBinding.inflate(inflater, container, false)

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

        boardViewModel.registerResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    try {
                        navController.navigate(R.id.board_register_2_board)
                    } catch (e: IllegalArgumentException) { }
                }
                is ApiResponse.Failure -> {
                    try {
                        val action =
                            BoardRegisterFragmentDirections.boardRegister2ChkDialog("네트워크 연결을 확인해주세요.")
                        navController.navigate(action)
                    } catch (e: IllegalArgumentException) { }
                }
            }
        }

        binding.startDateBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            context?.let { it ->
                DatePickerDialog(it, { _, year, month, day ->
                    run {
                        binding.startDateField.setText(year.toString() + "." + (month + 1).toString() + "." + day.toString())
                    }
                }, year, month, day)
            }?.show()
        }

        binding.endDateBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            context?.let { it ->
                DatePickerDialog(it, { _, year, month, day ->
                    run {
                        binding.endDateField.setText(year.toString() + "." + (month + 1).toString() + "." + day.toString())
                    }
                }, year, month, day)
            }?.show()
        }

        binding.registerBtn.setOnClickListener {

            try {
                val title: String = binding.title.text.toString()
                val contacts: String = binding.contacts.text.toString()
                val assignee: String = binding.assignee.text.toString()
                val registrationMethod: String = binding.registrationMethod.text.toString()
                val address: String = binding.address.text.toString()
                val detailedLink: String = binding.detailedLink.text.toString()
                val startDate: String = binding.startDateField.text.toString()
                val endDate: String = binding.endDateField.text.toString()
                val limitation: Int = binding.limitation.text.toString().toInt()

                if (!this.checkDates(startDate) || !this.checkDates(endDate)) {
                    try {
                        val action =
                            BoardRegisterFragmentDirections.boardRegister2ChkDialog("날짜를 제대로 입력해주세요.")
                        navController.navigate(action)
                    } catch (e: IllegalArgumentException) { }
                } else {
                    val resultOfCompare = compareDates(startDate, endDate)

                    when {
                        resultOfCompare <= 0 -> {
                            boardViewModel.registerPostItems(
                                token, PostItemRequest(
                                    title, contacts, assignee, registrationMethod, address, detailedLink,
                                    startDate, endDate, limitation
                                ), object : CoroutinesErrorHandler {
                                    override fun onError(message: String) {
                                        Log.d("Test3", message)
                                        try {
                                            val action =
                                                BoardRegisterFragmentDirections.boardRegister2ChkDialog("네트워크 연결을 확인해주세요.")
                                            navController.navigate(action)
                                        } catch (e: IllegalArgumentException) { }
                                    }
                                })
                        } else -> {
                            try {
                                val action =
                                    BoardRegisterFragmentDirections.boardRegister2ChkDialog("시작일이 종료일보다 앞서거나 같아야 합니다.")
                                navController.navigate(action)
                            } catch (e: IllegalArgumentException) { }
                        }
                    }

                }
            } catch (e: NumberFormatException) {
                Log.d("Test", "Type Conversion Error during Runtime")

                try {
                    val action =
                        BoardRegisterFragmentDirections.boardRegister2ChkDialog("모집인원은 숫자 형식으로 입력해주세요.")
                    navController.navigate(action)
                } catch (e: IllegalArgumentException) { }
            }
        }
    }
}
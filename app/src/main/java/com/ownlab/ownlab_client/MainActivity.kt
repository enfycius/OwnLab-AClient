package com.ownlab.ownlab_client

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ownlab.ownlab_client.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setupToolbar()
        setupNav()
        setContentView(binding?.root)

    }

    override fun onSupportNavigateUp(): Boolean {
        return binding.hostFragment.getFragment<NavHostFragment>().navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupToolbar() {
        val navController = binding.hostFragment.getFragment<NavHostFragment>().navController
        val toolbar = binding.fragmentToolbar
        val toolbarTitle = toolbar.findViewById<TextView>(R.id.toolbar_title)

        toolbar.setBackgroundResource(R.color.white)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    toolbarTitle.text = "로그인"
                    toolbar.visibility = View.VISIBLE
                }
                R.id.userAgreementFragment, R.id.registerFragment, R.id.bizRegisterFragment, R.id.registerationSuccessFragment -> {
                    toolbarTitle.text = "회원가입"
                    toolbar.visibility = View.VISIBLE
                }

                R.id.mainFragment -> {
                    toolbarTitle.text = "평가관리"
                    toolbar.visibility = View.VISIBLE
                }
                R.id.boardFragment ->{
                    toolbarTitle.text = "공고/지원자관리"
                    toolbar.visibility = View.VISIBLE
                }
                R.id.applicantManagementFragment2->{
                    toolbarTitle.text = "공고/지원자관리"
                    toolbar.visibility = View.VISIBLE
                }
                R.id.myPageScreen -> {
                    toolbarTitle.text= "마이페이지"
                    toolbar.visibility = View.VISIBLE
                }

                R.id.ownLabMainScreen->{
                    toolbarTitle.text ="홈"
                    toolbar.visibility = View.GONE
                }
                R.id.jobFinderScreen->{
                    toolbarTitle.text ="알바 검색"
                    toolbar.visibility = View.GONE
                }
                R.id.boardRegisterFragment->{
                    toolbarTitle.text= "채용공고등록"
                    toolbar.visibility = View.VISIBLE
                }
                R.id.regionalSettingScreen->{
                    toolbarTitle.text="지역선택"
                    toolbar.visibility = View.VISIBLE
                }
                R.id.resumeManagerScreen -> {
                    toolbarTitle.text = "이력서 관리"
                    toolbar.visibility = View.VISIBLE
                }

                R.id.mainFragment -> toolbar.navigationIcon = null
                R.id.boardFragment -> toolbar.navigationIcon = null
                R.id.myPageScreen -> toolbar.navigationIcon = null

                else -> {hideBottomNav()
                    toolbar.visibility = View.VISIBLE
                }
            }
            val hideHomeButtonIds = setOf(R.id.mainFragment, R.id.boardFragment, R.id.myPageScreen, R.id.ownLabMainScreen)
            toolbar.navigationIcon = if (hideHomeButtonIds.contains(destination.id)) null else toolbar.navigationIcon

        }
    }

    private fun setupNav() {
        val navController = binding.hostFragment.getFragment<NavHostFragment>().navController

        binding.bottomNavi.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.regionalSettingScreen -> hideBottomNav()
                else -> showBottomNav()
            }
        }
    }
    private fun showBottomNav() {
        binding.bottomNavi.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavi.visibility = View.GONE
    }
}
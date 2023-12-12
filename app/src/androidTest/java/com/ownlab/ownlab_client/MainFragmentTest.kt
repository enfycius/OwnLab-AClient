package com.ownlab.ownlab_client

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ownlab.ownlab_client.view.MainFragment
import com.ownlab.ownlab_client.viewmodels.MainViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock

@HiltAndroidTest
class MainFragmentTest {
//    @BindValue
//    val mockMainViewModel = mock<MainViewModel>()


    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    private var navController: NavHostController? = null

    @Before
    fun setup() {
        hiltAndroidRule.inject()
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun check_Fragment() {
        val scenario = launchFragmentInHiltContainer<MainFragment>(navHostController = navController) {
            viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                navController!!.setGraph(R.navigation.main_nav)

            }
        }
    }
}
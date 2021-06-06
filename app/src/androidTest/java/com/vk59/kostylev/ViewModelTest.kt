package com.vk59.kostylev

import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vk59.kostylev.Status.*
import com.vk59.kostylev.model.ResponseData
import com.vk59.kostylev.ui.BaseViewModel
import com.vk59.kostylev.ui.hot.HotViewModel
import com.vk59.kostylev.ui.main.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewModelTest : BaseViewModel() {
    private lateinit var hotViewModel: HotViewModel

    @Before
    fun setUp() {
        hotViewModel = HotViewModel()
    }

    @Test
    fun testViewModelExtendsBase() {
        var latest: ResponseData?
        runBlocking {
            latest = api.getLatest(0)
        }
        assertNotNull(latest)
        assertNotNull(latest?.result)
    }


    @Test
    fun eventTest() {
        val liveData: MutableLiveData<Event> = MutableLiveData()
        hotViewModel.requestWithLiveData(liveData) {
            api.getHot(0)
        }

        assertEquals(LOADING, liveData.value!!.status)
        assertNotNull(liveData.value)

        runBlocking {
            delay(2000)
            assertTrue(SUCCESS == liveData.value!!.status || ERROR == liveData.value!!.status)
        }

    }

    private val viewModel = MainViewModel()

    @Test
    fun getData() {
        assertNotNull(viewModel.getLatest(0))
    }

}
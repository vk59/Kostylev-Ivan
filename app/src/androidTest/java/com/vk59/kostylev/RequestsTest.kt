package com.vk59.kostylev

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vk59.kostylev.model.ResponseData
import com.vk59.kostylev.network.Api
import com.vk59.kostylev.network.NetworkService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RequestsTest {
    private var api: Api = NetworkService.retrofitService()
    private lateinit var latest: ResponseData
    private lateinit var hot: ResponseData
    private lateinit var top: ResponseData

    @Before
    fun setUp() {
        runBlocking {
            latest = api.getLatest(0)
            hot = api.getHot(0)
            top = api.getTop(0)
        }
    }

    @Test
    fun testIsThereResponse() {
        assertNotNull(latest)
        assertNotNull(hot)
        assertNotNull(top)
    }

    /*
    This test was written at the 6th of June 2021.
    During the time response data can be changed.
    So test below will break all the program
     */

    /*
    @Test
    fun responseCheck() {
        assertEquals(17088, latest.result!![0].id)
        val expectedUrl = "http://static.devli.ru/public/images/gifs/202009/3c2dbbe9-da67-4df3-8790-0fa3d995ceeb.gif"
        assertEquals(expectedUrl, latest.result!![1].gifURL)
    }
    */
}
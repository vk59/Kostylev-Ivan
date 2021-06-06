package com.vk59.kostylev.ui.main

import androidx.lifecycle.MutableLiveData
import com.vk59.kostylev.Event
import com.vk59.kostylev.ui.BaseViewModel

class MainViewModel : BaseViewModel() {
    // Создаем лайвдату для нашего списка юзеров
    val simpleLiveData = MutableLiveData<Event>()

    // Получение юзеров. Обращаемся к функции  requestWithLiveData
    // из BaseViewModel передаем нашу лайвдату и говорим,
    // какой сетевой запрос нужно выполнить и с какими параметрами
    // В данном случае это api.getUsers
    // Теперь функция сама выполнит запрос и засетит нужные
    // данные в лайвдату
    fun getLatest(page: Int) {
        requestWithLiveData(simpleLiveData) {
            api.getLatest(
                page = page
            )
        }
    }

    // Здесь аналогично, но вместо лайвдаты используем котлиновский колбек
    // UPD Полученный результат мы можем обработать здесь перед отправкой во вью
//    fun getUsersError(page: Int, callback: (data: Event) -> Unit) {
//        requestWithCallback({
//            api.getUsersError(
//                page = page
//            )
//        }) {
//            callback(it)
//        }
//    }
}
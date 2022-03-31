package com.example.workmanagerproject.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.workmanagerproject.model.api.ApiService
import com.example.workmanagerproject.model.response.ApiResponse
import com.example.workmanagerproject.model.worker.MyWorker
import com.example.workmanagerproject.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MyViewModel
@Inject
constructor(private val repository: ApiRepository,
application: Application
): AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    val _response = MutableStateFlow<String?>(null)
    val response: StateFlow<String?> get() = _response
    val uploadWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<MyWorker>().build()

    init{
        getDataViewModel()
    }

    private fun getDataViewModel(){

        WorkManager
            .getInstance(context)
            .enqueue(uploadWorkRequest)

        if(response.value != null) Log.e("TAG", "Data response is successful $response")
        else Log.e("TAG", "Data response is failed ${_response.value}")
    }

}
package com.example.workmanagerproject.model.worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.workmanagerproject.model.api.ApiService
import com.example.workmanagerproject.model.response.ApiResponse
import com.example.workmanagerproject.repository.ApiRepository
import com.example.workmanagerproject.viewmodel.MyViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltWorker
class MyWorker  @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val viewModel : MyViewModel,
    private val apiService: ApiService): Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.e("TAG", "do work started")
        try {
            getData{result ->
                viewModel._response.value = result
            }
        }catch (e: HttpException){
            Log.e("TAG", "getData HttpException: $e")
            return Result.retry()
        }
        return Result.success()
    }

    private fun getData(callback: (String?) -> Unit) = CoroutineScope(Dispatchers.IO).launch {
        apiService.getData().enqueue(object : Callback<ApiResponse> {
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("tag", "getRecipe Error")
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                return response.body()!!.message.let { callback(it) }!!
            }

        })
        }

    }
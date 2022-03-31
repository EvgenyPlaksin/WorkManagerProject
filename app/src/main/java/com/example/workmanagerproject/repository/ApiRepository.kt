package com.example.workmanagerproject.repository

import android.util.Log
import com.example.workmanagerproject.model.api.ApiService
import com.example.workmanagerproject.viewmodel.MyViewModel
import javax.inject.Inject

class ApiRepository
@Inject
constructor( private val service: ApiService) {
    fun getData() =  service.getData()
}
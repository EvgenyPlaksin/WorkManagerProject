package com.example.workmanagerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import coil.load
import com.example.workmanagerproject.ckeck.ConnectionCheck
import com.example.workmanagerproject.databinding.ActivityMainBinding
import com.example.workmanagerproject.model.worker.MyWorker
import com.example.workmanagerproject.repository.ApiRepository
import com.example.workmanagerproject.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var cld: ConnectionCheck
    private val viewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

                binding.button.setOnClickListener {

                    WorkManager.getInstance(this).getWorkInfoByIdLiveData(viewModel.uploadWorkRequest?.id)
                        .observe(this) {
                            Log.e("TAG", "it -> $it")
                        }

                    lifecycleScope.launchWhenStarted {
                        Log.e("TAG", "response -> ${viewModel.response.value}")
                        viewModel.response.collect { response ->
                        binding.imageView.load(response) {
                            Log.e("TAG", "response2 -> ${response}")
                            placeholder(R.drawable.ic_baseline_error_24)
                        }
                        }
                    }

                }
        }
}


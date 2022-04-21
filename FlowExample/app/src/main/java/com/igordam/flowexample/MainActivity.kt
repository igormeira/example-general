package com.igordam.flowexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.igordam.flowexample.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initClickListeners()
        initObservablesSubscription()
    }

    private fun initClickListeners() {
        binding.btnLiveData.setOnClickListener {
            viewModel.updateLiveData()
        }

        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                viewModel.updateFlow().collectLatest { value ->
                    binding.tvFlowCounterValue.text = value
                }
            }
        }

        binding.btnStateFlow.setOnClickListener {
            viewModel.updateStateFlow()
        }

        binding.btnSharedFlow.setOnClickListener {
            viewModel.updateSharedFlow()
        }
    }

    private fun initObservablesSubscription() {
        // LiveData
        viewModel.liveData.observe(this, { data ->
            binding.tvLiveDataCounterValue.text = data.toString()
        })

        // StateFlow
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collectLatest { value ->
                Snackbar.make(
                    binding.root,
                    value.toString(),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        // SharedFlow
        lifecycleScope.launchWhenStarted {
            viewModel.sharedFlow.collectLatest { value ->
                Snackbar.make(
                    binding.root,
                    value,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

}
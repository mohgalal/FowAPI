package com.example.observertaskodc

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.observertaskodc.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeData()
        initClickListeners()
    }

    private fun initClickListeners() {

        binding.livedataBtn.setOnClickListener {
            viewModel.setTextLiveData()
        }

        binding.sharedFlowBtn.setOnClickListener {
            viewModel.setTextSharedFlow()
        }

        binding.stateFlowBtn.setOnClickListener {
            viewModel.setTextStateFlow()
        }


        binding.flowBtn.setOnClickListener {
            // viewModel.getTextFlowValue()
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getTextFlowValue().collect {
                        binding.flowTv.text = it
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun observeData() {
        viewModel.textLiveData.observe(this) {
            binding.liveDataTv.text = it
            Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_SHORT
            ).show()
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.textStateFlow.collect {
                    binding.stateFlowTv.text = it
                    /*Snackbar.make(
                        binding.root,
                        it,
                        Snackbar.LENGTH_SHORT
                    ).show()*/
                }
            }

        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.textSharedFlow.collect {
                    binding.sharedFlowTv.text = it
                    Snackbar.make(
                        binding.root,
                        it,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
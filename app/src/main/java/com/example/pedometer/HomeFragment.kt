package com.example.pedometer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pedometer.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {
    lateinit var binding: FragmentHomeBinding

    var steps = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeStepCountTv.text = steps.toString()

        return binding.root
    }

    private fun loadData(){
        val sharedPreference = requireActivity().getSharedPreferences("steps", Context.MODE_PRIVATE)
        val n = sharedPreference.getInt("key", 0)

        steps = n
    }

}
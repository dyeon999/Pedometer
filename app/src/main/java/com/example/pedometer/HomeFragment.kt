package com.example.pedometer

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pedometer.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {
    lateinit var binding: FragmentHomeBinding

    var steps = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeStepCountTv.text = loadData().toString()

        binding.homeStepResetBt.setOnClickListener {
            steps = 0f
            saveData()
        }

        return binding.root
    }

    private fun saveData(){
        val sharedPreferences = requireActivity().getSharedPreferences("steps", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putFloat("key", steps)
        editor.apply()
    }

    private fun loadData(): Float {
        val sharedPreference = requireActivity().getSharedPreferences("steps", Context.MODE_PRIVATE)
        steps = sharedPreference.getFloat("key", 0f)

        Log.d("STEP : ", steps.toString())

        return steps
    }

}
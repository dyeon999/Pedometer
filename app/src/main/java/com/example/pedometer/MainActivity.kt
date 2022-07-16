package com.example.pedometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pedometer.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity(), SensorEventListener {
    lateinit var binding: ActivityMainBinding

    var running = false
    var sensorManager: SensorManager? = null
    var steps = 0f

    // onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

//        this.supportFragmentManager.beginTransaction()
//            .replace(R.id. main_frm, HomeFragment()).commitAllowingStateLoss()

        setContentView(R.layout.activity_main)

        binding.mainStepCountTv.text = loadData().toString()
        binding.mainStepResetBt.setOnClickListener {
            resetData()
            binding.mainStepCountTv.text = 0.toString()
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

    }

    //onResume
    override fun onResume() {
        super.onResume()
        running = true
        var stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null ){
            Toast.makeText(this, "No Step Counter Sensor !", Toast.LENGTH_SHORT).show()
        }
        else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (running){
            steps = event.values[0]
            saveData()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    private fun saveData(){
        val sharedPreferences = getSharedPreferences("steps", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putFloat("key", steps)
        editor.apply()
    }

    private fun loadData(): Float {
        val sharedPreference = getSharedPreferences("steps", Context.MODE_PRIVATE)
        val key = sharedPreference.getFloat("key", 0f)

        Log.d("MAIN STEPS ", key.toString())

        return key
    }

    private fun resetData() {
        steps = 0f
    }
}
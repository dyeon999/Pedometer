package com.example.pedometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pedometer.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity(), SensorEventListener {
    lateinit var binding: ActivityMainBinding

    var running = false
    var sensorManager: SensorManager? = null
    val steps = 0

    // onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        this.supportFragmentManager.beginTransaction()
            .replace(R.id. main_frm, HomeFragment()).commitAllowingStateLoss()

        setContentView(R.layout.activity_main)

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
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (running){
            saveData()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    private fun saveData(){
        val sharedPreferences = getSharedPreferences("steps", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putInt("key", steps)
        editor.apply()
    }
}
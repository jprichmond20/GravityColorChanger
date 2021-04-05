package edu.coe.hughes.sensors

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService


class MainActivity :  AppCompatActivity(){



    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val numSensors = findViewById(R.id.txtNumSensors) as TextView
        val availSensorNames = findViewById(R.id.txtSensorNames) as TextView
        // Get the SensorManager
        val sensorMgr = getSystemService(SENSOR_SERVICE) as SensorManager?
        val sensorList = sensorMgr!!.getSensorList(Sensor.TYPE_ALL)

        // Print how may Sensors are there
        numSensors.text = sensorList.size.toString() + " Sensors"

        // Print each Sensor available using sSensList as the String to be printed

        var sensList = StringBuilder(2048);
        for (sensor in sensorList) {
            sensList.append(sensor.name+"\n")
            sensList.append("Delay"+ sensor.minDelay+"\t")
            sensList.append("Range"+ sensor.maximumRange+"\n\n")
        }
        availSensorNames.text = sensList.toString()
    }


}

package edu.coe.hughes.sensors

import android.R.attr.x
import android.R.attr.y
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.math.*
import kotlin.math.abs


class AltActivity :  AppCompatActivity(), SensorEventListener {

    lateinit var sensorMgr: SensorManager
    lateinit var gravity: Sensor
    var motion = FloatArray(3) {0f}
    var last_x = 0f
    var last_y = 0f
    var last_z = 0f
    var lastUpdate = System.currentTimeMillis()
    val SHAKE_THRESHOLD = 800

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alt_activity)

        val xText = findViewById(R.id.xText) as TextView
        val yText = findViewById(R.id.yText) as TextView
        val zText = findViewById(R.id.zText) as TextView
        // Get the SensorManager
        sensorMgr = getSystemService(SENSOR_SERVICE) as SensorManager
        gravity = sensorMgr!!.getDefaultSensor(Sensor.TYPE_GRAVITY);
        //val sensorList = sensorMgr!!.getSensorList(Sensor.TYPE_ALL)


        // Print each Sensor available using sSensList as the String to be printed

        /*var sensList = StringBuilder(2048);
        for (sensor in sensorList) {
            sensList.append(sensor.name + "\n")
            sensList.append("Delay" + sensor.minDelay + "\t")
            sensList.append("Range" + sensor.maximumRange + "\n\n")
        }*/
        xText.text = "Gravity on X"
        yText.text = "Gravity on Y"
        zText.text = "Gravity on Z"
    }

    override fun onResume(){
        super.onResume()
        sensorMgr!!.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL)

        //SensorManager.SENSOR_DELAY_FASTEST
        //SensorManager.SENSOR_DELAY_GAME     20 millisecond;   20,000 microsecond
        //SensorManager.SENSOR_DELAY_UI       60  millisecond
        //SensorManager.SENSOR_DELAY_NORMAL   200  milliscond
    }

    override fun onPause() {
        super.onPause()
        sensorMgr!!.unregisterListener(this, gravity)
    }

////  override fun onSensorChanged(event: SensorEvent?) {
//        val availSensorNames = findViewById(R.id.txtSensorNames) as TextView
//        var sensList = StringBuilder(2048);
//        for (e in event!!.values) {
//            sensList.append(e.toString() + "\n")
//        }
//        availSensorNames.text = sensList.toString()
//    }


    override fun onSensorChanged(event: SensorEvent?) {
         val curTime = System.currentTimeMillis()

            if (curTime - lastUpdate > 100) {
                //val diffTime: Long = curTime - lastUpdate
                //lastUpdate = curTime
                val x = event!!.values[0]
                val y = event!!.values[1]
                val z = event!!.values[2]

                val redX = BigDecimal.valueOf(x.toDouble()).setScale(2, RoundingMode.HALF_EVEN)
                val redY = BigDecimal.valueOf(y.toDouble()).setScale(2, RoundingMode.HALF_EVEN)
                val redZ = BigDecimal.valueOf(z.toDouble()).setScale(2, RoundingMode.HALF_EVEN)
                /*val speed: Float = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000
                if (speed > SHAKE_THRESHOLD) {
                    Log.d("sensor", "shake detected w/ speed: $speed")
                    Toast.makeText(this, "shake detected w/ speed: $speed", Toast.LENGTH_SHORT)
                        .show()
                }
                last_x = x
                last_y = y
                last_z = z*/
                val xText = findViewById(R.id.xText) as TextView
                val yText = findViewById(R.id.yText) as TextView
                val zText = findViewById(R.id.zText) as TextView
                xText.text = "Gravity on X = $redX"
                yText.text = "Gravity on Y = $redY"
                zText.text = "Gravity on Z = $redZ"
                /*xText.text = "Gravity on X = $x"
                yText.text = "Gravity on Y = $y"
                zText.text = "Gravity on Z = $z"*/
                val bigGuy = findViewById<ConstraintLayout>(R.id.container)
                if (abs(redX.toFloat()) > abs(redY.toFloat()) && abs(redX.toFloat()) > abs(redZ.toFloat())) {
                    bigGuy.setBackgroundColor(Color.RED)
                }
                else if (abs(redY.toFloat()) > abs(redX.toFloat()) && abs(redY.toFloat()) > abs(redZ.toFloat())) {
                    bigGuy.setBackgroundColor(Color.BLUE)
                }
                else if (abs(redZ.toFloat()) > abs(redX.toFloat()) && abs(redZ.toFloat()) > abs(redY.toFloat())) {
                    bigGuy.setBackgroundColor(Color.GREEN)
                }
                else {
                    bigGuy.setBackgroundColor(Color.WHITE)
                }
            }
        }





    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

}

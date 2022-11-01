package com.ukm.iotsamplereceiver.component

import android.os.CountDownTimer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ukm.iotsamplereceiver.model.SensorValue
import kotlin.random.Random

class ColorSensor(private val action: (Double) -> Unit) {
    val firestore = Firebase.firestore
    val historyRef = firestore.collection("colorSensorHistory")
    val powerRef = firestore.collection("dispenser").document("powerOn")
    lateinit var timer : CountDownTimer

    fun start() {
        timer = object: CountDownTimer(5000, 1000) {
            override fun onTick(p0: Long) {
                val randomNumber = Random.nextDouble()
                historyRef.add(SensorValue(randomNumber))
                action.invoke(randomNumber)
            }

            override fun onFinish() {
                powerRef.update("startTask", false)
            }
        }
        timer.start()
    }
}
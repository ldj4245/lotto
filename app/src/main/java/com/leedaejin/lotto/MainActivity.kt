package com.leedaejin.lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val clearButton: Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }

    private val addButton: Button by lazy {
        findViewById(R.id.addButton)
    }

    private val runButton:Button by lazy{
        findViewById(R.id.runButton)
    }

    private val numberPicker : NumberPicker by lazy{
        findViewById(R.id.numberPicker)
    }

    private var didrun = false

    private val pickNumberSet  = hashSetOf<Int>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
    }
    private fun initAddButton(){
        addButton.setOnClickListener {
            Toast.makeText(this,"초기화 후에 시도해주세요.",Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }
    }
    private fun initRunButton(){

        runButton.setOnClickListener {
            val list = getRandomNumber()

            Log.d("mainActivity",list.toString())

        }


    }
    private fun getRandomNumber():List<Int>{

        val numberList = mutableListOf<Int>().apply {

            for(i in 1..45){
                this.add(i)
            }
        }
        numberList.shuffle()

        var newList = numberList.subList(0,6)

        return newList.sorted()
    }

}
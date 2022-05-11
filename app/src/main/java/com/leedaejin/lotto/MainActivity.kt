package com.leedaejin.lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private val clearButton: Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }

    private val addButton: Button by lazy {
        findViewById(R.id.addButton)
    }

    private val runButton: Button by lazy {
        findViewById(R.id.runButton)
    }

    private val numberPicker: NumberPicker by lazy {
        findViewById(R.id.numberPicker)
    }


        private  val numberTextViewList:List<TextView> by lazy{
            listOf<TextView>(

                        findViewById<TextView>(R.id.textView1),
                        findViewById<TextView>(R.id.textView2),
                        findViewById<TextView>(R.id.textView3),
                        findViewById<TextView>(R.id.textView4),
                        findViewById<TextView>(R.id.textView5),
                        findViewById<TextView>(R.id.textView6)
            )
        }


    private var didrun = false //눌러서 번호를 추가할수 없는 상태

    private val pickNumberSet = hashSetOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
        initClearButton()
    }

    private fun initAddButton() { // 번호추가 버튼
        addButton.setOnClickListener {
            if (didrun) { //이미 자동버튼을 눌러서 추가할수 없는 상황이라면?
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.size >= 5) {
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }

            if (pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            val textView = numberTextViewList[pickNumberSet.size]

            textView.isVisible = true
            textView.text = numberPicker.value.toString()

            setNumberBackground(numberPicker.value,textView)

            pickNumberSet.add(numberPicker.value)


            //초기화누르면 false로 변함 번호추가하기 가능
            //자동생성시작 누르면 true로 변함 번호추가 못함 false로 바꾸고 번호추가해야함



        }
    }

    private fun setNumberBackground(number:Int,textView: TextView){

        when(number){
            in 1..10 ->   textView.background = ContextCompat.getDrawable(this,R.drawable.circle_yellow)
            in 11..20 ->   textView.background = ContextCompat.getDrawable(this,R.drawable.circle_blue)
            in 21..30 ->   textView.background = ContextCompat.getDrawable(this,R.drawable.circle_red)
            in 31..40 ->   textView.background = ContextCompat.getDrawable(this,R.drawable.circle_green)
            else -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_gray)

        }


    }

    private fun initClearButton(){
        clearButton.setOnClickListener {
            pickNumberSet.clear()
            numberTextViewList.forEach {
                it.isVisible = false

            }
            didrun = false //클리어 버튼 누르면 false로 바꿔줘

        }



    }
    private fun initRunButton() { //이것이 자동생성 버튼

        runButton.setOnClickListener {
            val list = getRandomNumber()
            didrun = true //자동생성버튼 클릭하면 true로 바꿔줘

            list.forEachIndexed{index,number->
                val textView = numberTextViewList[index]

                textView.text = number.toString()
                textView.isVisible = true

                setNumberBackground(number,textView)



            }
        }


    }

    private fun getRandomNumber(): List<Int> {

        val numberList = mutableListOf<Int>().apply {

            for (i in 1..45) {

                if(pickNumberSet.contains(i)){
                    continue
                }
                this.add(i)
            }
        }
        numberList.shuffle()

        val newList = pickNumberSet.toList() + numberList.subList(0, 6 - pickNumberSet.size)  //이미 선택된 번호 list 변환환

        return newList.sorted()
    }

}
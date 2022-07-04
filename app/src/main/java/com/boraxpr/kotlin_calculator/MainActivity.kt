package com.boraxpr.kotlin_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.allViews
import androidx.core.view.iterator

import com.boraxpr.kotlin_calculator.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    enum class Screen {
        inputScreen, historyScreen
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        SET VIEW BINDING (Init from build.gradle android Features)
//        READ MORE : https://developer.android.com/topic/libraries/view-binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        HIDE NAVIGATION BAR
        window.decorView.windowInsetsController!!.hide(WindowInsetsCompat.Type.navigationBars())

//        GET bind views
        val button1 = binding.button1
        val button2 = binding.button2
        val button3 = binding.button3
        val button4 = binding.button4
        val button5 = binding.button5
        val button6 = binding.button6
        val button7 = binding.button7
        val button8 = binding.button8
        val button9 = binding.button9
        val button0 = binding.button0
        val buttonDecimal = binding.buttonDecimal
        val buttonAC = binding.buttonReset
        val buttonNegative = binding.buttonNegative
        val buttonModulo = binding.buttonModulo
        val buttonDivide = binding.buttonDivide
        val buttonMultiply = binding.buttonMultiply
        val buttonMinus = binding.buttonMinus
        val buttonPlus = binding.buttonAdd
        val buttonEqual = binding.buttonEqual

        val buttonArr = arrayOf(
            button0,
            button1,
            button2,
            button3,
            button4,
            button5,
            button6,
            button7,
            button8,
            button9,
            buttonDecimal,
            buttonAC,
            buttonNegative,
            buttonModulo,
            buttonDivide,
            buttonMultiply,
            buttonMinus,
            buttonPlus,
            buttonEqual
        )

        val textInputScreen = binding.textViewInputScreen
        val textHistoryScreen = binding.textViewHistory
        //Calculation-related variables
        var operandA: Float? = null
        var operandB = null
        var operation: MaterialButton? = null
        var temp = 0
        var continuous = 0
        var cont_value = 0

        for (button in buttonArr) {
            button.setOnClickListener {
                if (button.text.equals(buttonAC.text)) {
                    textInputScreen.text = ""
                    operandA = null
                    operandB = null
                    operation = null
                    temp = 0
                    continuous = 0
                    cont_value = 0
                } //INPUTS
                else if (button.text.isDigitsOnly()) {
                    textInputScreen.append(button.text.toString())
                } //NEGATIVE
                else if (button == buttonNegative) {
                    if (textInputScreen.text.startsWith("-")) {
                        textInputScreen.text = textInputScreen.text.removePrefix("-")
                    }
                    else {
                        textInputScreen.text = textInputScreen.text.padStart(textInputScreen.length()+1,"-".single())
                    }
                } //OPERATIONS
                else if (button == buttonModulo || button == buttonDivide || button == buttonPlus
                    || button == buttonMinus || button == buttonMultiply){
                    operandA = textInputScreen.text.toString().toFloat()
                    operation = button
                    textInputScreen.text = ""
                }
                else if (button == buttonDecimal && !textInputScreen.text.contains(".")){
                    textInputScreen.append(button.text.toString())
                }
            }
        }
    }

    //        SET inputScreen value
//        button0.setOnClickListener{
//            appendToInput("0")
//        }
//        button1.setOnClickListener{
//            appendToInput("1")
//        }
//        button2.setOnClickListener{
//            appendToInput("2")
//        }
//        button3.setOnClickListener{
//            a
//        }
//        button4.setOnClickListener{
//
//        }

}



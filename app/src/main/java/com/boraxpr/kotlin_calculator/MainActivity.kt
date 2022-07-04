package com.boraxpr.kotlin_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.isDigitsOnly
import androidx.core.view.WindowInsetsCompat
import com.boraxpr.kotlin_calculator.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import kotlin.math.ceil


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        SET VIEW BINDING (Init from build.gradle android Features)
//        READ MORE : https://developer.android.com/topic/libraries/view-binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        HIDE NAVIGATION BAR
//        window.decorView.windowInsetsController!!.hide(WindowInsetsCompat.Type.navigationBars())

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
        var operandB: Float? = null
        var operation: MaterialButton? = null
        var continuous = 0
        var contValue : Float? = null

        for (button in buttonArr) {
            button.setOnClickListener {
                if (button.text.equals(buttonAC.text)) {
                    textInputScreen.text = ""
                    operandA = null
                    operandB = null
                    operation = null
                    continuous = 0
                    contValue = null
                } //INPUTS
                else if (button.text.isDigitsOnly()) {
                    textInputScreen.append(button.text.toString())
                } //NEGATIVE
                else if (button == buttonNegative && textInputScreen.text.isNotEmpty()) {
                    if (textInputScreen.text.startsWith("-")) {
                        textInputScreen.text = textInputScreen.text.removePrefix("-")
                    }
                    else {
                        textInputScreen.text = textInputScreen.text.padStart(textInputScreen.length()+1,"-".single())
                    }
                } //OPERATIONS
                else if (button == buttonModulo || button == buttonDivide || button == buttonPlus
                    || button == buttonMinus || button == buttonMultiply){

                    if(textInputScreen.text.isNotEmpty()){
                        operandA = textInputScreen.text.toString().toFloat()
                    }
                    operation = button
                    textInputScreen.text = ""
                } //DECIMAL
                else if (button == buttonDecimal && !textInputScreen.text.contains(".")){
                    textInputScreen.append(button.text.toString())
                } //CALCULATION TODO: Bug Fix After show a History, Pressing an Equal crashes the app
                else if (button == buttonEqual){
                    if (operandA == null && operandB == null){
                        textInputScreen.text = ""
                    }
                    else {
                        if (operandB == null && continuous == 0){
                            operandB = textInputScreen.text.toString().toFloat()
                            continuous = 1
                        }
                        else {
                            operandA = contValue
                        }
                        var showA = operandA.toString()
                        var showB = operandB.toString()
                        //TODO:Not working properly. whole values still show decimal
                        if (!isWhole(operandA!!)){
                            showA = "%.3f".format(operandA)
                        }
                        if (!isWhole(operandB!!)){
                            showB = "%.3f".format(operandB)
                        }

                        textHistoryScreen.text = getString(R.string.history, showA, operation!!.text, showB)
                        //TODO:Calculations and show result on the input screen
                    }
                }
            }
        }
    }
    private fun isWhole(float: Float): Boolean {
        return float.rem(1).equals(0.0)
    }
}



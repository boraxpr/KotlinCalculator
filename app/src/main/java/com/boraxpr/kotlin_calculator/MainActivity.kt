package com.boraxpr.kotlin_calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.boraxpr.kotlin_calculator.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import java.math.BigInteger


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
        var operandA: Double? = null
        var operandB: Double? = null
        var operation: MaterialButton? = null
        var continuous = 0
        var contValue : Double? = null
        for (button in buttonArr) {
            button.setOnClickListener {
                if (button.text.equals(buttonAC.text)) {
                    textInputScreen.text = ""
                    textHistoryScreen.text = ""
                    operandA = null
                    operandB = null
                    operation = null
                    continuous = 0
                    contValue = null
                } //INPUTS
                else if (button.text.isDigitsOnly() && textInputScreen.text.removePrefix("-").length < 9) {
//                    When typed a zero first into a whole number, ignore zero. Allow a zero only when followed by a decimal
                    if (textInputScreen.text.toString() == "0" && isWhole(textInputScreen.text.toString().toDouble())){
                        textInputScreen.text = ""
                    }
                    textInputScreen.append(button.text.toString())
                    //Continuous calculation : Catch any changed operandB
                    if (contValue != null) {
                        operandB = textInputScreen.text.toString().toDouble()
                    }
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
                        operandA = textInputScreen.text.toString().toDouble()
                    }
                    operation = button
                    textInputScreen.text = ""
                } //DECIMAL
                else if (button == buttonDecimal && !textInputScreen.text.contains(".")){
                    textInputScreen.append(button.text.toString())
                } //CALCULATION
                else if (button == buttonEqual){

                    if (operandA == null && operandB == null){
                        textInputScreen.text = ""
                        textHistoryScreen.text = ""
                    }
                    else {
                        if (operandB == null && continuous == 0){
                            operandB = textInputScreen.text.toString().toDouble()
                            continuous = 1
                        }
                        else {
                            operandA = contValue
                        }

                        // Dealing with rounding (Limit to 3 decimal places) and if Whole number,then not showing decimal places
                        val showA = digitsFormatting(operandA!!)
                        val showB = digitsFormatting(operandB!!)

                        textHistoryScreen.text = getString(R.string.history, showA, operation!!.text, showB)

                        //Calculate and Show output on the screen
                        val calculation = calculate(operation!!,operandA!!,operandB!!)
                        textInputScreen.text = digitsFormatting(calculation)
                        contValue = calculation
                    }
                }
            }
        }
    }
    private fun isWhole(float: Double): Boolean {
        return float % 1 == 0.0
    }
    private fun digitsFormatting(float: Double): String {
        var digits = float.toString()
//        if (!isWhole(float) && digits.split(".")[1].length > 3){
//            digits = "%.3f".format(float)
//        }
//        if (isWhole(float)){
//            digits =  float.toString()
//        }
        return digits
    }
    private fun calculate(operation: MaterialButton, operandA: Double, operandB: Double): Double{
        var calculation = 0.0
        val buttonModulo = binding.buttonModulo
        val buttonDivide = binding.buttonDivide
        val buttonMultiply = binding.buttonMultiply
        val buttonMinus = binding.buttonMinus
        val buttonPlus = binding.buttonAdd
        when (operation) {
            buttonPlus -> {
                calculation = operandA + operandB
            }
            buttonMinus -> {
                calculation = operandA - operandB
            }
            buttonMultiply -> {
                calculation = operandA * operandB
            }
            buttonDivide -> {
                calculation = operandA / operandB
            }
            buttonModulo -> {
                calculation = operandA % operandB
            }
        }
        return calculation
    }
}



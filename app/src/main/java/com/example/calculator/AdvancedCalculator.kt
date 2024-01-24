package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.pow
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class AdvancedCalculator : AppCompatActivity() {
    private var operationPerformed = false
    private var isCClicked = false
    private var firstNum = 0.0
    private var secondNum = 0.0
    private var operation: String? = null
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advanced_calculator)

        result = findViewById(R.id.textView2)

        // Inicjalizacja obsługi zdarzeń dla przycisków
        numbersService()
        operatorsService()
        othersService()
        proOperatorsService()

        // Przywracanie stanu po zmianie orientacji urządzenia
        if (savedInstanceState != null) {
            firstNum = savedInstanceState.getDouble("firstNum")
            operation = savedInstanceState.getString("operation")
            operationPerformed = savedInstanceState.getBoolean("operationPerformed")
            isCClicked = savedInstanceState.getBoolean("isCClicked")
            result.text = savedInstanceState.getString("resultText")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("firstNum", firstNum)
        outState.putString("operation", operation)
        outState.putBoolean("operationPerformed", operationPerformed)
        outState.putBoolean("isCClicked", isCClicked)
        outState.putString("resultText", result.text.toString())
    }

    // Obsługa przycisków z cyframi oraz kropką
    private fun numbersService() {
        val n0: Button = findViewById(R.id.btn_0)
        val n1: Button = findViewById(R.id.btn_1)
        val n2: Button = findViewById(R.id.btn_2)
        val n3: Button = findViewById(R.id.btn_3)
        val n4: Button = findViewById(R.id.btn_4)
        val n5: Button = findViewById(R.id.btn_5)
        val n6: Button = findViewById(R.id.btn_6)
        val n7: Button = findViewById(R.id.btn_7)
        val n8: Button = findViewById(R.id.btn_8)
        val n9: Button = findViewById(R.id.btn_9)
        val dot: Button = findViewById(R.id.btn_dot)

        val numberClickListener = View.OnClickListener { view ->
            val currentText = result.text.toString()
            if (operationPerformed || currentText == "0") {
                result.text = (view as Button).text.toString()
                operationPerformed = false
            } else {
                result.text = currentText + (view as Button).text.toString()
            }
        }

        n1.setOnClickListener(numberClickListener)
        n2.setOnClickListener(numberClickListener)
        n3.setOnClickListener(numberClickListener)
        n4.setOnClickListener(numberClickListener)
        n5.setOnClickListener(numberClickListener)
        n6.setOnClickListener(numberClickListener)
        n7.setOnClickListener(numberClickListener)
        n8.setOnClickListener(numberClickListener)
        n9.setOnClickListener(numberClickListener)
        n0.setOnClickListener(numberClickListener)

        dot.setOnClickListener {
            if (!result.text.toString().contains(".")) {
                result.text = result.text.toString() + "."
            }
        }
    }

    // Obsługa przycisków dla zaawansowanych operacji
    private fun proOperatorsService() {
        val sinButton: Button = findViewById(R.id.btn_sin)
        val cosButton: Button = findViewById(R.id.btn_cos)
        val tanButton: Button = findViewById(R.id.btn_tan)
        val lnButton: Button = findViewById(R.id.btn_ln)
        val sqrtButton: Button = findViewById(R.id.btn_sqrt)
        val squareButton: Button = findViewById(R.id.btn_x_2)
        val powerButton: Button = findViewById(R.id.btn_x_n)
        val logButton: Button = findViewById(R.id.btn_log)

        sinButton.setOnClickListener {
            performUnaryOperation("sin")
        }

        cosButton.setOnClickListener {
            performUnaryOperation("cos")
        }

        tanButton.setOnClickListener {
            performUnaryOperation("tan")
        }

        lnButton.setOnClickListener {
            performUnaryOperation("ln")
        }

        sqrtButton.setOnClickListener {
            performUnaryOperation("sqrt")
        }

        squareButton.setOnClickListener {
            performUnaryOperation("x^2")
        }

        powerButton.setOnClickListener {
            val base = result.text.toString().toDoubleOrNull()
            if (base == null){
                showErrorMessage("Najpierw wybierz liczbę!")
                return@setOnClickListener
            }
            operation = "power"
            firstNum = base
            result.text = ""
            operationPerformed = true
        }

        logButton.setOnClickListener {
            performUnaryOperation("log")
        }
    }

    // Obsługa operacji jednoargumentowych (sin, cos, tan, ln, sqrt, x^2, log)
    private fun performUnaryOperation(operation: String) {
        if (!result.text.isBlank()) {
            val value = result.text.toString().toDoubleOrNull()
            showErrorMessageOnNull(value)

            if (value == null) {
                showErrorMessage("Najpierw wybierz liczbę!")
                return
            }

            val resultValue = when (operation) {
                "sin" -> sin(Math.toRadians(value))
                "cos" -> cos(Math.toRadians(value))
                "tan" -> tan(Math.toRadians(value))
                "ln" -> {
                    if (value <= 0) {
                        showErrorMessage("Logarytm niezdefiniowany dla wartości mniejszych lub równych 0!")
                        return
                    } else {
                        ln(value)
                    }
                }
                "sqrt" -> {
                    if (value < 0) {
                        showErrorMessage("Nie istnieje pierwiastek z liczby ujemnej!")
                        return
                    }
                    sqrt(value)
                }
                "x^2" -> pow(value, 2.0)
                "log" -> {
                    if (value <= 0) {
                        showErrorMessage("Logarytm niezdefiniowany dla wartości mniejszych lub równych 0!")
                        return
                    }
                    log10(value)
                }
                else -> value
            }

            result.text = resultValue.toString()
            operationPerformed = true
        }
    }

    // Obsługa przycisków CE, C, oraz zmiana znaku
    private fun othersService() {
        val c: Button = findViewById(R.id.btn_c_ce)
        val reset: Button = findViewById(R.id.btn_reset)
        val plusMinus: Button = findViewById(R.id.btn_plus_minus)

        c.setOnClickListener {
            if (isCClicked) {
                secondNum = 0.0
                result.text = "0"
                isCClicked = false
            } else {
                result.text = "0"
                isCClicked = true
            }
        }

        reset.setOnClickListener {
            firstNum = 0.0
            result.text = "0"
        }

        plusMinus.setOnClickListener {
            val currentValue = result.text.toString().toDoubleOrNull()
            if (currentValue == null) {
                showErrorMessage("Najpierw wybierz liczbę!")
                return@setOnClickListener
            }
            val newValue = -currentValue
            result.text = newValue.toString()
        }
    }

    // Obsługa przycisków operatorów (+, -, *, /, =)
    private fun operatorsService() {
        val add: Button = findViewById(R.id.btn_plus)
        val minus: Button = findViewById(R.id.btn_minus)
        val multiple: Button = findViewById(R.id.btn_multiply)
        val divide: Button = findViewById(R.id.btn_divide)
        val equal: Button = findViewById(R.id.btn_equal)

        val operatorClickListener = View.OnClickListener { view ->
            if (result.text.toString().isNotEmpty()) {
                val currentValue = result.text.toString().toDouble()

                // Sprawdzenie, czy operacja to odejmowanie i aktualna liczba to zero
                if ((view as Button).text.toString() == "-" && currentValue == 0.0) {
                    showErrorMessage("Nie można odejmować od zera!")
                    return@OnClickListener
                }

                firstNum = currentValue
                operation = (view as Button).text.toString()
                operationPerformed = true
            } else {
                showErrorMessage("Podaj liczbę przed użyciem operatora!")
            }
        }

        add.setOnClickListener(operatorClickListener)
        minus.setOnClickListener(operatorClickListener)
        multiple.setOnClickListener(operatorClickListener)
        divide.setOnClickListener(operatorClickListener)

        equal.setOnClickListener {
            if (operation == "power") {
                val exponent = result.text.toString().toDouble()
                val resultValue = Math.pow(firstNum, exponent)
                result.text = resultValue.toString()
                operation = ""
                operationPerformed = true
            } else {
                if (result.text.toString().isNotEmpty()) {
                    secondNum = result.text.toString().toDouble()

                    if (operation == ":" && secondNum == 0.0) {
                        showErrorMessage("Nie można dzielić przez zero!")
                        return@setOnClickListener
                    }

                    val score: Double = when (operation) {
                        ":" -> firstNum / secondNum
                        "x" -> firstNum * secondNum
                        "+" -> firstNum + secondNum
                        "-" -> firstNum - secondNum
                        else -> firstNum + secondNum
                    }
                    result.text = score.toString()
                    firstNum = score
                    operationPerformed = true
                } else {
                    showErrorMessage("Podaj liczbę przed użyciem operatora!")
                }
            }
        }
    }

    // Wyświetlanie komunikatu o błędzie
    private fun showErrorMessageOnNull(value: Double?) {
        if (value == null) {
            showErrorMessage("Najpierw wybierz liczbę!")
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

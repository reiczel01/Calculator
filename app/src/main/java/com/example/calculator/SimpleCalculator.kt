package com.example.calculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SimpleCalculator : AppCompatActivity() {
    private var operationPerformed = false
    private var isOperatorClicked = false
    private var firstNum = 0.0
    private var secondNum = 0.0
    private var operation: String? = null
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_calculator)

        result = findViewById(R.id.textView2)

        // znalezienie odpowiednich widżetów w pliku layout
        val n0 = findViewById<Button>(R.id.btn_0)
        val n1 = findViewById<Button>(R.id.btn_1)
        val n2 = findViewById<Button>(R.id.btn_2)
        val n3 = findViewById<Button>(R.id.btn_3)
        val n4 = findViewById<Button>(R.id.btn_4)
        val n5 = findViewById<Button>(R.id.btn_5)
        val n6 = findViewById<Button>(R.id.btn_6)
        val n7 = findViewById<Button>(R.id.btn_7)
        val n8 = findViewById<Button>(R.id.btn_8)
        val n9 = findViewById<Button>(R.id.btn_9)

        val c = findViewById<Button>(R.id.btn_c_ce)
        val reset = findViewById<Button>(R.id.btn_reset)
        val plusMinus = findViewById<Button>(R.id.btn_plus_minus)
        val dot = findViewById<Button>(R.id.btn_dot)

        val add = findViewById<Button>(R.id.btn_plus)
        val subtract = findViewById<Button>(R.id.btn_minus)
        val multiply = findViewById<Button>(R.id.btn_multiply)
        val divide = findViewById<Button>(R.id.btn_divide)
        val equal = findViewById<Button>(R.id.btn_equal)

        // przypisanie funkcji do zdarzeń kliknięcia odpowiednich przycisków
        n0.setOnClickListener { appendNumber("0") }
        n1.setOnClickListener { appendNumber("1") }
        n2.setOnClickListener { appendNumber("2") }
        n3.setOnClickListener { appendNumber("3") }
        n4.setOnClickListener { appendNumber("4") }
        n5.setOnClickListener { appendNumber("5") }
        n6.setOnClickListener { appendNumber("6") }
        n7.setOnClickListener { appendNumber("7") }
        n8.setOnClickListener { appendNumber("8") }
        n9.setOnClickListener { appendNumber("9") }

        c.setOnClickListener { clear() }// funkcja wyczyszczenia drugiej wprowadznoej liczby
        reset.setOnClickListener { reset() }// funkcja zresetowania kalkulatora do wartości domyślnych
        plusMinus.setOnClickListener { flipSign() } // funkcja zmiany znaku liczby na ekranie
        dot.setOnClickListener { addDecimalPoint() }// funkcja dodania kropki dziesiętnej do liczby na ekranie

        add.setOnClickListener { performOperation("+") }
        subtract.setOnClickListener { performOperation("-") }
        multiply.setOnClickListener { performOperation("x") }
        divide.setOnClickListener { performOperation(":") }

        equal.setOnClickListener { calculate() }

        // Przywracanie stanu po zmianie orientacji urządzenia
        if (savedInstanceState != null) {
            firstNum = savedInstanceState.getDouble("firstNum")
            operation = savedInstanceState.getString("operation")
            operationPerformed = savedInstanceState.getBoolean("operationPerformed")
            isOperatorClicked = savedInstanceState.getBoolean("isOperatorClicked")
            result.text = savedInstanceState.getString("resultText")
        }
    }

    // dodaje liczbę do obecnie wyświetlonego ekranu
    private fun appendNumber(number: String) {
        // jeśli aktualny tekst na ekranie jest pusty, to ustaw go równy wprowadzonej cyfrze
        if (result.text.isBlank()) {
            result.text = number
        } else {
            // w przeciwnym razie dołącz wprowadzoną cyfrę do aktualnego tekstu na ekranie
            result.append(number)
        }
    }

    private fun clear() {
        result.text = ""
        secondNum = 0.0
        operationPerformed = false
        isOperatorClicked = false
    }

    private fun reset() {
        clear()
        firstNum = 0.0
        operation = null
        isOperatorClicked = true
    }

    private fun flipSign() {
        if (result.text.isNotBlank()) {
            result.text = (-result.text.toString().toDouble()).toString()
        }
    }

    private fun addDecimalPoint() {
        if (!result.text.contains(".")) {
            result.append(".")
        }else{
            showErrorMessage("Liczba zawiera już kropkę dziesiętną!")
        }
    }

    private fun performOperation(operation: String) {
        if (this.operation == null && !result.text.isBlank()) {
            this.operation = operation
            firstNum = result.text.toString().toDouble()
            result.text = ""
        }
    }

    private fun calculate() {
        if (operation != null && !result.text.isBlank()) {
            secondNum = result.text.toString().toDouble()

            val score: Double = when (operation) {
                ":" -> {
                    if (secondNum == 0.0) {
                        showErrorMessage("Dzielenie przez zero jest niemożliwe!")
                        return
                    }
                    firstNum / secondNum
                }
                "x" -> firstNum * secondNum
                "+" -> firstNum + secondNum
                "-" -> firstNum - secondNum
                else -> {
                    showErrorMessage("Nieznana operacja: $operation")
                    return
                }
            }

            result.text = score.toString()
            firstNum = score
            operationPerformed = true
            operation = null // Zresetuj operację po obliczeniu wyniku
        }
    }


    // zapisuje aktualny stan aplikacji w przypadku przerwania jej działania
    override fun onSaveInstanceState(outState: Bundle) {
        // wywołanie metody superclass, aby zapisać aktualny stan aplikacji
        super.onSaveInstanceState(outState)
        // zapisanie zmiennych w buforze danych (Bundle) do odtworzenia ich później
        outState.putDouble("firstNum", firstNum)
        outState.putString("operation", operation)
        outState.putBoolean("operationPerformed", operationPerformed)
        outState.putBoolean("isOperatorClicked", isOperatorClicked)
        outState.putString("resultText", result.text.toString())
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
package com.example.slotmachinegame

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.Random

class MainActivity : AppCompatActivity() {
    private var balance : Int = 10
    private var betAmount : Int = 1
    private var firstNum : Int = 1
    private var secondNum : Int = 2
    private var thirdNum : Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun playButton(view: View) {
        val messageTV = findViewById<TextView>(R.id.message)
        var message : String
        var colorRed : Boolean

        if (betAmount > balance) {
            message = "Insufficient funds for $$betAmount bet"
            colorRed = true
        } else {
            getRandomNumber()
            if (firstNum == secondNum && firstNum == thirdNum) {
                balance += 5 * betAmount
                message = "Hooray! You've won"
                colorRed = false
            } else {
                balance -= betAmount
                message = "You lost! Try again"
                colorRed = true
            }
        }
        findViewById<TextView>(R.id.balance_num).text = "$$balance"
        if (balance == 0) {
            message = "You lost, game is over!"
            colorRed = true
        }
        messageTV.text = message
        if (colorRed) {
            messageTV.setTextColor(Color.parseColor("#F34336"))
        } else {
            messageTV.setTextColor(Color.parseColor("#4CAE50"))
        }
        checkButtonStatus()
    }
    fun changeBetAmount(view: View) {
        if (view.id == R.id.plus_button && betAmount < balance) {
            betAmount++
        } else if (view.id == R.id.minus_button && betAmount > 1) {
            if (betAmount > balance) {
                betAmount = balance
            } else {
                betAmount--
            }
        }
        findViewById<TextView>(R.id.bet_amount_num).text = "$$betAmount"
        checkButtonStatus()
    }
    fun resetButton(view: View) {
        balance = 10
        betAmount = 1
        firstNum = 1
        secondNum = 2
        thirdNum = 3
        displayResult()
        findViewById<TextView>(R.id.balance_num).text = "$$balance"
        findViewById<TextView>(R.id.bet_amount_num).text = "$$betAmount"
        findViewById<TextView>(R.id.message).text = ""
        checkButtonStatus()
    }
    private fun getRandomNumber() {
        firstNum = generateRandomNumber()
        secondNum = generateRandomNumber()
        thirdNum = generateRandomNumber()
        displayResult()
    }
    private fun generateRandomNumber() : Int{
        return Random().nextInt(9)+1
    }
    private fun displayResult() {
        findViewById<TextView>(R.id.first_number).text = firstNum.toString()
        findViewById<TextView>(R.id.second_number).text = secondNum.toString()
        findViewById<TextView>(R.id.third_number).text = thirdNum.toString()
    }
    private fun checkButtonStatus() {
        val plusButton = findViewById<Button>(R.id.plus_button)
        val minusButton = findViewById<Button>(R.id.minus_button)
        val playButton = findViewById<Button>(R.id.play_button)

        if (balance == 0) {
            disableButton(playButton)
            disableButton(plusButton)
            disableButton(minusButton)
        } else {
            playButton.setBackgroundResource(R.drawable.blue_button)
            playButton.isEnabled = true
            if (betAmount >= balance) {
                disableButton(plusButton)
            } else {
                plusButton.setBackgroundResource(R.drawable.green_button)
                plusButton.isEnabled = true
            }
            if (betAmount == 1) {
                disableButton(minusButton)
            } else {
                minusButton.setBackgroundResource(R.drawable.red_button)
                minusButton.isEnabled = true
            }
        }
    }
    private fun disableButton(button: Button) {
        button.setBackgroundResource(R.drawable.grey_button)
        button.isEnabled = false
    }
}
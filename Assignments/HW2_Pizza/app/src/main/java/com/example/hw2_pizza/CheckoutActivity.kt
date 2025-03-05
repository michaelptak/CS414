package com.example.hw2_pizza

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BundleCompat


class CheckoutActivity : AppCompatActivity() {
    private var quantity = 1
    private lateinit var pizza: Pizza


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val deliverySwitch = findViewById<Switch>(R.id.deliverySwitch)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)

        // Edit pizza by returning to first activity
        findViewById<Button>(R.id.editPizzaButton).setOnClickListener {
            finish()
        }

        // Delivery fee switch functionality
        deliverySwitch.setOnCheckedChangeListener { _, isChecked ->
            val deliveryFee = if (isChecked) 2.00 else 0.00
            deliverySwitch.text = if (isChecked) "Yes, $2.00" else "No, $0.00"
            updateTotal()
        }

        // Seek bar functionality
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Order button
        findViewById<Button>(R.id.orderButton).setOnClickListener {
            val totalPrice = findViewById<TextView>(R.id.totalAmount).text.toString()
            val resultIntent = Intent().apply {
                putExtra("FINAL_TOTAL", totalPrice)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        // Receive Pizza data from firs activity
        val bundle = intent.extras
        if (bundle != null) {
            val receivedPizza = BundleCompat.getSerializable(bundle, "PIZZA_DATA", Pizza::class.java)

            if (receivedPizza != null) {
                pizza = receivedPizza

                findViewById<TextView>(R.id.checkoutType).text = pizza.type
                findViewById<TextView>(R.id.checkoutSize).text = pizza.size
                findViewById<TextView>(R.id.checkoutNumOfToppings).text = "${pizza.toppingCount.toString()} Toppings"
                findViewById<ImageView>(R.id.checkoutPizzaImage).setImageResource(pizza.imageId)

                updateSubtotal()

                findViewById<Button>(R.id.addPizzaButton).setOnClickListener {
                    quantity++
                    updateSubtotal()
                }

                findViewById<Button>(R.id.reducePizzaButton).setOnClickListener {
                    quantity--
                    updateSubtotal()
                }
            }
        }
    }

    private fun updateSubtotal() {
        val subtotal = pizza.price * quantity
        findViewById<TextView>(R.id.checkoutSubtotal).text = String.format("$%.2f", subtotal)
        findViewById<TextView>(R.id.checkoutQuantity).text = quantity.toString()

        updateTotal()
    }

    private fun updateTotal() {
        val subtotal = pizza.price * quantity
        val deliveryFee = if (findViewById<Switch>(R.id.deliverySwitch).isChecked) 2.00 else 0.00
        val tipPercentage = findViewById<SeekBar>(R.id.seekBar).progress
        val tipAmount = subtotal * (tipPercentage / 100.0)

        val taxableAmount = subtotal + deliveryFee
        val taxAmount = taxableAmount * 0.0635

        findViewById<TextView>(R.id.tipAmount).text = String.format("$%.2f (%d%%)", tipAmount, tipPercentage)
        findViewById<TextView>(R.id.taxAmount).text = String.format("$%.2f", taxAmount)

        val total = subtotal + deliveryFee + taxAmount + tipAmount
        findViewById<TextView>(R.id.totalAmount).text = String.format("$%.2f", total)
    }

}
package com.example.hw2_pizza

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var selectedPizzaType: String = ""
    private var selectedSize: String = ""
    private var toppingCount: Int = 0
    private var basePrice: Double = 0.0

    private lateinit var toppingCheckboxes: List<CheckBox>
    private lateinit var subtotalText: TextView

    // Handling data from "order button" in second activity
    private val checkoutLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val totalPrice = result.data?.getStringExtra("FINAL_TOTAL")
            Toast.makeText(this, "Order placed! Total: $totalPrice", Toast.LENGTH_LONG).show()
            resetOrder()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pizzaTypeGroup = findViewById<RadioGroup>(R.id.pizzaTypeGroup)
        val pizzaImageView = findViewById<ImageView>(R.id.pizzaImageView)
        val sizeRadioGroup = findViewById<RadioGroup>(R.id.sizeRadioGroup)
        subtotalText = findViewById<TextView>(R.id.subtotalTextView)

        toppingCheckboxes = listOf(
            findViewById(R.id.checkbox_tomatoes),
            findViewById(R.id.checkbox_mushrooms),
            findViewById(R.id.checkbox_onions),
            findViewById(R.id.checkbox_olives),
            findViewById(R.id.checkbox_broccoli),
            findViewById(R.id.checkbox_spinach)
        )
        val resetButton = findViewById<Button>(R.id.resetButton)
        resetButton.setOnClickListener { resetOrder() }

        val checkoutButton = findViewById<Button>(R.id.checkoutButton)
        checkoutButton.setOnClickListener {
            if(validateOrder()) {
                checkOut()
            }
        }

        // Select Pizza Type
        pizzaTypeGroup.setOnCheckedChangeListener{ _, checkedId ->
            val newImage = when (checkedId) {
                R.id.pepperoniButton -> R.drawable.pepperoni
                R.id.bbqchickenButton -> R.drawable.bbq_chicken
                R.id.margheritaButton -> R.drawable.margherita
                R.id.hawaiianButton -> R.drawable.hawaiian
                else -> R.drawable.pizza_crust
            }
            pizzaImageView.setImageResource(newImage)
            selectedPizzaType = when (checkedId) {
                R.id.pepperoniButton -> "Pepperoni"
                R.id.bbqchickenButton -> "BBQ Chicken"
                R.id.margheritaButton -> "Margherita"
                R.id.hawaiianButton -> "Hawaiian"
                else -> ""
            }
            updateSubtotal()
        }

        // Select Pizza Size (and therefore base price)
        sizeRadioGroup.setOnCheckedChangeListener{_, checkedId ->
            selectedSize = when (checkedId) {
                R.id.smallButton -> "Small"
                R.id.mediumButton -> "Medium"
                R.id.largeButton -> "Large"
                else -> ""
            }
            basePrice = when (checkedId){
                R.id.smallButton -> 10.29
                R.id.mediumButton -> 12.59
                R.id.largeButton -> 14.89
                else -> 0.0
            }
            updateSubtotal()
        }
        // Select Toppings
        toppingCheckboxes.forEach { checkbox ->
            checkbox.setOnCheckedChangeListener { _, _ ->
                toppingCount = toppingCheckboxes.count { checkbox -> checkbox.isChecked }
                updateSubtotal()
            }
        }

        updateSubtotal()
    }
    private fun resetOrder() {
        findViewById<RadioGroup>(R.id.pizzaTypeGroup).clearCheck()
        findViewById<ImageView>(R.id.pizzaImageView).setImageResource(R.drawable.pizza_crust)
        selectedPizzaType = ""

        findViewById<RadioGroup>(R.id.sizeRadioGroup).clearCheck()
        selectedSize = ""
        basePrice = 0.0

        toppingCheckboxes.forEach { checkbox -> checkbox.isChecked = false }
        toppingCount = 0

        updateSubtotal()
    }

    private fun checkOut() {
        val pizza = Pizza(
            type = selectedPizzaType,
            price = basePrice + (toppingCount * when (selectedSize) {
                "Small" -> 1.39
                "Medium" -> 2.20
                "Large" -> 2.99
                else -> 0.0
            }),
            toppingCount = toppingCount,
            size = selectedSize,
            imageId = when (selectedPizzaType) {
                "Pepperoni" -> R.drawable.pepperoni
                "BBQ Chicken" -> R.drawable.bbq_chicken
                "Margherita" -> R.drawable.margherita
                "Hawaiian" -> R.drawable.hawaiian
                else -> R.drawable.pizza_crust
            }
        )

        val intent = Intent(this, CheckoutActivity::class.java)
        intent.putExtra("PIZZA_DATA", pizza)
        checkoutLauncher.launch(intent)
    }

    private fun validateOrder(): Boolean {
        if (selectedPizzaType.isEmpty()) {
            Toast.makeText(this, "Please select a type for your pizza.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (selectedSize.isEmpty()) {
            Toast.makeText(this, "Please select a size for your pizza.", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun updateSubtotal() {
        val toppingPrice = when (selectedSize) {
            "Small" -> 1.39
            "Medium" -> 2.20
            "Large" -> 2.99
            else -> 0.0
        }
        val total = basePrice + (toppingCount * toppingPrice)
        subtotalText.text = getString(R.string.subtotal, total)
    }
}
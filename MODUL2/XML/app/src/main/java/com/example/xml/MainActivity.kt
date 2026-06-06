package com.example.xml

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.appcompat.app.AppCompatActivity
import com.example.xml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tipOption = listOf("15%", "18%", "20%")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, tipOption)
        binding.ACTipPercent.setAdapter(adapter)
        binding.ACTipPercent.setText(tipOption[0], false)

        fun calculateTip(){
            val billAmount = binding.TIEBill.text.toString().toDoubleOrNull() ?: 0.0
            val tipPercentage = when (binding.ACTipPercent.text.toString()){
                "15%" -> 0.15
                "18%" -> 0.18
                "20%" -> 0.20
                else -> 0.0
            }
            var tipAmount = billAmount * tipPercentage
            if (binding.SRound.isChecked){
                tipAmount = kotlin.math.ceil(tipAmount)
            }
            binding.TVTipAmount.text = "Tip Amount: ${"$"}${"%.2f".format(tipAmount)}"
        }

        binding.TIEBill.addTextChangedListener{calculateTip()}
        binding.ACTipPercent.setOnItemClickListener{_, _, _, _ -> calculateTip()}
        binding.SRound.setOnCheckedChangeListener { _, _ ->  calculateTip()}
    }
}
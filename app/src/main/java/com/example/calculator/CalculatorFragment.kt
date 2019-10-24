package com.example.calculator


import android.app.Activity
import android.content.Context
import android.os.Bundle

import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_calculator.*
import kotlinx.android.synthetic.main.fragment_calculator.view.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.prefs.Preferences

/**
 * A simple [Fragment] subclass.
 */
class CalculatorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)
        return view

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun appendOnExpresstion(string: String, canClear: Boolean) {

        if (textResult.text.isNotEmpty()) {
            textExpression.text = ""
        }

        if (canClear) {
            textResult.text = ""
            textExpression.append(string)
        } else {
            textExpression.append(textResult.text)
            textExpression.append(string)
            textResult.text = ""
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
       textExpression.text = sharedPref?.getString("expression", "0").toString()
        textResult.text = sharedPref?.getString("result", "0").toString()

        //Numbers
        buttonOne.setOnClickListener { appendOnExpresstion("1", true) }
        buttonTwo.setOnClickListener { appendOnExpresstion("2", true) }
        buttonThree.setOnClickListener { appendOnExpresstion("3", true) }
        buttonFour.setOnClickListener { appendOnExpresstion("4", true) }
        buttonFive.setOnClickListener { appendOnExpresstion("5", true) }
        buttonSix.setOnClickListener { appendOnExpresstion("6", true) }
        buttonSeven.setOnClickListener { appendOnExpresstion("7", true) }
        buttonEight.setOnClickListener { appendOnExpresstion("8", true) }
        buttonNine.setOnClickListener { appendOnExpresstion("9", true) }
        buttonZero.setOnClickListener { appendOnExpresstion("0", true) }
        buttonDot.setOnClickListener { appendOnExpresstion(".", true) }

        //Operators
        buttonPlus.setOnClickListener { appendOnExpresstion("+", false) }
        buttonMinus.setOnClickListener { appendOnExpresstion("-", false) }
        buttonMulti.setOnClickListener { appendOnExpresstion("*", false) }
        buttonDiv.setOnClickListener { appendOnExpresstion("/", false) }
        buttonOpen.setOnClickListener { appendOnExpresstion("(", false) }
        buttonClose.setOnClickListener { appendOnExpresstion(")", false) }

        buttonAC.setOnClickListener {
            textExpression.text = ""
            textResult.text = ""
        }

        buttonClear.setOnClickListener {
            val string = textExpression.text.toString()
            if (string.isNotEmpty()) {
                textExpression.text = string.substring(0, string.length - 1)
            }
            textResult.text = ""
        }

        buttonEqual.setOnClickListener {
            try {

                val expression = ExpressionBuilder(textExpression.text.toString()).build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                if (result == longResult.toDouble())
                    textResult.text = longResult.toString()
                else
                    textResult.text = result.toString()

            } catch (e: Exception) {
                Log.d("Exception", " message : " + e.message)
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //handle item clicks of menu
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //get item id to handle item clicks
        val id = item!!.itemId
        //handle item clicks
        if (id == R.id.action_AC) {
            textExpression.text = ""
            textResult.text = ""
        }
        if (id == R.id.action_save) {
            val sharedPref = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
            val editor = sharedPref?.edit()
            editor?.putString("expression", textExpression.text.toString())?.apply()
            editor?.putString("result", textResult.text.toString())?.apply()

        }

        return super.onOptionsItemSelected(item)
    }

}






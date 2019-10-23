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

        val sharedPref = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        view.textExpression.text = sharedPref?.getString("expression", "0").toString()
        view.textResult.text = sharedPref?.getString("result", "0").toString()

        //Numbers
        view.buttonOne.setOnClickListener { appendOnExpresstion("1", true) }
        view.buttonTwo.setOnClickListener { appendOnExpresstion("2", true) }
        view.buttonThree.setOnClickListener { appendOnExpresstion("3", true) }
        view.buttonFour.setOnClickListener { appendOnExpresstion("4", true) }
        view.buttonFive.setOnClickListener { appendOnExpresstion("5", true) }
        view.buttonSix.setOnClickListener { appendOnExpresstion("6", true) }
        view.buttonSeven.setOnClickListener { appendOnExpresstion("7", true) }
        view.buttonEight.setOnClickListener { appendOnExpresstion("8", true) }
        view.buttonNine.setOnClickListener { appendOnExpresstion("9", true) }
        view.buttonZero.setOnClickListener { appendOnExpresstion("0", true) }
        view.buttonDot.setOnClickListener { appendOnExpresstion(".", true) }

        //Operators
        view.buttonPlus.setOnClickListener { appendOnExpresstion("+", false) }
        view.buttonMinus.setOnClickListener { appendOnExpresstion("-", false) }
        view.buttonMulti.setOnClickListener { appendOnExpresstion("*", false) }
        view.buttonDiv.setOnClickListener { appendOnExpresstion("/", false) }
        view.buttonOpen.setOnClickListener { appendOnExpresstion("(", false) }
        view.buttonClose.setOnClickListener { appendOnExpresstion(")", false) }

        view.buttonAC.setOnClickListener {
            textExpression.text = ""
            textResult.text = ""
        }

        view.buttonClear.setOnClickListener {
            val string = textExpression.text.toString()
            if (string.isNotEmpty()) {
                textExpression.text = string.substring(0, string.length - 1)
            }
            textResult.text = ""
        }

        view.buttonEqual.setOnClickListener {
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






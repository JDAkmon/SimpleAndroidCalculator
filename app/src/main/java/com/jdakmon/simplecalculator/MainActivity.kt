package com.jdakmon.simplecalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    //Using nulls saves code, no need for if statements for example
    private var tvInput: TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    //To use on click listeners instead.. (5 lines for each button)
    // private var btnOne : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //To use on click listeners instead
        //btnOne findViewById(R.id.btnOne)
        //btnOne.setOnClickListener{tvInput?.append("1")}
        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        //Because view.text wont work, firstly it has to be type cassed as a button to access the text property
        //view (lowrcased) is getting bassed the View (actual) data
        tvInput?.append((view as Button).text)
        lastNumeric = true //This will be the first flag trigger for onDecimalPoint
        lastDot = false
    }

    //Controls the /,+,*, symbols
    fun onOperator(view: View) {
        //make sure the text exist at first, code executes if not
        tvInput?.text?.let {
            //let gives result as car sequence, convert it to string
            if (lastNumeric && !isOperatorAdded(it.toString())) { //it being the button text, thus checking the string using isOperatorAdded
                tvInput?.append((view as Button).text) //
                //Last input is now an operator
                lastNumeric = false
                lastDot = false
            }
        }
    }

    //Kotlin return type syntax
    private fun isOperatorAdded(value: String): Boolean {
        //Negative numbers are needed so it will be ignore
        return if (value.startsWith("-")) {
            false
        } else {
            //These are the actual operators thus they are counted differently
            value.contains("/") || value.contains("*") || value.contains("+")
                    || value.contains("-")
        }
    }


    //To handle onClicks of button which is a view
    //Each button is a "View" itself
    fun onNum(view: View) {
        //Explanation: the button text is accessible but first it must be know as a button
        tvInput?.append((view as Button).text) //.Text works here because of the view as button
        //Test buttons
        //Toast.makeText(this,"Test",Toast.LENGTH_SHORT).show();
    }

    fun onClear(view: View) {
//        tvInput?.text = ""
        tvInput?.setText(null)
    }

    fun onDecimalPoint(view: View) {
        //Need to use if statements in order make sure decimals get used right
        if (lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true

        }

    }

    //Will be extracting text from buttons thus the view must be passed

    fun onEquals(button: View) {
        //Avoid a double equals user input
        if (lastNumeric) {
            var tvValue = tvInput?.text.toString() //text is a char sequence by default
            var prefix = ""
            try {
                //The program wont work right with negative numbers unless they get removed manually

                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue = tvValue.substring(1) //This removes that negative from the tv
                }
                if(tvValue.contains("-")){
                    //By using lastNumeric it is easy to get the values no matter the length
                    val pullValues = tvValue.split("-")
                    var first = pullValues[0]
                    var second = pullValues[1]

                    //Do do math it is much easier to use doubles instead of strings
                    //Simply changing the "screen" of the calculator with the calculation

//                var result = first.toDouble() - second.toDouble()
//                tvInput?.text = result.toString()

                    if(prefix.isNotEmpty()){
                        first = prefix + first //Adding the negative back
                    }
                    //Using order of operations to my advantage!
                    tvInput?.text = (first.toDouble() - second.toDouble()).toString()
                }else  if(tvValue.contains("-")){

                    val pullValues = tvValue.split("-")
                    var first = pullValues[0]
                    var second = pullValues[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    tvInput?.text = removeZero((first.toDouble() - second.toDouble()).toString())
                }else  if(tvValue.contains("+")){

                    val pullValues = tvValue.split("+")
                    var first = pullValues[0]
                    var second = pullValues[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    tvInput?.text = removeZero((first.toDouble() + second.toDouble()).toString())
                } else  if(tvValue.contains("/")){

                    val pullValues = tvValue.split("/")
                    var first = pullValues[0]
                    var second = pullValues[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    tvInput?.text = removeZero((first.toDouble() / second.toDouble()).toString())
                }else  if(tvValue.contains("*")){

                    val pullValues = tvValue.split("*")
                    var first = pullValues[0]
                    var second = pullValues[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    tvInput?.text = removeZero((first.toDouble() * second.toDouble()).toString())
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace() //
            }
        }
    }//end onEquals
    private fun removeZero(result: String) : String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0,result.length - 2) //0 is one space . is another space
        return value
    }


} //end MainActivity
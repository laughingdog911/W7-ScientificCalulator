package com.hj.scientificcalulator

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.hj.scientificcalulator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val operatorList = listOf('%', '/', '*', '-', '+')

    var result = MutableLiveData("0")
    var expression = MutableLiveData("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.view = this
    }

    private fun opBtnClicked(op: Char){
        if(expression.value!!.isNotEmpty()){
            val last = expression.value?.get(expression.value!!.lastIndex)

            for(o in operatorList){
                if(last == o){
                    expression.value = expression.value!!.dropLast(1)
                    break
                }
            }

            expression.value = "${expression.value!!}${op}"
        } else{
            expression.value = "0${op}"
        }
    }

    private fun calc(){
        val expressionBuilder = ExpressionBuilder("${expression.value}").build()
        val resultValue = expressionBuilder.evaluate()
    }

    fun onClick(v: View){
        when(v){
            binding.btnAc -> {
                expression.value = ""
                result.value = "0"
            }

            binding.btnDelete -> {
                if(expression.value!!.isNotEmpty()){
                    if(result.value!!.isNotEmpty() && result.value!![result.value!!.length - 1] == expression.value!![expression.value!!.length - 1]){
                        result.value = result.value?.substring(0, result.value!!.length - 1)
                    }

                    expression.value = expression.value?.substring(0, expression.value!!.length - 1)
                }
            }


            binding.btnDivide -> {
                calc()
                opBtnClicked('/')
            }

            binding.btnMultiply -> {
                calc()
                opBtnClicked('*')
            }

            binding.btnMinus -> {
                calc()
                opBtnClicked('-')
            }

            binding.btnPlus -> {
                calc()
                opBtnClicked('+')
            }

            binding.btnPoint -> {
                if(expression.value!!.isNotEmpty() && expression.value!![expression.value!!.length - 1] != '.'){
                    opBtnClicked('.')
                    result.value += "."
                } else if(expression.value!!.isEmpty()){
                    expression.value = "0."
                    result.value += "."
                }
            }

            binding.btnEqual -> {
                calc()
            }

            binding.btnOpenParenthesis -> {
                //TODO: add to stack
            }

            binding.btnCloseParenthesis -> {
                //TODO: add to stack
            }
            else -> {
                if(expression.value!!.isNotEmpty()){
                    for(op in operatorList){
                        if(expression.value!![expression.value!!.length-1] == op){
                            result.value = "0"
                        }
                    }
                }

                val id = v.resources.getResourceName(v.id)
                expression.value += id[id.lastIndex]

                if(result.value == "0"){
                    result.value = id[id.lastIndex].toString()
                } else{
                    result.value += id[id.lastIndex]
                }
            }
        }

    }
}
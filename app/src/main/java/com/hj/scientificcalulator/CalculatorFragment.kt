package com.hj.scientificcalulator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.hj.scientificcalulator.databinding.ActivityMainBinding
import com.hj.scientificcalulator.databinding.FragmentCalculatorBinding
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorFragment(): Fragment(){
    private lateinit var binding: FragmentCalculatorBinding

    private val operatorList = listOf('%', '/', '*', '-', '+')

    var result = MutableLiveData("0")
    var expression = MutableLiveData("")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false)
        binding.lifecycleOwner = this
        binding.view = this

        return binding.root
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

    private fun parenBtnClicked(p: Char){                       //새로운 함수 추가:
        var parentheses = ArrayDeque<String>()

        for (p in parentheses){
            if(p == "("){
                parentheses.addFirst("(")
                expression.value = "${expression.value!!}${p}"
            }else if (p == ")"){
                parentheses.removeFirstOrNull()
                expression.value = "${expression.value!!}${p}"
            }

        }

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
                opBtnClicked('/')
            }

            binding.btnMultiply -> {

                opBtnClicked('*')
            }

            binding.btnMinus -> {

                opBtnClicked('-')
            }

            binding.btnPlus -> {

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
                val expressionBuilder = ExpressionBuilder("${expression.value}").build() //여기에다가 calc() 함수 넣었고,
                result.value = expressionBuilder.evaluate().toString()
            }

            binding.btnOpenParenthesis -> {                                                       //여기에다가 각각 (, ) 넣었고
                parenBtnClicked('(')
            }

            binding.btnCloseParenthesis -> {
                parenBtnClicked(')')
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
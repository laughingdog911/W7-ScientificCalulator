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
    private var parentheses = ArrayDeque<String>()

    var result = MutableLiveData("0")
    var expression = MutableLiveData("")

    private val last = expression.value?.get(expression.value!!.lastIndex)

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
        if(parentheses.isEmpty()) {                             //만약 괄호 배열이 비었으면:

            for (p in parentheses) {
                if (p == "(") {                                 //이게 밑에도 반복되어서 좀..그르네?
                    parentheses.addFirst("(")
                    expression.value = "${expression.value!!}${p}"
                } else if (p == ")") {
                    //Toast.makeText(context as MainActivity, "Wrong Equation.", Toast.LENGTH_SHORT).show()
                    //무시....를 어떻게 하지? 그냥 냅두...?
                }
            }
        }else {                                                  //괄호 배열에 (이 아직 있다면:
            for (p in parentheses) {
                if (p == "(") {                                 //괄호 (은 그냥 추가 가능.
                    parentheses.addFirst("(")
                    expression.value = "${expression.value!!}${p}"
                }else if (p == ")")                             //괄호 )는 head 제거.
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
                val expressionBuilder = ExpressionBuilder("${expression.value}").build() //여기에다가 calc() 함수 넣었는데 뺄까 다시

                if(parentheses.isNotEmpty()){
                    Toast.makeText(context as MainActivity, "Incorrect Input.", Toast.LENGTH_SHORT).show() //Toast도 함수 만들까,
                }else if(expression.value!!.isNotEmpty() && expression.value!![expression.value!!.length - 1] in operatorList){
                    Toast.makeText(context as MainActivity, "Incorrect Input.", Toast.LENGTH_SHORT).show()
                }

                result.value = expressionBuilder.evaluate().toString()
            }

            binding.btnOpenParenthesis -> {
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
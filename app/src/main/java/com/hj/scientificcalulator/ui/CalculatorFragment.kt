package com.hj.scientificcalulator.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.android.material.button.MaterialButton
import com.hj.scientificcalulator.Helper.CalcHelper
import com.hj.scientificcalulator.Helper.HistoryHelper
import com.hj.scientificcalulator.R
import com.hj.scientificcalulator.databinding.FragmentCalculatorBinding
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.exp

class CalculatorFragment() : Fragment() {
    private lateinit var binding: FragmentCalculatorBinding

    private val calcHelper = CalcHelper()
    private val historyHelper = HistoryHelper()

    var result = MutableLiveData("0")
    var expression = MutableLiveData("")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false)
        binding.lifecycleOwner = this
        binding.view = this

        return binding.root
    }


    private fun showToast(text: String) {
        Toast.makeText(context as MainActivity, text, Toast.LENGTH_SHORT).show()
    }

    private fun getLast(text: String): Char? {
        return if (expression.value!!.isNotEmpty()) {
            expression.value?.get(expression.value!!.lastIndex)
        } else {
            null
        }
    }

    private fun opBtnClicked(op: Char) {
        if (expression.value!!.isNotEmpty()) {
            for (o in calcHelper.operatorList) {
                if (getLast(expression.value!!) == o) {
                    expression.value = expression.value!!.dropLast(1)
                    break
                }
            }
            expression.value = "${expression.value!!}$op"
            result.value = "0"
        } else {
            expression.value = "0$op"
        }
    }

    private fun parenBtnClicked(p: Char) {                       //새로운 함수 추가:
        if (p == '(' && expression.value!![expression.value!!.length - 1] in calcHelper.operatorList) {
            expression.value = "${expression.value!!}${p}"
        } else if (p == ')') {
            if (expression.value!!.count { it == '(' } > expression.value!!.count { it == ')' }) {
                expression.value = "${expression.value!!}${p}"
            } else {
                showToast("Incorrect Input.")
            }
        }
    }


    private fun calc() {
        if (expression.value!!.isNotEmpty() && expression.value!![expression.value!!.length - 1] in calcHelper.operatorList) {
            showToast("Incomplete Input.")
        } else if (expression.value!!.contains("/0")) {
            showToast("Incorrect Input.")
        } else {
            historyHelper.addHistory(expression.value.toString(), result.value.toString())
            calcHelper.calcExpression = expression.value.toString()
            expression.value = "${expression.value!!}= "
            result.value = calcHelper.calculate()
        }
    }


    fun onClick(v: View) {
        when (v) {
            binding.btnHistory -> {
                val transactionManager =
                    (activity as MainActivity).supportFragmentManager.beginTransaction()
                transactionManager.replace(R.id.main, HistoryFragment()).addToBackStack("").commit()
            }

            binding.btnAc -> {
                expression.value = ""
                result.value = "0"
            }

            binding.btnDelete -> {
                if (expression.value!!.isNotEmpty()) {
                    if (result.value!!.isNotEmpty()
                        && result.value!![result.value!!.length - 1]
                        == expression.value!![expression.value!!.length - 1]
                    ) {
                        result.value = result.value?.substring(0, result.value!!.length - 1)
                    } else if (expression.value!![expression.value!!.length - 1] in calcHelper.operatorList) {
                        expression.value = expression.value?.substring(0, expression.value!!.length - 1)
                        calcHelper.getOpIndices(expression.value.toString())
                        result.value = calcHelper.getLastNo(expression.value.toString())
                    }
                }
            }


            binding.btnMinus, binding.btnPlus, binding.btnDivide, binding.btnMultiply -> {
                opBtnClicked((v as MaterialButton).text[0])

            }

            binding.btnPoint -> {
                if (expression.value!!.isNotEmpty()
                    && expression.value!![expression.value!!.length - 1] != '.'
                    && !result.value!!.contains('.')
                ) {
                    expression.value += "."
                    result.value += "."
                } else if (expression.value!!.isEmpty()) {
                    expression.value = "0."
                    result.value += "."
                }
            }

            binding.btnEqual -> {
                calc()

            }

            binding.btnOpenParenthesis -> {
                parenBtnClicked('(')
            }
//
            binding.btnCloseParenthesis -> {
                parenBtnClicked(')')
            }

            else -> {
                if (expression.value!!.isNotEmpty()) {
                    for (op in calcHelper.operatorList) {
                        if (expression.value!![expression.value!!.length - 1] == op) {
                            result.value = "0"
                        }
                    }
                }else if(expression.value!!.contains('=')){
                    expression.value = ""
                    result.value = "0"
                }

                val id = v.resources.getResourceName(v.id)
                expression.value += id[id.lastIndex]

                if (result.value == "0") {
                    result.value = id[id.lastIndex].toString()
                } else {
                    result.value += id[id.lastIndex]
                }
            }
        }

    }
}
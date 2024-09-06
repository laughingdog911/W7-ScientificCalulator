package com.hj.scientificcalulator.Helper

import androidx.lifecycle.MutableLiveData
import net.objecthunter.exp4j.ExpressionBuilder

class CalcHelper {

    val operatorList = listOf('÷', '×', '-', '+')
    var parentheses = ArrayDeque<String>()

    var calcExpression : String = ""


//    fun parenOperation(p: Char) {
//        for (p in parentheses) {
//            if (p == "(" && expression.value!![expression.value!!.length - 1] in operatorList) {
//                parentheses.addFirst("(")
//
//            } else if (p == ")") {
//                if (parentheses.isNotEmpty()) {
//                    parentheses.removeFirstOrNull()
//                }
//            }
//        }
//    }



    fun symbolConverter(expression: String):String {
        expression.replace('×', '*')
        expression.replace('÷', '/')

        return expression

    }


    fun calculate(): String {
        calcExpression = symbolConverter(calcExpression).toString()
        val expressionBuilder = ExpressionBuilder(calcExpression).build()
        val calcResult = expressionBuilder.evaluate().toString()
        return calcResult

    }


}
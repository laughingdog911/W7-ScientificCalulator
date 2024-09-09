package com.hj.scientificcalulator.Helper

import net.objecthunter.exp4j.ExpressionBuilder

class CalcHelper {

    val operatorList = listOf('÷', '×', '-', '+')

    val operatorIndex = mutableListOf<Int>()
    var calcExpression : String = ""

    private fun symbolConverter(expression: String):String {
        expression.replace('×', '*')
        expression.replace('÷', '/')

        return expression

    }

    fun calculate(): String {
        calcExpression = symbolConverter(calcExpression)
        val expressionBuilder = ExpressionBuilder(calcExpression).build()
        val calcResult = expressionBuilder.evaluate().toString()
        return calcResult

    }


}
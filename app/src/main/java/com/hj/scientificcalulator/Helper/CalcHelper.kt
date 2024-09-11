package com.hj.scientificcalulator.Helper

import net.objecthunter.exp4j.ExpressionBuilder


class CalcHelper {

    val operatorList = listOf('÷', '×', '-', '+')


    var calcExpression: String = ""

    private fun symbolConverter(expression: String): String {
        expression.replace('×', '*')
        expression.replace('÷', '/')

        return expression

    }

    fun getOpIndices(expression: String): List<Int> {
        val opIndices = mutableListOf<Int>()
        for (i in expression.indices) {
            if (expression[i] in operatorList) {
                opIndices.add(i)
            }
    }
        return opIndices
    }

    fun getLastNo(expression: String): String {
        val opIndices = getOpIndices(expression)

        return if (opIndices.isEmpty()) {
            expression
        }else{
            val lastOpIndex = opIndices.last()
            val secondLastOpIndex = opIndices[opIndices.lastIndex - 1]
            expression.substring(secondLastOpIndex, lastOpIndex)

        }
    }

    fun calculate(): String {
        calcExpression = symbolConverter(calcExpression)
        val expressionBuilder = ExpressionBuilder(calcExpression).build()
        val calcResult = expressionBuilder.evaluate().toString()
        return calcResult

    }


}
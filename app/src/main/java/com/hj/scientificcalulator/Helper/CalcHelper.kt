package com.hj.scientificcalulator.Helper

class CalcHelper {

    val operatorList = listOf('÷', '×', '-', '+')
    var parentheses = ArrayDeque<String>()

    var calcexpression = //여기에 계산 식을 담아서 evaluate() 함수에 넘겨주기?


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

    fun symbolConverter(op: Char) {
        when (op) {
            '×' -> {
                '*'
            }
            '÷' -> {
                '/'
            }
            else -> {
                op
            }
        }
    }
}
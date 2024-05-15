package com.ndc.core.utils

fun main() {
    val mockVm = MockVm<String>()
    mockVm.onAction("1")
    val navigate = {

    }
    mockVm.updateUi {

    }
}

abstract class MockBaseVm<Action>() {
    abstract fun onAction(action: Action)
}

class MockVm<Action>() : MockBaseVm<Action>() {
    override fun onAction(action: Action) {
        when (action) {
            "1" -> println("1")
            "2" -> println("2")
        }
    }

    fun updateUi(update: () -> Unit){
        update()
    }

}



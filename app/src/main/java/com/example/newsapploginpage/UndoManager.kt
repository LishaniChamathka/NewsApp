package com.example.newsapploginpage

class UndoManager {
    private val states = mutableListOf<String>()
    private var currentIndex = -1
    private val maxStates = 100

    fun addState(state: String) {
        // Remove any redo states
        while (states.size > currentIndex + 1) {
            states.removeAt(states.size - 1)
        }

        // Add new state
        states.add(state)
        currentIndex++

        // Remove oldest states if exceeding maxStates
        if (states.size > maxStates) {
            states.removeAt(0)
            currentIndex--
        }
    }

    fun undo(): String? {
        if (currentIndex > 0) {
            currentIndex--
            return states[currentIndex]
        }
        return null
    }

    fun redo(): String? {
        if (currentIndex < states.size - 1) {
            currentIndex++
            return states[currentIndex]
        }
        return null
    }
}
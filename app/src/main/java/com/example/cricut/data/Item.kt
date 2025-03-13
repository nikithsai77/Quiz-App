package com.example.cricut.data

data class Item(val title: String, val question: String, val minimum : Int = 1, var userAnswer: MutableList<String> = mutableListOf<String>(), var userTheoryAnswer: String = "", val options : ArrayList<String>? = null, var isError : Boolean = false)
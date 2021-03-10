package com.abhishek101.core

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}

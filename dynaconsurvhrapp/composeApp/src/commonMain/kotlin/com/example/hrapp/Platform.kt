package com.example.hrapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
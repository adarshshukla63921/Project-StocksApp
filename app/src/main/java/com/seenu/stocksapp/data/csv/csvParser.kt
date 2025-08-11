package com.seenu.stocksapp.data.csv

import java.io.InputStream

interface csvParser<T>{
    suspend fun parse(stream : InputStream) : List<T>
}
package com.seenu.stocksapp.data.mappers


import com.seenu.stocksapp.data.remote.dto.IntradayInfoDto
import com.seenu.stocksapp.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun IntradayInfoDto.toIntradayInfo() : IntradayInfo{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timeStamp,formatter)

    return IntradayInfo(
        data = localDateTime,
        close = close
    )
}
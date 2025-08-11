package com.seenu.stocksapp.data.csv

import com.opencsv.CSVReader
import com.seenu.stocksapp.data.mappers.toIntradayInfo
import com.seenu.stocksapp.data.remote.dto.IntradayInfoDto
import com.seenu.stocksapp.domain.model.CompanyListing
import com.seenu.stocksapp.domain.model.IntradayInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor() : csvParser<IntradayInfo> {
    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull {
                        line->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    val dto = IntradayInfoDto(timestamp,close.toDouble())
                    dto.toIntradayInfo()
                }.filter {
                   it.data.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth
                }
                .sortedBy {
                    it.data.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }
}
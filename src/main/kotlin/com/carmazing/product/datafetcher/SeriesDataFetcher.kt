package com.carmazing.product.datafetcher


import com.carmazing.product.codegen.types.SeriesInput
import com.carmazing.product.datasource.entity.Series
import com.carmazing.product.service.query.SeriesQueryService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery

@DgsComponent
class SeriesDataFetcher(private val seriesQueryService: SeriesQueryService) {

    @DgsQuery
    fun series(seriesInput: SeriesInput?): List<Series> {
        return seriesQueryService.filterSeries(seriesInput)
    }

}
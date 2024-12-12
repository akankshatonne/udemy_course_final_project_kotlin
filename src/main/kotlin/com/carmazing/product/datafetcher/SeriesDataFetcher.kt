package com.carmazing.product.datafetcher

import com.carmazing.product.codegen.types.SeriesInput
import com.carmazing.product.datasource.entity.Series
import com.carmazing.product.service.query.SeriesQueryService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import graphql.relay.Connection
import graphql.relay.SimpleListConnection
import graphql.schema.DataFetchingEnvironment

@DgsComponent
class SeriesDataFetcher(private val seriesQueryService: SeriesQueryService) {

    @DgsQuery
    fun series(seriesInput: SeriesInput?): List<Series> {
        return seriesQueryService.filterSeries(seriesInput)
    }

    @DgsQuery
    fun seriesPagination(
        seriesInput: SeriesInput?,
        dfe: DataFetchingEnvironment,
        first: Int?,
        last: Int?,
        after: String?,
        before: String? ): Connection<Series> {

        val result =  seriesQueryService.filterSeries(seriesInput)
        return SimpleListConnection(result).get(dfe)
    }

}
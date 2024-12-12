package com.carmazing.product.datafetcher


import com.carmazing.product.codegen.types.ManufacturerInput
import com.carmazing.product.datasource.entity.Manufacturers
import com.carmazing.product.service.query.ManufacturerQueryService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import graphql.relay.Connection
import graphql.relay.SimpleListConnection
import graphql.schema.DataFetchingEnvironment

@DgsComponent
class ManufacturerDataFetcher(
    private val manufacturerQueryService: ManufacturerQueryService,
) {

    @DgsQuery
    fun manufacturers(manufacturerInput: ManufacturerInput?): List<Manufacturers> {
        return manufacturerQueryService.filterManufacturer(manufacturerInput)
    }

    @DgsQuery
    fun manufacturersPagination(
        manufacturerInput: ManufacturerInput?,
        dfe: DataFetchingEnvironment,
        first: Int?,
        last: Int?,
        after: String?,
        before: String? ): Connection<Manufacturers> {

        val result =  manufacturerQueryService.filterManufacturer(manufacturerInput)
        return SimpleListConnection(result).get(dfe)
    }

}
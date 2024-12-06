package com.carmazing.product.datafetcher

import com.carmazing.product.codegen.types.Manufacturer
import com.carmazing.product.codegen.types.ManufacturerInput
import com.carmazing.product.datasource.entity.Manufacturers
import com.carmazing.product.service.query.ManufacturerQueryService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery

@DgsComponent
class ManufacturerDataFetcher(
    private val manufacturerQueryService: ManufacturerQueryService,
) {

    @DgsQuery
    fun manufacturers(manufacturerInput: ManufacturerInput?): List<Manufacturers> {
        return manufacturerQueryService.filterManufacturer(manufacturerInput)
    }

}
package com.carmazing.product.datafetcher

import com.carmazing.product.codegen.types.ModelInput
import com.carmazing.product.codegen.types.NumericComparisonInput
import com.carmazing.product.datasource.entity.Models
import com.carmazing.product.service.query.ModelQueryService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery

@DgsComponent
class ModelDataFetcher(private val modelQueryService: ModelQueryService) {

    @DgsQuery
    fun models(modelInput: ModelInput?, priceInput: NumericComparisonInput?): List<Models>{
        return modelQueryService.filterModels(modelInput, priceInput)
    }
}
package com.carmazing.product.datafetcher

import com.carmazing.product.codegen.types.ModelInput
import com.carmazing.product.codegen.types.ModelPagination
import com.carmazing.product.codegen.types.NumericComparisonInput
import com.carmazing.product.datasource.entity.Models
import com.carmazing.product.service.query.ModelQueryService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import graphql.relay.SimpleListConnection
import graphql.schema.DataFetchingEnvironment

@DgsComponent
class ModelDataFetcher(private val modelQueryService: ModelQueryService) {

    @DgsQuery
    fun models(modelInput: ModelInput?, priceInput: NumericComparisonInput?): List<Models>{
        return modelQueryService.filterModels(modelInput, priceInput)
    }

    @DgsQuery
    fun modelsPagination(
        modelInput: ModelInput?,
        priceInput: NumericComparisonInput?,
        dfe: DataFetchingEnvironment,
        first: Int?,
        last: Int?,
        after: String?,
        before: String?,
        page: Int,
        size: Int): ModelPagination {

        val filteredModelsPage =  modelQueryService.filterModels(modelInput, priceInput, page, size)
        val modelConnection = SimpleListConnection(filteredModelsPage.content).get(dfe)
        val result = ModelPagination(modelConnection,
                                    filteredModelsPage.number,
                                    filteredModelsPage.size,
                                    filteredModelsPage.totalPages,
                                    filteredModelsPage.totalElements )

        return result
    }


}
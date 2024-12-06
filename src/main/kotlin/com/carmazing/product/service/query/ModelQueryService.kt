package com.carmazing.product.service.query

import com.carmazing.product.codegen.types.ModelInput
import com.carmazing.product.codegen.types.NumericComparisonInput
import com.carmazing.product.datasource.entity.Models
import com.carmazing.product.datasource.repository.ModelRepository
import com.carmazing.product.specification.ManufacturerSpecification
import com.carmazing.product.specification.ModelSpecification
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ModelQueryService(private val modelRepository: ModelRepository) {

    fun filterModels(modelInput: ModelInput?, numericComparisonInput: NumericComparisonInput?): List<Models> {
        modelInput?.let {
            val spec = ModelSpecification.buildSpec(
                modelInput.name,
                modelInput.series?.manufacturer?.name,
                modelInput.series?.name,
                modelInput.exteriorColors,
                modelInput.transmission,
                modelInput.isAvailable,
                numericComparisonInput
            )
            val sorts = ManufacturerSpecification.sortOrderWith(modelInput.sorts)
            return modelRepository.findAll(spec, Sort.by(sorts))
        }
        return modelRepository.findAll()
    }



}
package com.carmazing.product.service.query

import com.carmazing.product.codegen.types.ManufacturerInput
import com.carmazing.product.datasource.entity.Manufacturers
import com.carmazing.product.datasource.repository.ManufacturerRepository
import com.carmazing.product.specification.ManufacturerSpecification
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class ManufacturerQueryService (private val manufacturerRepository: ManufacturerRepository)
{
    fun filterManufacturer(manufacturerInput: ManufacturerInput?): List<Manufacturers> {
        manufacturerInput?.let {
            val spec = ManufacturerSpecification.buildSpec(
                manufacturerInput.name,
                manufacturerInput.originCountry
            )
            val sorts = ManufacturerSpecification.sortOrderWith(manufacturerInput.sorts)
            return manufacturerRepository.findAll(spec, Sort.by(sorts))

        }
        return manufacturerRepository.findAll()
    }
}
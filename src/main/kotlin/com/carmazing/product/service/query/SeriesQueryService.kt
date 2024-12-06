package com.carmazing.product.service.query

import com.carmazing.product.codegen.types.SeriesInput
import com.carmazing.product.datasource.entity.Series
import com.carmazing.product.datasource.repository.SeriesRepository
import com.carmazing.product.specification.ManufacturerSpecification
import com.carmazing.product.specification.SeriesSpecification
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class SeriesQueryService(private val seriesRepository: SeriesRepository) {

    // Need to see which of the two is more efficient, two joins or filter in code?

//    fun filterSeries(seriesInput: SeriesInput): List<Series> {
//        val spec = SeriesSpecification.buildSpec(seriesInput.name)
//        val result = seriesRepository.findAll(spec)
//            .filter { it.manufacturers.name == seriesInput.manufacturer?.name &&
//                    it.manufacturers.originCountry == seriesInput.manufacturer.originCountry }
//        return result
//
//
//    }

    // course way
    fun filterSeries(seriesInput: SeriesInput?): List<Series> {
        seriesInput?.let {
            val spec = SeriesSpecification.buildSpec(
                seriesInput.name,
                seriesInput.manufacturer?.name,
                seriesInput.manufacturer?.originCountry
            )
            val sorts = ManufacturerSpecification.sortOrderWith(seriesInput.sorts)
            return seriesRepository.findAll(spec, Sort.by(sorts))
        }
        return seriesRepository.findAll()
    }

}
package com.carmazing.product.specification

import com.carmazing.product.codegen.types.NumericComparison
import com.carmazing.product.codegen.types.NumericComparisonInput
import com.carmazing.product.codegen.types.Transmission
import com.carmazing.product.datasource.entity.Manufacturers
import com.carmazing.product.datasource.entity.Models
import com.carmazing.product.datasource.entity.Series
import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object ModelSpecification: BaseSpecification() {

    const val FIELD_NAME = "name"
    const val FIELD_ON_THE_ROAD_PRICE = "onTheRoadPrice"
    const val FIELD_EXTERIOR_COLOR = "exteriorColor"
    const val FIELD_TRANSMISSION = "transmission"
    const val FIELD_AVAILABILITY = "isAvailable"

    const val FIELD_SERIES = "series"

    fun manufacturerNameIs(manufacturerName: String?) : Specification<Models>? {
        return manufacturerName?.let {
            Specification<Models> { root, _, criteriaBuilder ->
                val join: Join<Models,Manufacturers> = root.join<Models,Series>(FIELD_SERIES).join(SeriesSpecification.FIELD_MANUFACTURER)
                criteriaBuilder.like(criteriaBuilder.lower(join.get(FIELD_NAME)), contains(it.lowercase()) )
            }
        }
    }

    fun seriesNameIs(seriesName: String?) : Specification<Models>? {
        return seriesName?.let {
            Specification<Models> { root, _, criteriaBuilder ->
                val join: Join<Models, Series> = root.join("series")
                criteriaBuilder.like(criteriaBuilder.lower(join.get(FIELD_SERIES)), contains(it.lowercase()) )
            }
        }
    }

    fun exteriorColorsLikeIgnoreCase(exteriorColors: List<String>?): Specification<Models>? {
        return exteriorColors?.let {

            val predicateList = mutableListOf<Predicate>()

            Specification<Models> { root, _, criteriaBuilder ->

                exteriorColors.forEach{
                    predicateList.add(
                        criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(FIELD_EXTERIOR_COLOR)),
                        contains(it.lowercase()) )
                    )
                }

                criteriaBuilder.or(*predicateList.toTypedArray())

            }

        }
    }

    fun transmissionIs(transmission: Transmission?) : Specification<Models>? {
        return transmission?.let {
            Specification<Models> { root, _, criteriaBuilder ->
                criteriaBuilder.equal(root.get<String>(FIELD_TRANSMISSION), it.toString() )
            }
        }
    }

    fun avaliabilityIs(avaliability: Boolean?) : Specification<Models>? {
        return avaliability?.let {
            Specification<Models> { root, _, criteriaBuilder ->
                criteriaBuilder.equal(root.get<Boolean>(FIELD_AVAILABILITY), it )
            }
        }
    }

    fun roadPriceLessThan(price: Int?) : Specification<Models>? {
        return price?.let {
            Specification<Models> { root, _, criteriaBuilder ->
                criteriaBuilder.lessThan(root.get(FIELD_ON_THE_ROAD_PRICE), it )
            }
        }
    }

    fun roadPriceGreaterThanOrEqualTo(price: Int?) : Specification<Models>? {
        return price?.let {
            Specification<Models> { root, _, criteriaBuilder ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(FIELD_ON_THE_ROAD_PRICE), it )
            }
        }
    }

    fun roadPriceBetweenThan(price1: Int, price2: Int ) : Specification<Models> {
        return Specification<Models> { root, _, criteriaBuilder ->
                criteriaBuilder.between(root.get(FIELD_ON_THE_ROAD_PRICE), price1, price2)
            }
    }

    fun modelNameIs(modelName: String?) : Specification<Models>? {
        return modelName?.let {
            Specification<Models> { root, _, criteriaBuilder ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(FIELD_NAME)), contains(it.lowercase()) )
            }
        }
    }

    fun getPriceComparisonOperator(numericComparisonInput: NumericComparisonInput?) : Specification<Models>? {
        return numericComparisonInput?.let {
            val operator = numericComparisonInput.operator
            val price  = numericComparisonInput.value
            val highValue = numericComparisonInput.highValue
            when (operator){
                NumericComparison.LESS_THAN_EQUALS -> roadPriceLessThan(price)
                NumericComparison.GREATER_THAN_EQUALS -> roadPriceGreaterThanOrEqualTo(price)
                NumericComparison.BETWEEN_INCLUSIVE -> roadPriceBetweenThan(price,highValue ?: price)
            }

        }
    }

    fun buildSpec(modelName: String?, manufacturerName: String?, seriesName: String?, exteriorColors: List<String>?, transmission: Transmission?, availability: Boolean?, numericComparisonInput: NumericComparisonInput?) : Specification<Models>? {
        return Specification.where(manufacturerNameIs(manufacturerName))
            ?.and(modelNameIs(modelName))
            ?.and(seriesNameIs(seriesName))
            ?.and(exteriorColorsLikeIgnoreCase(exteriorColors))
            ?.and(transmissionIs(transmission))
            ?.and(getPriceComparisonOperator(numericComparisonInput))
            ?.and(avaliabilityIs(availability)) ?: Specification{ _, _, criteriaBuilder -> criteriaBuilder.conjunction()}
    }

}
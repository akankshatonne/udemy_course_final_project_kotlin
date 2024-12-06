package com.carmazing.product.specification

import com.carmazing.product.datasource.entity.Manufacturers
import com.carmazing.product.datasource.entity.Series
import jakarta.persistence.criteria.Join
import org.springframework.data.jpa.domain.Specification

object SeriesSpecification: BaseSpecification() {

    const val FIELD_NAME = "name"
    const val FIELD_MANUFACTURER = "manufacturers"

    fun nameIs(name: String?): Specification<Series>? {
        return name?.let {
            Specification<Series>{root,_,criteriaBuilder ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(FIELD_NAME)), contains(it.lowercase())) } // "contains" is a fun in the base abstract class, uses lower case and contains method
        }
    }

    fun manufacturerNameIs(manufacturerName: String?): Specification<Series>? {
        return manufacturerName?.let {
            Specification<Series>{root,_,criteriaBuilder ->
              val join: Join<Series, Manufacturers> = root.join(FIELD_MANUFACTURER)
                criteriaBuilder.like(criteriaBuilder.lower(join.get(ManufacturerSpecification.FIELD_NAME)), contains(it.lowercase()))
            }
        }
    }

    fun manufacturerOriginCountryIs(manufacturerOriginCountry: String?): Specification<Series>? {
        return manufacturerOriginCountry?.let {
            Specification<Series>{root,_,criteriaBuilder ->
                val join: Join<Series, Manufacturers> = root.join(FIELD_MANUFACTURER)
                criteriaBuilder.like(criteriaBuilder.lower(join.get(ManufacturerSpecification.FIELD_ORIGIN_COUNTRY)), contains(it.lowercase()))
            }
        }
    }

    fun buildSpec(seriesName: String?,manufacturerName: String?, manufacturerOriginCountry: String?): Specification<Series> {
        return Specification.where(nameIs(seriesName))
            ?.and(manufacturerNameIs(manufacturerName))
            ?.and(manufacturerOriginCountryIs(manufacturerOriginCountry))
            ?: Specification{_,_,criteriaBuilder-> criteriaBuilder.conjunction()}
    }

    fun buildSpec(seriesName: String?): Specification<Series> {
        return Specification.where(nameIs(seriesName))
    }

}
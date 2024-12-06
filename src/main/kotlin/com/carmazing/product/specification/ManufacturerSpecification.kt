package com.carmazing.product.specification

import com.carmazing.product.datasource.entity.Manufacturers
import org.springframework.data.jpa.domain.Specification

object ManufacturerSpecification: BaseSpecification(){

    const val FIELD_NAME = "name"
    const val FIELD_ORIGIN_COUNTRY = "originCountry"

    fun nameIs(name: String?): Specification<Manufacturers>? {
        return name?.let {
            Specification<Manufacturers> { root, _, criteriaBuilder ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(FIELD_NAME)), contains(it.lowercase()) )
            }
        }
    }

    fun countryOriginIs(countryOrigin: String?): Specification<Manufacturers>? {
        return countryOrigin?.let {
            Specification<Manufacturers>{ root,_,criteriaBuilder ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(FIELD_ORIGIN_COUNTRY)), contains(it.lowercase()) )

            }
        }
    }



    fun buildSpec(name: String?,countryOrigin: String?): Specification<Manufacturers> {
        return Specification.where(nameIs(name))
            ?.and(countryOriginIs(countryOrigin))
            ?: Specification {
                _, _, criteriaBuilder -> criteriaBuilder.conjunction()
            }
    }




}
package com.carmazing.product.specification

import com.carmazing.product.codegen.types.SortDirection
import com.carmazing.product.codegen.types.SortInput
import org.springframework.data.domain.Sort
import java.text.MessageFormat

abstract class BaseSpecification {


        fun contains(keyword: String?): String {
            return MessageFormat.format("%$keyword%")
        }

        fun sortOrderWith(listSortOrder: List<SortInput>?): List<Sort.Order> {
            val listSortOrderField = mutableListOf<Sort.Order>()
            listSortOrder?.forEach {
                if (it.direction == SortDirection.ASCENDING) listSortOrderField.add(Sort.Order.asc(it.field))
                else listSortOrderField.add(Sort.Order.desc(it.field))

            }
            return listSortOrderField
    }
}
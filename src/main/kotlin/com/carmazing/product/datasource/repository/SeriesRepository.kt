package com.carmazing.product.datasource.repository

import com.carmazing.product.datasource.entity.Series
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface SeriesRepository: JpaRepository<Series, UUID>, JpaSpecificationExecutor<Series>
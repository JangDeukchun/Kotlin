package io.medium.poc.api.controller.ksd.request.query

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min

@Schema(description = "예탁원 공모리스트 조회 조건 요청 객체")
data class KsdSearchConditionQuery(
    @Schema(description = "조회조건", example = "")
    val searchCondition: String = "",

    @Schema(description = "가져올 검색 결과 수, 기본 값은 10", example = "10")
    @field:Min(1, message = "가져올 검색 결과 수는 0보다 커야 합니다.")
    val recordsCount: Long = 10,

    @Schema(description = "다음 페이지 호출시 필요한 bookmark 정보, 없으면 비운다.")
    val bookmark: String? = null,
)
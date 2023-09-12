package io.medium.poc.api.controller.bank.request.query

import io.medium.poc.common.code.BalanceType
import io.medium.poc.common.constraint.EnumCheck
import io.medium.poc.domain.bank.model.entity.BankCustMgmtInstCashTokenStateMultiKeys
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Schema(description = "은행 고객관리기관 캐시토큰 상태 조회 요청 객체")
data class BankCustMgmtInstCashTokenStateQuery(
    @Schema(description = "고객관리기관번호")
    @field:NotNull(message = "고객관리기관번호 정보는 필수입니다.")
    @field:NotBlank(message = "고객관리기관번호 정보는 필수입니다.")
    val custMgmtInstNo: String,

    @Schema(description = "캐시토큰 ID")
    @field:NotNull(message = "캐시토큰 ID 정보는 필수입니다.")
    @field:NotBlank(message = "캐시토큰 ID 정보는 필수입니다.")
    val cashId: String,

    @Schema(description = "잔고유형 코드")
    @field:EnumCheck(enumClazz = BalanceType::class, message = "BalanceType 필드는 01, 02, 03, 04, 05 만 가능합니다.")
    val balanceType: String,
) {
    fun compositeId(): BankCustMgmtInstCashTokenStateMultiKeys {
        return BankCustMgmtInstCashTokenStateMultiKeys(
            custMgmtInstNo = custMgmtInstNo,
            cashId = cashId,
            balanceType = BalanceType.fromCode(balanceType),
        )
    }
}

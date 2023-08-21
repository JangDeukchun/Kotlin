package io.dustin.apps.board.api.qna

import io.dustin.apps.board.api.qna.request.command.QuestionCommand
import io.dustin.apps.board.api.qna.request.command.QuestionDeleteCommand
import io.dustin.apps.board.api.usecase.qna.question.DeleteQuestionUseCase
import io.dustin.apps.board.api.usecase.qna.question.ModifyQuestionUseCase
import io.dustin.apps.board.api.usecase.qna.question.ReadQuestionUseCase
import io.dustin.apps.board.api.usecase.qna.question.WriteQuestionUseCase
import io.dustin.apps.board.domain.qna.question.model.dto.QuestionDetailDto
import io.dustin.apps.board.domain.qna.question.model.dto.QuestionDto
import io.dustin.apps.common.code.CommonMessage
import io.dustin.apps.common.model.QueryPage
import io.dustin.apps.common.model.ResponseWithScroll
import io.dustin.apps.common.model.response.CommonResponse
import io.dustin.apps.common.model.response.ResultResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/question")
@Tag(name = "1:1문의 작성 API", description = "1:1문의 작성 API 를 제공한다.")
class QuestionController (

    private val readQuestionUseCase: ReadQuestionUseCase,
    private val writeQuestionUseCase: WriteQuestionUseCase,
    private val modifyQuestionUseCase: ModifyQuestionUseCase,
    private val deleteQuestionUseCase: DeleteQuestionUseCase,

    ) {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    @Operation(
        summary = "1:1 문의의 질문을 리스트를 불러온다",
        description = "1:1 문의의 질문을 리스트를 불러온다"
    )
    fun allQuestions(queryPage: QueryPage): ResponseWithScroll<*> {
        return readQuestionUseCase.execute(queryPage)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{questionId}")
    @Operation(
        summary = "1:1 문의의 질문 상세보기",
        description = "1:1 문의의 질문 상세보기"
    )
    fun questionDetail(@PathVariable("questionId") questionId: Long): QuestionDetailDto {
        return readQuestionUseCase.questionDetail(questionId)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/create")
    @Operation(
        summary = "1:1 문의의 질문을 작성한다",
        description = "1:1 문의의 질문을 작성한다"
    )
    fun createQuestion(@RequestBody @Valid command: QuestionCommand): ResultResponse<QuestionDto> {
        return writeQuestionUseCase.execute(command.userId, command.subject, command.content)
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{questionId}")
    @Operation(
        summary = "작성된 질문을 수정한다",
        description = "작성된 질문을 수정한다"
    )
    fun modifyQuestion(
        @PathVariable("questionId") questionId: Long,
        @RequestBody @Valid command: QuestionCommand
    ): ResultResponse<QuestionDto> {
        return modifyQuestionUseCase.execute(
            command.userId,
            questionId,
            command.subject,
            command.content
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{questionId}")
    @Operation(
        summary = "작성된 질문을 삭제한다",
        description = "작성된 질문을 삭제한다"
    )
    fun deleteQuestion(
        @PathVariable("questionId") questionId: Long,
        @RequestBody command: QuestionDeleteCommand
    ): CommonResponse {
        deleteQuestionUseCase.execute(command.userId, questionId)
        return CommonResponse(
            code = HttpStatus.OK.value(),
            message = CommonMessage.SUCCESS.code,
            timestamp = LocalDateTime.now()
        )
    }
}

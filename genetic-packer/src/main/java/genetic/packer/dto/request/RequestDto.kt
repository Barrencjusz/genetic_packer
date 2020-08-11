package genetic.packer.dto.request

import genetic.packer.dto.request.validation.Even
import javax.validation.constraints.Min

data class RequestDto<T>(
    val numberOfGenerations: Int,
    @field:Even
    @field:Min(2)
    val generationSize: Int,
    val numberOfTopIndividuals: Int, //fixme it's statistics property, move it to new node 'statistics' ? or make it parameter
    val numberOfEliteIndividuals: Int,
    val embryo: T
)
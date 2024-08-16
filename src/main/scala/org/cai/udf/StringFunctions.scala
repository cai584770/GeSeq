package org.cai.udf

import org.neo4j.annotations.Description
import org.neo4j.procedure.{Name, UserFunction}

/**
 * @author cai584770
 * @date 2024/8/16 21:33
 * @Version
 */
class StringFunctions {

  @UserFunction
  @Description("Process custom data type")
  def reverseString(@Name("input") input: String): String = {
    input.reverse
  }

  @UserFunction
  @Description("Process custom data type")
  def complementString(@Name("input") input: String): String = {
    complementString(input)
  }

  @UserFunction
  @Description("Process custom data type")
  def reverseComplementString(@Name("input") input: String): String = {
    complementString(input).reverse
  }

}

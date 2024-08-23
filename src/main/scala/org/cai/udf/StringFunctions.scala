package org.cai.udf

import org.neo4j.annotations.Description
import org.neo4j.procedure.{Name, UserFunction}

/**
 * @author cai584770
 * @date 2024/8/16 21:33
 * @Version
 */
class StringFunctions {

  @UserFunction(name = "stringReverse")
  @Description("reverse gene sequence string")
  def reverseString(@Name("input") input: String): String = {
    input.reverse
  }

  @UserFunction(name = "stringComplement")
  @Description("complement gene sequence string")
  def complementString(@Name("input") input: String): String = {
    complementString(input)
  }

  @UserFunction(name = "stringrev_com")
  @Description("reverse and complement gene sequence string")
  def reverseComplementString(@Name("input") input: String): String = {
    complementString(input).reverse
  }

}

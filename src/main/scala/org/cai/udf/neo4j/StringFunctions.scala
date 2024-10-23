package org.cai.udf.neo4j

import org.neo4j.annotations.Description
import org.neo4j.procedure.{Name, UserFunction}

/**
 * @author cai584770
 * @date 2024/8/16 21:33
 * @Version
 */
class StringFunctions {

  @UserFunction("str.rev")
  @Description("reverse gene sequence string")
  def reverseString(@Name("input") input: String): String = {
    input.reverse
  }

  @UserFunction("str.com")
  @Description("complement gene sequence string")
  def complementString(@Name("input") input: String): String = {
    complementString(input)
  }

  @UserFunction("str.rev_com")
  @Description("reverse and complement gene sequence string")
  def reverseComplementString(@Name("input") input: String): String = {
    complementString(input).reverse
  }

}

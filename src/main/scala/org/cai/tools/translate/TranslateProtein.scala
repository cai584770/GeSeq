package org.cai.tools.translate

import scala.collection.mutable

/**
 * @author cai584770
 * @date 2024/10/24 11:22
 * @Version
 */
object TranslateProtein {

  def translateString(sequence: String): mutable.StringBuilder = {
    var cur = 0
    val protein: mutable.StringBuilder = new mutable.StringBuilder()

    while (cur + 2 < sequence.length) {
      val codon = sequence.substring(cur, cur + 3)
      cur += 3

      val aminoAcid = EncodingProtein.encodingProtein(codon(0),codon(1),codon(2))

      protein.append(aminoAcid)
    }

    if (sequence.length % 3 != 0) {
      protein.setLength(protein.length - 1)
    }

    protein
  }

}

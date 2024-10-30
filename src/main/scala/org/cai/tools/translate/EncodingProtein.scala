package org.cai.tools.translate


/**
 * @author cai584770
 * @date 2024/10/24 11:20
 * @Version
 */
object EncodingProtein {
  val t = 'T'
  val a = 'A'
  val c = 'C'
  val g = 'G'
  val r002 = Seq('T', 'u', 'C', 'y')
  val r021 = Seq('A', 'G', 'C')
  val r003 = Seq('C', 'T')
  val r004 = Seq('T', 'C', 'u', 'y')
  val r005 = Seq('A', 'C', 'G', 'T', 'u', 'r', 'y', 's', 'w', 'k', 'm', 'b', 'd', 'h', 'v', 'n')
  val r006 = Seq('A', 'T', 'C', 'u', 'w', 'm', 'h', 'y')
  val r1 = Seq('C', 'A', 'm')
  val r11 = Seq('T', 'u')
  val r207 = Seq('C', 'T', 'u', 'y')

  val codonTable: Map[(Char, Char, Char), String] = Map(
    // UUU -> F, UUC -> F
    (t, t, t) -> "F", // UUU
    (t, t, c) -> "F", // UUC
    // UUA -> L, UUG -> L
    (t, t, a) -> "L", // UUA
    (t, t, g) -> "L", // UUG
    // CUU -> L, CUC -> L, CUA -> L, CUG -> L
    (c, t, t) -> "L", // CUU
    (c, t, c) -> "L", // CUC
    (c, t, a) -> "L", // CUA
    (c, t, g) -> "L", // CUG
    // AUU -> I, AUC -> I, AUA -> I
    (a, t, t) -> "I", // AUU
    (a, t, c) -> "I", // AUC
    (a, t, a) -> "I", // AUA
    // AUG -> M
    (a, t, g) -> "M", // AUG
    // GUU -> V, GUC -> V, GUA -> V, GUG -> V
    (g, t, t) -> "V", // GUU
    (g, t, c) -> "V", // GUC
    (g, t, a) -> "V", // GUA
    (g, t, g) -> "V", // GUG
    // UCU -> S, UCC -> S, UCA -> S, UCG -> S
    (t, c, t) -> "S", // UCU
    (t, c, c) -> "S", // UCC
    (t, c, a) -> "S", // UCA
    (t, c, g) -> "S", // UCG
    // CCU -> P, CCC -> P, CCA -> P, CCG -> P
    (c, c, t) -> "P", // CCU
    (c, c, c) -> "P", // CCC
    (c, c, a) -> "P", // CCA
    (c, c, g) -> "P", // CCG
    // ACU -> T, ACC -> T, ACA -> T, ACG -> T
    (a, c, t) -> "T", // ACU
    (a, c, c) -> "T", // ACC
    (a, c, a) -> "T", // ACA
    (a, c, g) -> "T", // ACG
    // GCU -> A, GCC -> A, GCA -> A, GCG -> A
    (g, c, t) -> "A", // GCU
    (g, c, c) -> "A", // GCC
    (g, c, a) -> "A", // GCA
    (g, c, g) -> "A", // GCG
    // UAU -> Y, UAC -> Y
    (t, a, t) -> "Y", // UAU
    (t, a, c) -> "Y", // UAC
    // UAA -> Stop, UAG -> Stop
    (t, a, a) -> "*", // UAA
    (t, a, g) -> "*", // UAG
    // CAU -> H, CAC -> H
    (c, a, t) -> "H", // CAU
    (c, a, c) -> "H", // CAC
    // CAA -> Q, CAG -> Q
    (c, a, a) -> "Q", // CAA
    (c, a, g) -> "Q", // CAG
    // AAU -> N, AAC -> N
    (a, a, t) -> "N", // AAU
    (a, a, c) -> "N", // AAC
    // AAA -> K, AAG -> K
    (a, a, a) -> "K", // AAA
    (a, a, g) -> "K", // AAG
    // GAU -> D, GAC -> D
    (g, a, t) -> "D", // GAU
    (g, a, c) -> "D", // GAC
    // GAA -> E, GAG -> E
    (g, a, a) -> "E", // GAA
    (g, a, g) -> "E", // GAG
    // UGU -> C, UGC -> C
    (t, g, t) -> "C", // UGU
    (t, g, c) -> "C", // UGC
    // UGA -> Stop, UGG -> W
    (t, g, a) -> "*", // UGA
    (t, g, g) -> "W", // UGG
    // CGU -> R, CGC -> R, CGA -> R, CGG -> R
    (c, g, t) -> "R", // CGU
    (c, g, c) -> "R", // CGC
    (c, g, a) -> "R", // CGA
    (c, g, g) -> "R", // CGG
    // AGU -> S, AGC -> S
    (a, g, t) -> "S", // AGU
    (a, g, c) -> "S", // AGC
    // AGA -> R, AGG -> R
    (a, g, a) -> "R", // AGA
    (a, g, g) -> "R", // AGG
    // GGU -> G, GGC -> G, GGA -> G, GGG -> G
    (g, g, t) -> "G", // GGU
    (g, g, c) -> "G", // GGC
    (g, g, a) -> "G", // GGA
    (g, g, g) -> "G" // GGG
  )

  def encodingProtein(d1: Char, d2: Char, d3: Char): String = {
    codonTable.getOrElse((d1, d2, d3), "")
  }

}

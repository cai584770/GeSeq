# GeSeq

GeSeq is an integrated graph database gene sequence data type that handles both storage and management. In GeSeq, gene sequence data is compressed and stored using **BBM (Bit-Byte Mapping)**, which modifies the way gene sequence data is processed based on the storage scheme. GeSeq also provides **UDFs (User-Defined Functions)** for use in Neo4j, enabling users to work with gene sequence data within the graph database.

**USE**

Neo4j 4.4.11 Community Edition

**BBM**

BBM is contained within the `org.cai.bbm` package and primarily handles the mapping of gene sequence strings to byte data. The mapping rules are as follows: `"A" -> 00, "T" -> 11, "C" -> 01, "G" -> 10`.

**file**

The `org.cai.file` package contains parsing functionality for imported files, with the main purpose of handling all files imported into the graph database and setting rules for exporting.

**geseq**

The `org.cai.file` package includes the definition of GeSeq and common usage methods.

```
scala复制代码case class GeSeq(
                  sequence: Array[Byte],
                  lowercase: List[(Int, Int)],
                  nBase: List[(Int, Int)],
                  otherBASE: List[(Int, String)],
                  sequenceLength: Long,
                  nucleotidesLength: Long
                )
```

Here, `sequence` contains the gene sequence data processed by BBM. `lowercase` contains information about case conversion points, where each tuple `(int1, int2)` represents the starting point and length of case conversion relative to the sequence. `nBase` indicates the positions and lengths of the "N" characters in the sequence, with each tuple `(int1, int2)` representing the relative starting position and length. `otherBase` contains information about other degenerate bases, with each tuple `(Int, String)` representing the relative position and a string of consecutive degenerate base sub-sequences. `sequenceLength` represents the original sequence length, and `nucleotidesLength` represents the length of the nucleotide bases.

**tools**

The `tools` package includes methods for processing byte data, currently focusing on operations such as `convert`, `complement`, and `translate`.

**udf**

The `udf` package contains the UDFs available for use in Neo4j. They can be invoked with the following Cypher commands:

```cypher
geseq.fromFASTQ // Import sequences from a FASTQ file
geseq.fromFASTA // Import sequences from a FASTA file
geseq.translate // Translate sequences
geseq.rev // Reverse sequences
geseq.com // Complement sequences
geseq.rev_com // Reverse complement sequences
```


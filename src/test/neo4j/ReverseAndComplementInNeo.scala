package neo4j

import org.junit.jupiter.api.Test
import org.neo4j.driver._

/**
 * @author cai584770
 * @date 2024/8/16 7:44
 * @Version
 */
class ReverseAndComplementInNeo {
  val uri = "bolt://localhost:7687"
  val username = "neo4j"
  val password = "neo4j"

  @Test
  def mat(): Unit ={
    val driver: Driver = GraphDatabase.driver(uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val cypherQuery = "MATCH (n) RETURN n"

      val result = session.run(cypherQuery)

      while (result.hasNext) {
        val record = result.next()
        println(record.get("n").asNode())
      }
    } finally {
      session.close()
      driver.close()
    }
  }


}

package exp.neo4j.base

import org.junit.jupiter.api.Test
import org.neo4j.driver.types.Node
import org.neo4j.driver.{AuthTokens, Driver, GraphDatabase, Session, SessionConfig}

/**
 * @author cai584770
 * @date 2024/10/23 18:11
 * @Version
 */
class ConnectTest {

  @Test
  def connect(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val result = session.run("MATCH (n) RETURN id(n) as nodeId, n.storage as storage, n.information as information;")
      while (result.hasNext) {
        val record = result.next()
        val nodeId = record.get("nodeId").asInt()
        val storage = record.get("storage").asString()
        val information = record.get("information").asString()

        println(s"Node ID: $nodeId, Storage: $storage, Information: $information")
      }
    } catch {
      case e: Exception =>
        println(s"An error occurred: ${e.getMessage}")
    } finally {
      session.close()
      driver.close()
    }
  }


}

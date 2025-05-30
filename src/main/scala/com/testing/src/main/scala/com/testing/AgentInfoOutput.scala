package epi_project.testing

import com.bharatsim.engine.Context
import com.bharatsim.engine.basicConversions.DoubleValue
import com.bharatsim.engine.basicConversions.encoders.DefaultEncoders._
import com.bharatsim.engine.basicConversions.decoders.DefaultDecoders._
import com.bharatsim.engine.graph.patternMatcher.MatchCondition._
import epi_project.testing
import com.bharatsim.engine.models.{Node, StatefulAgent}
import epi_project.testing.InfectionStatus._
import com.bharatsim.engine.listeners.CSVSpecs
import scala.collection.mutable.ListBuffer // this is the new import statement



class AgentInfoOutput(context: Context) extends CSVSpecs {

  override def getHeaders: List[String] = List("AgentID",   "InfectingAgent", "InfectedAt", "NumberOfSecondaryInfections", "InfectionState","DayOnWhichAgentGotInfected")

  override def getRows(): List[List[Any]] = {
    val rows = ListBuffer.empty[List[String]]

    val graphProvider = context.graphProvider
    val label = "Person"
    val nodes = graphProvider.fetchNodes(label)

    nodes.foreach(node => {
      val person = node.as[Person] // @TODO: problem 
      if(!person.isSusceptible) {
        rows.addOne(List(
          person.id.toString,
          person.infectingAgent.toString,
          person.infectedAt.toString,
          person.agentsInfected.toString,
          person.infectionState.toString,
          person.dayAtWhichPersonIsInfected.toString))
      }
    })
    rows.toList

  }

}


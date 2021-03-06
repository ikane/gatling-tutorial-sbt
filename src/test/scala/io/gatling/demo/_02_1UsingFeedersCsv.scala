package io.gatling.demo

import io.gatling.core.Predef._
import io.gatling.demo.config.BasicHttpProtocol
import io.gatling.http.Predef._

import scala.concurrent.duration._

class _02_1UsingFeedersCsv extends Simulation {

  val httpProtocol = BasicHttpProtocol.GitHubProtocolBuilder

  val feeder = csv("data/github-users.csv").random

  val findRepos = scenario("Find repos from GitHub")
    .feed(feeder)
    .exec(http("find_enriquezrene_repos")
      .get("/users/${user}/repos")
      .check(status.is(200)))

  setUp(findRepos.inject(constantUsersPerSec(2) during (2 seconds))).protocols(httpProtocol)

  // feeder: collection to populate the request, it will provide a user from the Array in a random way
  // constantUsersPerSec: how many request per second
  // during: how along the simulation will run

}

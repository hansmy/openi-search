name := """openi-search"""

version := "0.1-SNAPSHOT"

libraryDependencies ++= Seq(
  // Select Play modules
  //jdbc,      // The JDBC connection pool and the play.api.db API
  //anorm,     // Scala RDBMS Library
  //javaJdbc,  // Java database API
  //javaEbean, // Java Ebean plugin
  //javaJpa,   // Java JPA plugin
  //filters,   // A set of built-in filters
  javaCore,  // The core Java API
  // WebJars pull in client-side web libraries
  "org.webjars" %% "webjars-play" % "2.2.0",
  "org.webjars" % "bootstrap" % "2.3.1",  
  "com.wordnik" %% "swagger-core" % "1.3.0",
  "com.wordnik" % "swagger-play2_2.10" % "1.3.2",
  "com.wordnik" % "swagger-play2-utils_2.10" % "1.3.1",
  "com.dropbox.core" % "dropbox-core-sdk" % "1.7.6",
  "org.apache.solr" % "solr-solrj" % "4.6.0"
  //"org.slf4j" % "slf4j-simple" % "1.7.5"            
   // Add your own project dependencies in the form:
  // "group" % "artifact" % "version"  
)



play.Project.playScalaSettings

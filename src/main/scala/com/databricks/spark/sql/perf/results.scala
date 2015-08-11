/*
 * Copyright 2015 Databricks Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.databricks.spark.sql.perf

/**
 * The performance results of all given queries for a single iteration.
 * @param timestamp The timestamp indicates when the entire experiment is started.
 * @param iteration The index number of the current iteration.
 * @param tags Tags of this iteration (variations are stored at here).
 * @param configuration Configuration properties of this iteration.
 * @param results The performance results of queries for this iteration.
 */
case class ExperimentRun(
    timestamp: Long,
    iteration: Int,
    tags: Map[String, String],
    configuration: BenchmarkConfiguration,
    results: Seq[BenchmarkResult])

/**
 * The configuration used for an iteration of an experiment.
 * @param sparkVersion The version of Spark.
 * @param sqlConf All configuration properties related to Spark SQL.
 * @param sparkConf All configuration properties of Spark.
 * @param defaultParallelism The default parallelism of the cluster.
 *                           Usually, it is the number of cores of the cluster.
 */
case class BenchmarkConfiguration(
    sparkVersion: String = org.apache.spark.SPARK_VERSION,
    sqlConf: Map[String, String],
    sparkConf: Map[String,String],
    defaultParallelism: Int)

/**
 * The result of a query.
 * @param name The name of the query.
 * @param joinTypes The type of join operations in the query.
 * @param tables The tables involved in the query.
 * @param parsingTime The time used to parse the query.
 * @param analysisTime The time used to analyze the query.
 * @param optimizationTime The time used to optimize the query.
 * @param planningTime The time used to plan the query.
 * @param executionTime The time used to execute the query.
 * @param breakDown The breakdown results of the query plan tree.
 */
case class BenchmarkResult(
    name: String,
    joinTypes: Seq[String] = Nil,
    tables: Seq[String] = Nil,
    parsingTime: Option[Double] = None,
    analysisTime: Option[Double] = None,
    optimizationTime: Option[Double] = None,
    planningTime: Option[Double] = None,
    executionTime: Option[Double] = None,
    breakDown: Seq[BreakdownResult] = Nil,
    queryExecution: Option[String] = None,
    failure: Option[Failure] = None)

/**
 * The execution time of a subtree of the query plan tree of a specific query.
 * @param nodeName The name of the top physical operator of the subtree.
 * @param nodeNameWithArgs The name and arguments of the top physical operator of the subtree.
 * @param index The index of the top physical operator of the subtree
 *              in the original query plan tree. The index starts from 0
 *              (0 represents the top physical operator of the original query plan tree).
 * @param executionTime The execution time of the subtree.
 */
case class BreakdownResult(
    nodeName: String,
    nodeNameWithArgs: String,
    index: Int,
    executionTime: Double)

case class Failure(className: String, message: String)
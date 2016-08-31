/*
 * Copyright (c) 2016, GigaSpaces Technologies, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.insightedge.spark.fixture

import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import org.insightedge.spark.implicits.basic._
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, Suite}

/**
  * Suite mixin that starts and stops Spark before and after each test
  *
  * @author Oleksiy_Dyagilev
  */
trait Spark extends BeforeAndAfterAll with BeforeAndAfterEach {
  self: Suite with IEConfig with InsightEdge =>

  var spark: SparkSession = _
  var sc: SparkContext = _

  def createSpark(): SparkSession = {
    SparkSession
      .builder()
      .appName("insightedge-test")
      .master("local[2]")
      .insightEdgeConfig(ieConfig)
      .getOrCreate()
  }

  override protected def beforeEach() = {
    spark = createSpark()
    sc = spark.sparkContext
    super.beforeEach()
  }

  override protected def afterEach() = {
    spark.stop()
    // TODO: move stopInsightEdgeContext() to SparkSession
    //    sc.stopInsightEdgeContext()
    super.afterEach()
  }

}

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

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.scalatest.Suite

/**
  * Suite mixin that enables InsightEdge off-heap external block manager
  *
  * @author Oleksiy_Dyagilev
  */
trait OffHeap extends Spark {
  self: Suite with IEConfig with InsightEdge =>

  override def createSpark(): SparkSession = {
    SparkSession
      .builder()
      .appName("insightedge-test")
      .config("spark.externalBlockStore.blockManager", "org.apache.spark.storage.InsightEdgeBlockManager")
      .master("local[2]")
      .getOrCreate()
  }

}

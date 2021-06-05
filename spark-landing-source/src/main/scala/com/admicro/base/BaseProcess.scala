package com.admicro.base

import org.apache.spark.sql.SparkSession

abstract class BaseProcess(spark: SparkSession) extends Serializable {
  def run(dateRun: String)
  def dataInput(dateRun: String)
  def process(dateRun: String)
  def saveData(dateRun: String)

}

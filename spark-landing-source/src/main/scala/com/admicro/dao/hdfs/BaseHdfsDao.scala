package com.admicro.dao.hdfs

import org.apache.spark.sql.{Dataset, SparkSession}

abstract class BaseHdfsDao[T] (spark: SparkSession) extends Serializable {
  def getData(input: String): Dataset[T]
}

package com.admicro.utils.config

import com.typesafe.config.ConfigFactory

import java.io.File

object ConfigInfo extends Serializable {
  val config = ConfigFactory.parseFile(new File("config.properties"))

  //app config
  val APP_NAME: String = config.getString("app.name")

  //input
  //hdfs config
  val HDFS_INPUT: String = config.getString("hdfs.pc.input")

  //MYSQL config
  val MYSQL_USERNAME: String = config.getString("mysql.user")
  val MYSQL_PASSWORD: String= config.getString("mysql.pass")
  val MYSQL_DRIVER: String = config.getString("mysql.driver")
  val MYSQL_TABLE: String = config.getString("mysql.table.name")
  val MYSQL_URL: String = config.getString("mysql.url")


}

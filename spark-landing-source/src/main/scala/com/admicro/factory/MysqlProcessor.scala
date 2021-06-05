package com.admicro.factory

import com.admicro.factory.MysqlProcessor.configDate
import com.admicro.utils.config.{ConfigDate, ConfigInfo}
import org.apache.spark.sql.{DataFrame, SaveMode}

import java.sql.{Connection, DriverManager}
import java.util.Properties

object MysqlProcessor {
  val configDate = new ConfigDate
  def save(dataFrame: DataFrame, url: String, table: String): Unit = {
    val prop: Properties = new Properties()
    prop.setProperty("user",ConfigInfo.MYSQL_USERNAME)
    prop.setProperty("password",ConfigInfo.MYSQL_PASSWORD)
    prop.setProperty("driver",ConfigInfo.MYSQL_DRIVER)

    dataFrame.write
      .mode(SaveMode.Append)
      .jdbc(url,table,prop)
  }
  def deleteData(table: String, url: String, user: String, pass: String, driver: String): Unit={
    var conn: Connection = null
    try{
      Class.forName(driver)
      conn = DriverManager.getConnection(url, user, pass)
      conn.createStatement().executeUpdate("DELETE FROM "+ table + " WHERE dt = '" +configDate.getCurrentDate()+"'")
    }catch{
      case e: Exception => e.printStackTrace()
    }
  }
}

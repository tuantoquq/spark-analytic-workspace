package com.admicro

import com.admicro.base.BaseProcess
import com.admicro.process.PcAnalyticProcess
import com.admicro.utils.config.{ConfigDate, ConfigInfo, CustomLog}
import org.apache.spark.sql.SparkSession

import java.util.Calendar

object Main {
  val configDate = new ConfigDate
  def main(args: Array[String]): Unit = {
    var spark: SparkSession = null
    try{
      CustomLog.printLog("Date run: \t"+ configDate.getCurrentDate().toString)
      spark = SparkSession.builder
        .appName(ConfigInfo.APP_NAME + " on " + configDate.getCurrentDate().toString)
        .getOrCreate()
      val process: BaseProcess = new PcAnalyticProcess(spark)
      val currentHour = Calendar.getInstance().getTime.getHours
      if(currentHour >= 1) process.run(configDate.getCurrentDate().toString)
      else{
        process.run(configDate.getCurrentDate().minusDays(1).toString)
      }
    }catch{
      case e: Exception => {
        var appName: String = ConfigInfo.APP_NAME + " "
        if(spark != null){
          appName = spark.conf.get("spark.app.name")
        }
        e.printStackTrace()
      }
    }finally {
      if(spark != null){
        spark.close()
      }
    }
  }
}

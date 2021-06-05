package com.admicro.process

import com.admicro.base.BaseProcess
import com.admicro.dao.hdfs.LogPageViewDao
import com.admicro.factory.MysqlProcessor
import com.admicro.model.PcAnalyticModel.LogPageView
import com.admicro.utils.config.{ConfigInfo, CustomLog}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

class PcAnalyticProcess(spark: SparkSession) extends BaseProcess(spark){
  var pcData: Dataset[LogPageView] = _
  var logPcData: DataFrame = _
  override def run(dateRun: String): Unit = {
    //get data from hdfs
    dataInput(dateRun)
    //process data
    process(dateRun)
    //save data to database
    saveData(dateRun)
  }

  override def dataInput(dateRun: String): Unit = {
    pcData = pcDataInput(dateRun)
    CustomLog.printLog("Log pageview data:")
    pcData.show(10)
  }

  override def process(dateRun: String): Unit = {
    MysqlProcessor.deleteData(ConfigInfo.MYSQL_TABLE, ConfigInfo.MYSQL_URL,
      ConfigInfo.MYSQL_USERNAME, ConfigInfo.MYSQL_PASSWORD,ConfigInfo.MYSQL_DRIVER)
    logPcData = pcData.toDF()
  }

  override def saveData(dateRun: String): Unit = {
    if(logPcData == null){
      CustomLog.printLog("Data to save is empty!")
    }else{
        MysqlProcessor.save(logPcData,ConfigInfo.MYSQL_URL,ConfigInfo.MYSQL_TABLE)
    }
  }
  def pcDataInput(dateRun: String): Dataset[LogPageView] = {
    val pcLogDao = new LogPageViewDao(spark)
    val input = ConfigInfo.HDFS_INPUT + dateRun
    val pcLogs: Dataset[LogPageView] = pcLogDao.getData(input)
    pcLogs
  }
}

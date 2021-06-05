package com.admicro.dao.hdfs

import com.admicro.model.PcAnalyticModel.{LogPVOrigin, LogPageView, PcAnalyticInput}
import com.admicro.utils.config.CustomLog
import org.apache.spark.sql.{Dataset, SparkSession}

import java.sql.Date
import scala.language.postfixOps

class LogPageViewDao (spark: SparkSession) extends BaseHdfsDao [LogPageView] (spark){
  override def getData(input: String): Dataset[LogPageView] = {
    import spark.implicits._
//    val checkRef = spark.udf.register("checkRef",(referer: String, domain: String) => checkReferer(referer, domain))
    val checkDomain = spark.udf.register("checkDomain",(domain:String) => checkGoogle(domain))
    val data = spark.read.parquet(input)
      .filter("referer != '-1' and referer != '' and !(referer like %"+$"domain"+"%)")
      .select(
        $"dt",
        $"guid",
        $"d_guid",
        $"domain",
        $"path",
        $"referer",
        $"utm_source",
        $"utm_campaign"
      )
      .where(checkDomain($"domain") === 0)
      .as[LogPVOrigin]
      .map(x => PcAnalyticInput(
        dt = Date.valueOf(x.dt.split(" ")(0)),
        guid = x.guid,
        d_guid = x.d_guid,
        domain = x.domain,
        path = x.path,
        referer = x.referer,
        utm_source = x.utm_source,
        utm_campaign = x.utm_campaign
      ))
    data
  }
//  def checkReferer(referer: String, domain: String): Int = {
//    val splits = referer.split("""(//|/)""")
//    if(splits.length < 2){
////      CustomLog.printLog("Error referer: " + referer)
//      0
//    }else {
//      val r_domain = splits(1)
//      if (r_domain == domain || ("m." + r_domain) == domain || ("www." + r_domain) == domain
//        || r_domain == ("m." + domain) || r_domain == ("www." + domain)) {
//        1
//      } else {
//        0
//      }
//    }
//  }
  def checkGoogle(domain: String): Int = {
    if(domain.contains("google.com")){
      1
    }else{
      0
    }
  }
}

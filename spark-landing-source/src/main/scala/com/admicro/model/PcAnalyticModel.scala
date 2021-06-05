package com.admicro.model

import java.sql.Date


object PcAnalyticModel {
    case class LogPVOrigin(
                    var dt: String,
                    var guid: String,
                    var d_guid: String,
                    var domain: String,
                    var path: String,
                    var referer: String,
                    var utm_source: String,
                    var utm_campaign: String
                          )
    case class LogPageView(
                            var dt: Date,
                            var guid: String,
                            var d_guid: String,
                            var domain: String,
                            var path: String,
                            var referer: String,
                            var utm_source: String,
                            var utm_campaign: String
                          )
    object PcAnalyticInput{
      def apply(
       dt: Date,
       guid: String,
       d_guid: String,
       domain: String,
       path: String,
       referer: String,
       utm_source: String,
       utm_campaign: String
               ): LogPageView ={
        val pc = LogPageView(dt,guid,d_guid,domain, path, referer,utm_source, utm_campaign)
        pc
      }
    }
}

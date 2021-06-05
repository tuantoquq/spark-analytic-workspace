package com.admicro.utils.config

import java.text.SimpleDateFormat
import java.util.Calendar

object CustomLog {
  def log(): String={
    val calendar = Calendar.getInstance()
    val sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    val s = sdf.format(calendar.getTime)
    s + "\tLog Custom:\t"
  }
  def printLog(input: String): Unit={
    println(log() + input)
  }

}

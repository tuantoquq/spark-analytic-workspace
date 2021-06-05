package com.admicro

object Test {
  def main(args: Array[String]): Unit = {
    val input: String = ""
    val sp = input.split("""(//|/)""")
    sp.foreach(println)
    println(sp(1))
  }
}

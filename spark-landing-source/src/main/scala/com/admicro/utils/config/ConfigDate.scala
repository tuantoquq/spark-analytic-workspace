package com.admicro.utils.config

import java.time.LocalDate

class ConfigDate {
  def getCurrentDate(): LocalDate = {
    LocalDate.now()
  }
}


CREATE ALIAS HOWOLD AS '
  int howOld(java.sql.Date startDate, java.sql.Date endDate){
      if (startDate.compareTo(endDate) > 0) return -1;

      java.util.Calendar startCalendar = new GregorianCalendar();
      startCalendar.setTime(startDate);
      java.util.Calendar endCalendar = new GregorianCalendar();
      endCalendar.setTime(endDate);

      if (endCalendar.get(Calendar.YEAR) == startCalendar.get(Calendar.YEAR)) return 0;

      int age = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
      if ( (startCalendar.get(Calendar.MONTH) == endCalendar.get(Calendar.MONTH) &&
              startCalendar.get(Calendar.DAY_OF_MONTH) > endCalendar.get(Calendar.DAY_OF_MONTH)) ||
            startCalendar.get(Calendar.MONTH) > endCalendar.get(Calendar.MONTH)){
            age = age - 1;
      }
      return age;
  }
';

CREATE ALIAS IF NOT EXISTS HOWOLD AS '
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

CREATE ALIAS IF NOT EXISTS TONUM AS '
    int toNum(java.lang.String protocolNumberString){
        java.lang.Integer answer;
        try {
            answer = java.lang.Integer.parseInt(protocolNumberString);
        } catch (java.lang.Exception e) {
            java.lang.StringBuilder builder = new java.lang.StringBuilder();
            for (int i = 0; i < protocolNumberString.length(); i++){
                char ch = protocolNumberString.charAt(i);
                if (java.lang.Character.isDigit(ch)){
                    builder.append(protocolNumberString.charAt(i));
                } else {
                    break;
                }
            }
            try {
                answer = java.lang.Integer.parseInt(builder.toString());
            } catch (java.lang.Exception ex){
                answer = java.lang.Integer.valueOf(0);
            }
        }
        return answer;
    }
';

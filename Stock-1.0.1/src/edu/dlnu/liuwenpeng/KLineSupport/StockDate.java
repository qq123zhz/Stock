package edu.dlnu.liuwenpeng.KLineSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

 class StockDate {
   /*
    * ������������ǶԲ���һ������list�����������С��Χ�ڵ�һ������
    */
	public StockDate() {
	
	}
	/*
	 * �����ڽ��м�һ����
	 */
	public Date add(Date date,int AddDay) {
		 Calendar c = Calendar.getInstance();
		 c.setTime(date);
		 c.add(Calendar.DAY_OF_MONTH,AddDay);
		 date=c.getTime();
		return date;
    		
	}
	
	public Date getMindate(List<Date> dates){
		Date minDate=dates.get(0);
		//System.out.println("min is "+minDate);
	/*	for (Date date : dates) {
			if (date.compareTo(minDate)<=-1) {
				minDate=date;
			}
		}*/
		
		return minDate;
		
	}
	public Date getMaxdate(List<Date> dates){
		Date maxDate=dates.get(dates.size()-1);
		//System.out.println("max is "+maxDate);
		/*for (Date date : dates) {
			if (date.compareTo(maxDate)>=1) {
				maxDate=date;
			}
		}*/
		
		return maxDate;
		
	}
	
	/*
	 * ��������С���ڷ�Χ����������
	 */
public List<Date> CreateRangeDate(List<Date> dates) {
	List<Date> rangeDates=new ArrayList<>();
	Date primeDate=getMindate(dates);
	
	while(primeDate.compareTo(getMaxdate(dates))!=0) {
		
		Date rangeDate=add(primeDate,1);
		primeDate=rangeDate;
		rangeDates.add(primeDate);	
	}
	return rangeDates;
	
}
	

}

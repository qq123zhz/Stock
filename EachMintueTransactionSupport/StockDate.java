package edu.dlnu.liuwenpeng.EachMintueTransactionSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.dlnu.liuwenpeng.NewTime.Hour;
import edu.dlnu.liuwenpeng.NewTime.Minute;

 class StockDate {
   /*
    * ������������ǶԲ���һ������list�����������С��Χ�ڵ�һ������
    */
	public StockDate() {
	
	}
	/*
	 * �����ڽ��м�һ����
	 */
	public Minute add(Minute date,int Add) {
		Hour hour=date.getHour();
		
		int minute=date.getMinute()+Add;	
			if (date.getMinute()+1>59) {
				int intHour=hour.getHour()+1;
				hour=new Hour(intHour, hour.getDay());
				minute=0;
			}
				
		return new Minute(minute, hour); 
    		
	}
	
	public Minute getMindate(List<Minute> dates){
		Minute minDate=dates.get(0);
		return minDate;
		
	}
	public Minute getMaxdate(List<Minute> dates){
		Minute maxDate=dates.get(dates.size()-1);	
		return maxDate;
		
	}
	
	/*
	 * ��������С���ڷ�Χ����������
	 */
public List<Minute> CreateRangeDate(List<Minute> dates) {
	List<Minute> rangeDates=new ArrayList<>();
	Minute primeDate=getMindate(dates);

	while(primeDate.compareTo(getMaxdate(dates))!=0) {
		
		Minute rangeDate=add(primeDate,1);
		primeDate=rangeDate;
		rangeDates.add(primeDate);	
	}
	return rangeDates;
	
}
	

}

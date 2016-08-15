package edu.dlnu.liuwenpeng.KLineSupport;

import java.util.Date;
import java.util.List;

import org.jfree.chart.axis.SegmentedTimeline;

class StockTimeLine {

	private SegmentedTimeline timeline = new SegmentedTimeline(
			SegmentedTimeline.DAY_SEGMENT_SIZE, 7, 0);
	private StockDate stockDate = new StockDate();

	public StockTimeLine() {

	}

	/*
	 * ����data�����ڵ����� �ȶ�������������������õ���Ҫ��ɾ��������
	 */
	@SuppressWarnings("static-access")
	public void ExceptionDate(List<Date> date) {
		timeline.setStartTime(timeline.firstMondayAfter1900());
		List<Date> rangeDates = stockDate.CreateRangeDate(date);
		for (Date date2 : rangeDates) {
			if (!date.contains(date2)) {

				timeline.addException(date2);
			}

		}

	}

	public Date getMaxDate(List<Date> date) {
		Date maxDate = stockDate.getMaxdate(date);
		return stockDate.add(maxDate, 1);
	}

	public Date getMinDate(List<Date> date) {
		Date minDate = stockDate.getMindate(date);
		return stockDate.add(minDate, -1);
	}

	/*
	 * �õ�����ʱ����
	 */
	public SegmentedTimeline finalTimeline() {
		return this.timeline;
	}

}

package edu.dlnu.liuwenpeng.KLineSupport;

import java.awt.Color;
import java.awt.Paint;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.HighLowItemLabelGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import edu.dlnu.liuwenpeng.render.CandlestickRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.OHLCDataset;

import edu.dlnu.liuwenpeng.Chart.StockChart;
import edu.dlnu.liuwenpeng.DataInterface.Data;
import edu.dlnu.liuwenpeng.DataInterface.DataItem;

/*
 * ����ʵ��StockChart�ӿ�
 * 
 */

public class KLineChart implements StockChart {

	@SuppressWarnings("rawtypes")
	private List ls = new ArrayList();// ����һ�������������ݵļ�����List
	@SuppressWarnings("rawtypes")
	private Map map = null;// ������ʾһ����¼
	private DateAxis dateaxis = new DateAxis("");
	private JFreeChart jChart;
	private TimeSeriesCollection vol;
	private TimeSeries series2 = new TimeSeries("");
	private DefaultHighLowDataset k_line = null;
	private StockTimeLine timeline = new StockTimeLine();
	private List<Date> rdates = new ArrayList<>();
	private List<Double> highdata = new ArrayList<>();
	private List<Double> lowdata = new ArrayList<>();
	private Date maxdate;
	private Date mindate;
	private Double maxValueOfKLine;
	private Double minValieOfKLine;
	private DataAxisRangeOfKLine data=new DataAxisRangeOfKLine();
	private TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
	private JFreeChart jfreechart;
	/**
	 * ��ձ������ݵļ�����List
	 */
	@Override
	public void Clear() {
		ls.clear();
	}

	/**
	 * @return jfreechart���� ����һ�����ϵĻ���
	 */
	@SuppressWarnings({ "rawtypes" })
	private JFreeChart Create() {
		Map m = createDatasetMap();// �����ݶ�����ȡ���������͵Ķ�����Ҫ��������ʾ���ߵ�ʱ����(IntervalXYDataset)�����������ʾ�����ߺͳɽ���������ͼ����(OHLCDataset)
		OHLCDataset k_line = (OHLCDataset) m.get("k_line");
		String stock_name = (String) m.get("stock_name");
		vol = (TimeSeriesCollection) m.get("vol");
		
		// ����һ��CandlestickRenderer������ͼ����ʹ�ã�Ŀ���Ƕ�����ͼ�������ʾ���е�����������Ҫ�ǵ�������ʾ�Ŀ�Ȳ����������ʾ
		CandlestickRenderer candlesRender = new CandlestickRenderer();
		candlesRender.setCandleWidth(10D);
		
		candlesRender.setBaseToolTipGenerator(new HighLowItemLabelGenerator(
				new SimpleDateFormat("yyyy-MM-dd"), new DecimalFormat("0.00")));
		// candlesRender.setAutoWidthGap(0D);
		NumberAxis numberAxis = new NumberAxis("");
		numberAxis.setRange(minValieOfKLine*0.95,maxValueOfKLine*1.05);
		NumberAxis y2Axis = new NumberAxis();

		y2Axis.setAutoRange(true);
		XYPlot candlePlot = new XYPlot(k_line, null, numberAxis, candlesRender);// ����һ���������͵�Plot��Ŀ����Ϊ�˰�Chart���ϰ�غ��°�ؽ���������γ�һ��������K��ͼ

		edu.dlnu.liuwenpeng.render.XYBarRenderer xyBarRender = new edu.dlnu.liuwenpeng.render.XYBarRenderer(0.4d) {
			private static final long serialVersionUID = 1L;// Ϊ�˱�����־�����Ϣ�����趨��ֵ

			public Paint getItemPaint(int i, int j) {// �����ڲ������������յĳɽ�������ͼ����ɫ��K��ͼ����ɫ����һ��

				if (k_line.getCloseValue(i, j) > k_line.getOpenValue(i, j)) {// ���̼۸��ڿ��̼ۣ���Ʊ���ǣ�ѡ�ù�Ʊ���ǵ���ɫ
					
					return candlesRender.getUpPaint();
				} else {
					return candlesRender.getDownPaint();
				}
			}
		};
		
		
		XYPlot plot2 = new XYPlot(vol, null, y2Axis, xyBarRender);
		
		dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 1,
				new SimpleDateFormat("M/dd")));// �������ڸ�ʽ����ʾ���ڵļ��
		dateaxis.setLowerMargin(0.01D);
		dateaxis.setUpperMargin(0.01D);
		dateaxis.setAutoRange(false);
		dateaxis.setRange(mindate, maxdate);
		dateaxis.setTimeline(timeline.finalTimeline());
		dateaxis.setTickLabelPaint(Color.white);
		numberAxis.setTickLabelPaint(Color.white);
		y2Axis.setTickLabelPaint(Color.white);
		CombinedDomainXYPlot combineXY = new CombinedDomainXYPlot(dateaxis);
		combineXY.add(candlePlot, 3);
		combineXY.add(plot2, 1);
		combineXY.setGap(2D);
		candlePlot.setBackgroundPaint(Color.black);
		plot2.setBackgroundPaint(Color.black);
		combineXY.setDomainGridlinesVisible(false);
		
		jfreechart = new JFreeChart(stock_name,
				JFreeChart.DEFAULT_TITLE_FONT, combineXY, false);
		jfreechart.setBackgroundPaint(Color.black);
		return jfreechart;
	}

	
	/**
	 * ����һ���µ�Map,����һ�пյļ�¼
	 */
	@SuppressWarnings("rawtypes")
	/*
	 * �½�һ���洢��
	 */
	private void insertRecord() {
		map = new HashMap();
	}

	/**
	 * @param key
	 * @param value
	 *            Ϊ��ǰ��¼����ֵ
	 */
	@SuppressWarnings("unchecked")
	private void setValue(String key, String value) {
		map.put(key, value);
	}

	/**
	 * �ѵ�ǰ��¼��ӵ���¼��List��
	 */
	@SuppressWarnings("unchecked")
	private void postRecord() {
		ls.add(map);
	}

	/**
	 * @return map���� ��ÿһ�м�¼��ȡ���ض�ֵ���������ɸ������͵ľ��ߺ�������ͼ
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private Map createDatasetMap() {
		Map m = new HashMap();
		int count = ls.size();
		Date adate[] = new Date[count];
		double high[] = new double[count];
		double low[] = new double[count];
		double close[] = new double[count];
		double open[] = new double[count];
		double volume[] = new double[count];
		Date adate1[] = new Date[count];
		Date date = new Date();
		String stock_name = null;
		Calendar cal = Calendar.getInstance();

		for (int j = 0; j < ls.size(); j++) {
			Map vMap = (Map) ls.get(j);

			String year = vMap.get("issue_date").toString().substring(0, 4);

			int a = Integer.parseInt(year);
			String month = vMap.get("issue_date").toString().substring(4, 6);

			int b = Integer.parseInt(month);
			String day = vMap.get("issue_date").toString().substring(6, 8);

			int c = Integer.parseInt(day);
			String time = year + "-" + month + "-" + day;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			try {
				date = df.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			stock_name = (String) vMap.get("stock_name");
			Date issue_date = date;
			double open_value = Double.parseDouble(vMap.get("open_value")
					.toString());
			double high_value = Double.parseDouble(vMap.get("high_value")
					.toString());
			double low_value = Double.parseDouble(vMap.get("low_value")
					.toString());
			double close_value = Double.parseDouble(vMap.get("close_value")
					.toString());

			double volume_value = Double.parseDouble(vMap.get("volume_value")
					.toString()) / 10000000;
			series2.addOrUpdate(new Day(c, b, a), volume_value);
            
			cal.setTime(issue_date);
			adate[j] = issue_date;
			high[j] = high_value;
			low[j] = low_value;
			close[j] = close_value;
			open[j] = open_value;
			volume[j] = 0;
			rdates.add(issue_date);
			highdata.add(high_value);
			lowdata.add(low_value);
			//System.out.println(issue_date.toString()+dateaxis.getTimeline().containsDomainValue(issue_date));
			// timeline.ExceptionDate(issue_date);
		}
		
		
		timeline.ExceptionDate(rdates);
		maxdate=timeline.getMaxDate(rdates);
		mindate=timeline.getMinDate(rdates);
		maxValueOfKLine=data.getMaxValue(highdata);
		minValieOfKLine=data.getMinValue(lowdata);
		// �����ɽ������ݵļ���
		timeSeriesCollection.addSeries(series2);
		k_line = new DefaultHighLowDataset("", adate, high, low, close, open,
				volume);
		// �Ѹ������͵�ͼ�����ŵ�Map���Ϊ���������ṩʹ��
		m.put("vol", timeSeriesCollection);
		m.put("k_line", k_line);
		m.put("stock_name", stock_name);

		return m;
	}

	@SuppressWarnings({ "rawtypes" })
	/*
	 * ���Ļ������ݷ���
	 */
	private void ChangeChart(JFreeChart chart1) {
		Map m = createDatasetMap();
		OHLCDataset k_line = (OHLCDataset) m.get("k_line");

		JFreeChart chart = chart1;
		CombinedDomainXYPlot plot = (CombinedDomainXYPlot) chart.getPlot();

		for (int j = 0; j < ls.size(); j++) {
			Map vMap = (Map) ls.get(j);
			String year = vMap.get("issue_date").toString().substring(0, 4);
			String month = vMap.get("issue_date").toString().substring(4, 6);
			String day = vMap.get("issue_date").toString().substring(6, 8);
			String time = year + "-" + month + "-" + day;
			double vol = Double.parseDouble((String) vMap.get("volume_value")) / 10000000;
			series2.addOrUpdate(Day.parseDay(time), vol);
		}
		dateaxis.setRange(mindate, maxdate);
        dateaxis.setTimeline(timeline.finalTimeline());
		List list = plot.getSubplots();
		XYPlot candlePlot = (XYPlot) list.get(0);
		
		candlePlot.setDataset(0, k_line);
	}

	
	public ChartPanel createDemoPanel() {
		JFreeChart jfreechart = Create();
		return new ChartPanel(jfreechart);
	}
	
	
	/*private void ExChangeChart(JFreeChart chart1){
		Map m = createDatasetMap();
		OHLCDataset k_line = (OHLCDataset) m.get("k_line");

		JFreeChart chart = chart1;
		CombinedDomainXYPlot plot = (CombinedDomainXYPlot) chart.getPlot();
		series2.clear();
		for (int j = 0; j < ls.size(); j++) {
			Map vMap = (Map) ls.get(j);
			String year = vMap.get("issue_date").toString().substring(0, 4);
			String month = vMap.get("issue_date").toString().substring(4, 6);
			String day = vMap.get("issue_date").toString().substring(6, 8);
			String time = year + "-" + month + "-" + day;
			double vol = Double.parseDouble((String) vMap.get("volume_value")) / 10000000;
			series2.addOrUpdate(Day.parseDay(time), vol);
		}
		dateaxis.setRange(mindate, maxdate);
        dateaxis.setTimeline(timeline.finalTimeline());
		List list = plot.getSubplots();
		XYPlot candlePlot = (XYPlot) list.get(0);
		
		candlePlot.setDataset(0, k_line);
	}*/
	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.dlnu.liuwenpeng.Chart.StockChart#createChart(edu.dlnu.liuwenpeng.
	 * DataInterface.Data) ����Ľӿڷ�����������������
	 */
	public JFreeChart createChart(Data ohlcdata) {

		for (DataItem dataItem : ohlcdata) {
			insertRecord();
			setValue("stock_name", dataItem.get("code"));

			setValue("issue_date", dataItem.get("time"));// ʱ�����date��ʽ����
			setValue("open_value", dataItem.get("open"));
			setValue("high_value", dataItem.get("highest"));
			setValue("low_value", dataItem.get("lowest"));
			setValue("close_value", dataItem.get("close"));
			setValue("avg5", "0");
			setValue("avg10", "0");
			setValue("avg20", "0");
			setValue("avg60", "0");
			setValue("volume_value", dataItem.get("volume"));
			setValue("vol_avg5", "0");
			postRecord();

		}

		jChart = Create();
	
		return jChart;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.dlnu.liuwenpeng.Chart.StockChart#UpdateChart(edu.dlnu.liuwenpeng.
	 * DataInterface.Data) ����ʵʱ�������ϻ���
	 */
	public synchronized void UpdateChart(Data data) {
		insertRecord();
		for (DataItem dataItem : data) {
		
			setValue("stock_name", dataItem.get("code"));
			setValue("issue_date", dataItem.get("time"));// ʱ�����date��ʽ����
			setValue("open_value", dataItem.get("open"));
			setValue("high_value", dataItem.get("highest"));
			setValue("low_value", dataItem.get("lowest"));
			setValue("close_value", dataItem.get("close"));
			setValue("avg5", "0");
			setValue("avg10", "0");
			setValue("avg20", "0");
			setValue("avg60", "0");
			setValue("volume_value", dataItem.get("volume"));
			setValue("vol_avg5", "0");
			
			

		}
		postRecord();
		ChangeChart(jChart);
	}
	
	/*public synchronized void ExChangeChart(Data data) {
	
		for (DataItem dataItem : data) {
			insertRecord();
			setValue("stock_name", dataItem.get("code"));
			setValue("issue_date", dataItem.get("time"));// ʱ�����date��ʽ����
			setValue("open_value", dataItem.get("open"));
			setValue("high_value", dataItem.get("highest"));
			setValue("low_value", dataItem.get("lowest"));
			setValue("close_value", dataItem.get("close"));
			setValue("avg5", "0");
			setValue("avg10", "0");
			setValue("avg20", "0");
			setValue("avg60", "0");
			setValue("volume_value", dataItem.get("volume"));
			setValue("vol_avg5", "0");
			
			postRecord();

		}
		
		ExChangeChart(jChart);
		System.out.println("here");
	}
*/
}

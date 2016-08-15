package edu.dlnu.liuwenpeng.StockData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import edu.dlnu.liuwenpeng.DataInterface.Data;
import edu.dlnu.liuwenpeng.DataInterface.DataHelp;
import edu.dlnu.liuwenpeng.DataInterface.DataItem;
import edu.dlnu.liuwenpeng.DataInterface.DataItemAbstract;



public class QuoteData extends edu.dlnu.liuwenpeng.DataInterface.DataAbstract {
	private static QuoteData data = null;

	private QuoteData() {
		// TODO Auto-generated constructor stub
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE, 30);
		one_open_date = calendar.getTime().getTime();
		
		calendar.set(Calendar.HOUR_OF_DAY, 11);
		one_close_date = calendar.getTime().getTime();
		
		calendar.set(Calendar.HOUR_OF_DAY, 13);
		calendar.set(Calendar.MINUTE, 0);
		two_open_data = calendar.getTime().getTime();
		
		calendar.set(Calendar.HOUR_OF_DAY, 15);
		two_close_data = calendar.getTime().getTime();
	}

	public static QuoteData Init(List<String> d_v) {
		if (data == null) {
			data = new QuoteData();
			data.code_l = d_v;
			int n_ = 0;

			while (n_ != d_v.size()) {
				int num = d_v.size() - n_;
				if (num > 70)
					num = 70;
				List<String> d_sub = d_v.subList(n_, n_ + num);
				data._init(d_sub);
				n_ += num;
			}
		}
		return data;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		int n_ = 0;
		while (n_ != code_l.size()) {
			int num = code_l.size() - n_;
			if (num > 70)
				num = 70;
			List<String> d_sub = code_l.subList(n_, n_ + num);
			_update(d_sub);
			n_ += num;
		}
		sort();
	}
	
	@Override
	public void setSortItem(String name) {
		QuoteDataItem.sort_item = name;
	}

	private void _init(List<String> d_v) {
		String qdString = HQBase.GetSecurityQuotes(d_v.toArray(new String[d_v.size()]));
		String[] n_q = qdString.split("\n");
		for (int i = 1; i != n_q.length; ++i) {
			String[] dString = n_q[i].split("\t");
			List<String> d_ = new ArrayList<>();
			d_.add(dString[1]); // code
			d_.add(dString[0]); // market
			d_.add(dString[2]); // activity degree
			d_.add(DataHelp.format(dString[3])); // price
			d_.add(DataHelp.format(dString[4])); // close
			d_.add(DataHelp.format(dString[5])); // open
			d_.add(DataHelp.format(dString[6])); // highest
			d_.add(DataHelp.format(dString[7])); // lowest
			d_.add(dString[8]); // time
			d_.add(DataHelp.format(dString[10])); // total
			d_.add(DataHelp.format(dString[11])); // pratyaksa
			d_.add(DataHelp.format(dString[12])); // total amount
			d_.add(DataHelp.format(dString[13])); // disk
			d_.add(DataHelp.format(dString[14])); // outer disk

			d_.add(DataHelp.format(dString[17])); // buy one
			d_.add(DataHelp.format(dString[18])); // sell one
			d_.add(DataHelp.format(dString[19])); // buy two
			d_.add(DataHelp.format(dString[20])); // sell two
			d_.add(DataHelp.format(dString[21])); // buy three
			d_.add(DataHelp.format(dString[22])); // sell three
			d_.add(DataHelp.format(dString[23])); // buy four
			d_.add(DataHelp.format(dString[24])); // sell four
			d_.add(DataHelp.format(dString[25])); // buy five
			d_.add(DataHelp.format(dString[26])); // sell five
			
			d_.add(DataHelp.format(get_gains(dString[3], dString[4])));
			d_.add(DataHelp.format(get_amplitude(dString[6], dString[7], dString[4])));
			d_.add(DataHelp.format(get_QRR(dString[1], dString[10])));
			add(new QuoteDataItem(d_));
		}
		sort();
	}

	private void _update(List<String> d_v) {
		String qdString = HQBase.GetSecurityQuotes(d_v.toArray(new String[d_v.size()]));
		String[] n_q = qdString.split("\n");
		for (int i = 1; i != n_q.length; ++i) {
			String[] dString = n_q[i].split("\t");
			DataItem d_ = get(i - 1);

			d_.set(2, dString[2]); // activity degree
			d_.set(3, DataHelp.format(dString[3])); // price
			d_.set(5, DataHelp.format(dString[5])); // open
			d_.set(6, DataHelp.format(dString[6])); // highest
			d_.set(7, DataHelp.format(dString[7])); // lowest

			d_.set(8, dString[8]); // time
			d_.set(9, DataHelp.format(dString[10])); // total
			d_.set(10, DataHelp.format(dString[11])); // pratyaksa
			d_.set(11, DataHelp.format(dString[12])); // total amount
			d_.set(12, DataHelp.format(dString[13])); // disk
			d_.set(13, DataHelp.format(dString[14])); // outer disk

			d_.set(14, DataHelp.format(dString[17])); // buy one
			d_.set(15, DataHelp.format(dString[18])); // sell one
			d_.set(16, DataHelp.format(dString[19])); // buy two
			d_.set(17, DataHelp.format(dString[20])); // sell two
			d_.set(18, DataHelp.format(dString[21])); // buy three
			d_.set(19, DataHelp.format(dString[22])); // sell three
			d_.set(20, DataHelp.format(dString[23])); // buy four
			d_.set(21, DataHelp.format(dString[24])); // sell four
			d_.set(22, DataHelp.format(dString[25])); // buy five
			d_.set(23, DataHelp.format(dString[26])); // sell five
			
			d_.set(24, DataHelp.format(get_gains(dString[3], dString[4])));
			d_.set(25, DataHelp.format(get_amplitude(dString[6], dString[7], dString[4])));
			d_.set(26, DataHelp.format(get_QRR(dString[1], dString[10])));
		}
	}

	// ��ȡ�Ƿ�
	private double get_gains(String price, String close) {
		double price_ = Double.valueOf(price);
		double close_ = Double.valueOf(close);
		return (price_ - close_) / close_;
	}

	// ��ȡ���
	private double get_amplitude(String highest, String lowest, String close) {
		double highest_ = Double.valueOf(highest);
		double lowest_ = Double.valueOf(lowest);
		double close_ = Double.valueOf(close);

		return (highest_ - lowest_) / close_;
	}

	// ��ȡ����
	public double get_QRR(String code, String _total) {
		double total = Double.valueOf(_total);
		int turnover = _get_turnover(code);
		int minutenum = _get_minutenum();
		
		return total / turnover / minutenum;
	}

	// ��ȡƽ��5�ճɽ���
	@SuppressWarnings("unchecked")
	public int _get_turnover(String code) {
		File file = new File("turnover.xml");
		if (!file.exists())
			_init_turnover();
		
		try (FileInputStream fis = new FileInputStream(file)) {
			SAXReader saxReader = new SAXReader();
			Document doc = saxReader.read(fis);
			Element root = doc.getRootElement();
			List<Element> c_ = root.elements();
			
			for (Element c_Element :c_) {
				if (!c_Element.attributeValue("value").equals(code))
					continue;
					
				String tur_s = c_Element.attributeValue("turnover");
				return Integer.valueOf(tur_s);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return 1;
	}

	private void _init_turnover() {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("root");
		try {
			XMLWriter xmlWriter = new XMLWriter(new FileWriter("turnover.xml"));
			for (String code : code_l) {
				Element cElement = root.addElement("code");
				cElement.addAttribute("value", code);

				Data kData = KData.Init(4, code);
				int _turnover = 0;
				for (int i = kData.size() - 2; i != kData.size() - 7; --i) {
					String tur_s = kData.get(i).get("turnover");
					System.out.println("kdata = " + tur_s);
					_turnover += Double.valueOf(tur_s);
				}
				_turnover /= 1200;
				cElement.addAttribute("turnover", Integer.toString(_turnover));
			}

			xmlWriter.write(doc);
			xmlWriter.flush();
			xmlWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int _get_minutenum() {
		long time = System.currentTimeMillis();
		
		if (time > two_close_data || time < one_open_date)
			return 240; // һ��Ŀ�ʼ���������һ��Ŀ��̷�����
		
		if (time > one_close_date && time < two_open_data)
			return 120; // 11:30 �� 13:00֮�䷵�����翪��ʱ��
		
		if (time > two_open_data)
			return _ms_to_m(time - one_open_date - 120);
		else
			return _ms_to_m(time - one_open_date);
	}
	
	private int _ms_to_m(long ms) {
		return (int)ms / 1000 / 60;
	}

	private List<String> code_l;
	private long one_open_date; // ����ʱ�� 9:30
	private long one_close_date; // ��һ������ʱ�� 11:30
	private long two_open_data; // �ڶ��ο���ʱ�� 1:00
	private long two_close_data; // ����ʱ�� 15:00
	
	public static void main(String[] args) {
		HQBase.Connect("121.14.110.200", 443);
		List<String> s_ = Arrays.asList("000001");
		QuoteData data = QuoteData.Init(s_);
		System.out.println(data.get(0).get("total"));
		System.out.println(data._get_minutenum());
		System.out.println(data._get_turnover("000001"));
		System.out.println(data.get_QRR("000001", data.get(0).get("total")));
		HQBase.Disconnect();
	}
}

class QuoteDataItem extends DataItemAbstract {
	/**
	 * �嵵����DataItem
	 * <p>
	 * get����:
	 * <p>
	 * 0|code: ֤�����, 1|market: �г�, 2|activity: ��Ծ��, 3|price: �ּ�,
	 * <p>
	 * 4|close: ��������, 5|open: ���տ���, 6|highest: ���, 7|lowest: ���, 8|time: ʱ�� ,
	 * <p>
	 * 9|total: ����, 10|pratyaksa: ����, 11|total amount: �ܽ��, 12|disk: ����,
	 * 13|outer disk: ����, 
	 * <p>
	 * 14, 16, 18, 20, 22|buy one-five:��[һ-��],
	 * <p>
	 * 15, 17, 19, 21, 23|sell one-five:��[һ-��]
	 * <p>
	 * 24|gains: �Ƿ�, 25|amplitude: ���, 26|QRR: ����
	 */
	public QuoteDataItem(List<String> d_) {
		// TODO Auto-generated constructor stub
		data = d_;

		name_ = Arrays.asList("code", "market", "activity", "price", "close", "open", "hlighest", "lowest", "time",
				"total", "pratyaksa", "total amount", "disk", "outer disk", "buy one", "sell one", "buy two",
				"sell two", "buy three", "sell three", "buy four", "sell four", "buy five", "sell five",
				"gains", "amplitude", "QRR");
	}
}
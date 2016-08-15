package edu.dlnu.liuwenpeng.StockData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.dlnu.liuwenpeng.DataInterface.DataHelp;
import edu.dlnu.liuwenpeng.DataInterface.DataItem;
import edu.dlnu.liuwenpeng.DataInterface.DataItemAbstract;


public  class KData extends edu.dlnu.liuwenpeng.DataInterface.DataAbstract {
	private static Map<String, KData> k_Data = new HashMap<>();
	
	private KData() {  }
	
	/**
	 * KData��������
	 * @param Category K������, 0->5����K��    1->15����K��    2->30����K��  3->1СʱK��    4->��K��  5->��K��  6->��K��  7->1����  8->1����K��  9->��K��  10->��K��  11->��K��
	 * @param code ֤�����
	 * @return
	 */
	public static KData Init(int Category, String code) {
		assert(Category >= 0 && Category < 12);//����  �൱��if�ж����
		KData k_ = k_Data.get(code);
		if (k_ == null) {
			k_ = new KData();
			k_Data.put(code, k_);
		}
		k_.Category = Category;
		k_.setCode(code);
		k_._init();
		
		return k_;
	}
	
	/**
	 * ���������ȡ����
	 * @param _Count
	 * @return
	 */
	public KData setCount(int _Count) {
		Count = _Count;
		return this;
	}
	
	@Override
	public synchronized void update() {
		// TODO Auto-generated method stub
		String d_ = HQBase.GetSecurityBars(Category, market, code, Count);
		String[] d_l = d_.split("\n");  
		for (int i = 1; i != d_l.length; ++i) {
			String[] is_ = d_l[i].split("\t");
			DataItem n_ = get(i - 1);
			n_.set(1, is_[0]); // time
			n_.set(3, DataHelp.format(is_[2])); // close
			n_.set(4, DataHelp.format(is_[3])); // highest
			n_.set(5, DataHelp.format(is_[4])); // lowest
			if (is_[5].length() > 3)
				n_.set(6, DataHelp.format(is_[5].substring(0, is_[5].length() - 2))); // volume
			else 
				n_.set(6, DataHelp.format("0")); // volume
			n_.set(7, DataHelp.format(is_[6])); // turnover
		}
	}

	private void setCode(String code) {
		this.code = code;
	}
	
	private void _init() {
		String d_ = HQBase.GetSecurityBars(Category, market, code, Count);
		String[] d_l = d_.split("\n");  
		for (int i = 1; i != d_l.length; ++i) {
			List<String> n_ = new ArrayList<>();
			String[] is_ = d_l[i].split("\t");
			n_.add(code); // code
			n_.add(is_[0]); // time
			n_.add(DataHelp.format(is_[1])); // open
			n_.add(DataHelp.format(is_[2])); // close
			n_.add(DataHelp.format(is_[3])); // highest
			n_.add(DataHelp.format(is_[4])); // lowest
			if (is_[5].length() > 3)
				n_.add(DataHelp.format(is_[5].substring(0, is_[5].length() - 2))); // volume
			else 
				n_.add(DataHelp.format("0")); // volume
			n_.add(DataHelp.format(is_[6])); // turnover
			add(new KDataItem(n_));
		}
	}
	
	private String code;
	
	private int Category = 0;
	private int market = 0;
	private int Count = 30;
}


class KDataItem extends DataItemAbstract {
	public KDataItem(List<String> d_) {
		// TODO Auto-generated constructor stub
		data = d_;
		name_ = Arrays.asList("code", "time",  "open", "close", "highest", "lowest", "turnover", "volume");
	}
}

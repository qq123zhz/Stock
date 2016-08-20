package edu.dlnu.liuwenpeng.KLineSupport;

import javax.swing.JFrame;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;

import edu.dlnu.liuwenpeng.StockData.HQBase;
import edu.dlnu.liuwenpeng.DataInterface.Data;
import edu.dlnu.liuwenpeng.StockData.KData;
import edu.dlnu.liuwenpeng.StockData.TransactionData;

public class Test {
   ChartFrame jFrame;
    KLineChart jfreechartTest=new KLineChart();
    
	public Test() {
		
		HQBase.Connect("211.100.49.196", 7709);
		Data data=KData.Init(4, "000007");
	   /* for (DataItem dataItem : data) {
			System.out.println(dataItem.get(1));
		}*/
		JFreeChart chart =jfreechartTest.createChart(data);
		jFrame=new ChartFrame("��Ʊ", chart);
		 jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Data data2=KData.Init(4,"000006");
		for (int i = 0; i <100; i++) {
		//	System.out.println(i);
		if(i<=4){
			data.update();
			data2.update();
			jfreechartTest.UpdateChart(data);//�������������
		}else {
			if (i==5) {
				jfreechartTest.Clear();
			}
			
			jfreechartTest.UpdateChart(data2);
		}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
				
				//data2.update();
				
				
			
			/*if (i>=9&&i<=14) {
				//data.update();
				Data data2=KData.Init(4,"000009");
				data2.update();
				jfreechartTest.UpdateChart(data2);
			}*/
		
		}
		HQBase.Disconnect();
	}
	
	public static void main(String args[]) 
	{
		new Test();
		
	}

}

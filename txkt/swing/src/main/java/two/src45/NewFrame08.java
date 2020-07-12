package two.src45;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewFrame08 extends JFrame{

	JLabel lblSelect;
	JComboBox<String> jcbGift;
	DefaultComboBoxModel<String> giftData;
	String[] data = {"A","B","C"};
	JButton btnOK;
	JLabel lblHome;
	JComboBox<String> jcbProvince;
	JComboBox<String> jcbCity;
	DefaultComboBoxModel<String> provinceData;
	DefaultComboBoxModel<String> cityData1;
	DefaultComboBoxModel<String> cityData2;
	DefaultComboBoxModel<String> cityData3;
	DefaultComboBoxModel<String>[] cityArray;
	
	public NewFrame08() { 
		init();
	}

	private void init() {
		Container container = this.getContentPane();//获得窗体的主体区域
		container.setLayout(null);
		
		lblSelect = new JLabel("你最想得到的礼物是");
		giftData = new DefaultComboBoxModel<String>();
		jcbGift = new JComboBox<String>(giftData);
		btnOK = new JButton("确定");
		lblHome = new JLabel("你的家乡是");
		jcbProvince = new JComboBox<String>();
		jcbCity = new JComboBox<String>();
		provinceData = new DefaultComboBoxModel<String>();
		provinceData.addElement("河北");
		provinceData.addElement("河南");
		provinceData.addElement("山东");
		jcbProvince.setModel(provinceData);
		cityData1 = new DefaultComboBoxModel<String>(new String[]{"石家庄","唐山","秦皇岛"});
		cityData2 = new DefaultComboBoxModel<String>(new String[]{"郑州","开封","洛阳"});
		cityData3 = new DefaultComboBoxModel<String>(new String[]{"济南","青岛","烟台"});
		cityArray = new DefaultComboBoxModel[3];
		cityArray[0] = cityData1;
		cityArray[1] = cityData2;
		cityArray[2] = cityData3;
		
		lblHome.setSize(200,40);
		lblHome.setLocation(30, 130);
		lblHome.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		jcbProvince.setSize(150,30);
		jcbProvince.setLocation(30, 180);
		jcbProvince.setFont(new Font("微软雅黑", Font.PLAIN, 20));
//		jcbProvince.addItemListener(new ItemListener() {
//			
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//				if(e.getStateChange()==ItemEvent.SELECTED){
//					System.out.println("---------");
//					int index = jcbProvince.getSelectedIndex();
//					jcbCity.setModel(cityArray[index]);
//				}
//			}
//		});
		jcbProvince.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("++++++++++");
				int index = jcbProvince.getSelectedIndex();
				jcbCity.setModel(cityArray[index]);
			}
		});
		
		jcbCity.setSize(150,30);
		jcbCity.setLocation(200, 180);
		jcbCity.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		jcbCity.setModel(cityData1);

		lblSelect.setSize(200,40);
		lblSelect.setLocation(30, 30);
		lblSelect.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		jcbGift.setSize(150,30);
		jcbGift.setLocation(30, 80);
		jcbGift.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		jcbGift.setEditable(true);

		giftData.addElement("三轮车");
		giftData.addElement("自行车");
		giftData.addElement("宝马车");
		
		btnOK.setSize(80,30);
		btnOK.setLocation(200, 80);
		btnOK.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(giftData.getSelectedItem());
				
			}
		});
		
		container.add(lblSelect);
		container.add(jcbGift);
		container.add(btnOK);
		container.add(lblHome);
		container.add(jcbProvince);
		container.add(jcbCity);
	}
}







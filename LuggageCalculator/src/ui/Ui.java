package ui;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import data.*;

public class Ui {

	private JFrame frame;
	private JTextField textField_jjc;
	private int flighttype;		//航班类型、区域
	private int seattype;		//舱室、座位类型
	private int special;        //特别旅客(南航明珠金卡会员、天合联盟超级精英,南航明珠银卡会员、天合联盟精英,留学生、劳务、海员)
	private float eairfare;     //经济舱票价
	private int isUSA;          //航线是否涉及美国
	private int isinfants;		//不占座婴儿

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ui window = new Ui();
					window.frame.setTitle("南航国内/国际航班行李托运计算");
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Ui() {
		this.eairfare = 0;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 805, 503);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel label_hq = new JLabel("航线区域");
		
		JLabel label_jc = new JLabel("机舱");
		
		JLabel label_cklx = new JLabel("乘客类型");
		
		JLabel label_sjmg = new JLabel("涉及美国航线");
		
		JLabel label_tbck = new JLabel("特别乘客");
		
		textField_jjc = new JTextField();
		textField_jjc.setColumns(10);
		textField_jjc.setText("0.0");
		
		JLabel label_jjcpj = new JLabel("经济舱票价(人民币)");
		
		String s1[] = { "国内航班", 
				"区域一：涉及日本、美洲、澳新、俄罗斯（注1）、迪拜（注2）的航程，以及新加坡始发（注3）与中国大陆间的航程", 
				"区域二：涉及中西亚（注4）的航程 ", 
				"区域三：涉及内罗毕的航程（注5） ", 
				"区域四：除日本、美洲、澳新、俄罗斯（注1）、迪拜（注2）、内罗毕、中西亚（注4）以外的国际/地区航程 ", 
				"涉及韩国始发与中国间的航程", 
				"涉及兰州/乌鲁木齐与迪拜之间"};
		JComboBox comboBox_hq = new JComboBox(s1);
		comboBox_hq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flighttype = comboBox_hq.getSelectedIndex();
				System.out.print(flighttype+":");
				System.out.println(comboBox_hq.getSelectedItem());
			}
		});
		
		
		String s2[] = {"头等舱","公务舱","明珠经济舱", "经济舱"};
		JComboBox comboBox_jc = new JComboBox(s2);
		comboBox_jc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seattype = comboBox_jc.getSelectedIndex();
				System.out.print(seattype+":");
				System.out.println(comboBox_jc.getSelectedItem());
			}
		});
		
		
		String s3[] = {"成人、儿童、占座婴儿","不占座婴儿"};
		JComboBox comboBox_cklx = new JComboBox(s3);
		comboBox_cklx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isinfants = comboBox_cklx.getSelectedIndex();
				System.out.print(isinfants+":");
				System.out.println(comboBox_cklx.getSelectedItem());
			}
		});
		
		String s4[] = {"否", "是"};
		JComboBox comboBox_sjmg = new JComboBox(s4);
		comboBox_sjmg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isUSA = comboBox_sjmg.getSelectedIndex();
				System.out.print(isUSA+":");
				System.out.println(comboBox_sjmg.getSelectedItem());
			}
		});
		
		String s5[] = {"", "南航明珠金卡会员、天合联盟超级精英","南航明珠银卡会员、天合联盟精英","留学生、劳务、海员 "};
		JComboBox comboBox_tbck = new JComboBox(s5);
		comboBox_tbck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				special = comboBox_tbck.getSelectedIndex();
				System.out.print(special+":");
				System.out.println(comboBox_tbck.getSelectedItem());
			}
		});
		
		JTextPane txtpnkgkg = new JTextPane();
		txtpnkgkg.setText("1、俄罗斯航线是指中国与俄罗斯（包括欧洲和亚洲部分）之间的航线。\r\n2、涉及兰州/乌鲁木齐与迪拜之间的免费行李额:高端经济舱为1件，32KG/件；经济舱为1件，23KG/件，头等与公务舱标准不变。 \r\n3、涉及新加坡始发的规则仅适用于新加坡始发至中国大陆间航程，不包括中国大陆始发至新加坡间航程。 \r\n4、中西亚航线包括中国与乌兹别克斯坦/塔吉克斯坦/哈萨克斯坦/吉尔吉斯斯坦/土库曼斯坦/伊朗/巴基斯坦/阿塞拜疆/格鲁吉亚之间的航线。 \r\n5、不含毛里求斯，毛里求斯行李额按区域四规则执行。\r\n6、不占座婴儿除了上表所示免费行李额外，还可免费托运1辆全折叠的轻便婴儿车或婴儿手推车。\r\n7、不涉及美国航线的，每件托运行李的最大重量不得超过32公斤（70磅）；涉及美国航线的，每件托运行李的最大重量不得超过45公斤（90磅）。 \r\n\r\n详情：http://www.csair.com/cn/tourguide/luggage_service/carryon_luggage/rules/index.shtml");
		
		JButton button_OK = new JButton("确定");
		button_OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(isinfants==1)//不占座婴儿
				{
					seattype = 4;
				}
				String s = textField_jjc.getText();
				if(flighttype==0)
				{
					eairfare = Float.parseFloat(s);
					if(eairfare <= 0)
					{
						JOptionPane.showMessageDialog(null, "经济舱机票价格错误!请重新检查。", "消息提示", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				
				Person p = new Person(flighttype, seattype, special, eairfare, isUSA);
				LuggageTable ff = new LuggageTable();
				ff.set(p);
				ff.show();
				
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(46)
									.addComponent(label_jc))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(29)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(label_cklx)
										.addComponent(label_hq))))
							.addGap(22)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(comboBox_cklx, 0, 676, Short.MAX_VALUE)
								.addComponent(comboBox_jc, 0, 676, Short.MAX_VALUE)
								.addComponent(comboBox_hq, 0, 676, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(label_sjmg))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(28)
									.addComponent(label_tbck)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(comboBox_sjmg, 0, 631, Short.MAX_VALUE)
								.addComponent(comboBox_tbck, 0, 631, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(47)
							.addComponent(txtpnkgkg, GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(label_jjcpj)
							.addGap(18)
							.addComponent(textField_jjc, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
							.addGap(41)
							.addComponent(button_OK, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(34)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox_hq, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_hq))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label_jc)
						.addComponent(comboBox_jc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label_cklx)
						.addComponent(comboBox_cklx, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label_sjmg)
						.addComponent(comboBox_sjmg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(label_tbck))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBox_tbck, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_jjcpj)
						.addComponent(textField_jjc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_OK))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnkgkg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
		
	}
}

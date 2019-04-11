package ui;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import calculation.*;
import data.*;

public class LuggageTable extends JFrame{
	private JPanel contentPane;
	private JTable table;
	private Person Nowperson;
	private Vector vData;
	private Vector vName;
	private DefaultTableModel model;
	
	public LuggageTable()  
    {  
        init();  
    } 
	
	public void set(Person p) {
		this.Nowperson = p;
	}
  
    /** 
     * ��ʼ��������� 
     */  
    private void init()  
    {  
    	this.setTitle("����Ŀ¼");
    	contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnNewButton1 = new JButton("�������");
		panel.add(btnNewButton1, BorderLayout.WEST);
		btnNewButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addLuggage();
			}
		});
		
		JButton btnNewButton = new JButton("����");
		panel.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validate();
				
				Calculation cal = new Calculation(Nowperson);
				Vector data = getData();
				if(data.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "����������!", "��Ϣ��ʾ", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					float allcost = 0;
					for(int ii=0;ii<data.size();ii++)
					{
						float re = cal.caculate((Luggage)data.get(ii));
						if(re==-1)
						{
							JOptionPane.showMessageDialog(null, "����������һ��涨!\n�����¼�顣", "��Ϣ��ʾ", JOptionPane.ERROR_MESSAGE);
							allcost = -10;
							break;
							//return;
						}
						else if(re==-2)
						{
							JOptionPane.showMessageDialog(null, "�ò�ռ��Ӥ��������������Ϲ涨��\n�����¼��!", "��Ϣ��ʾ", JOptionPane.ERROR_MESSAGE);
							allcost = -20;
							break;
							//return;
						}
						else if(re==-3)
						{
							JOptionPane.showMessageDialog(null, "�ò�ռ��Ӥ������������㡣\n��Ҫ���ֵ�ͬ�г˿͵��������!", "��Ϣ��ʾ", JOptionPane.ERROR_MESSAGE);
							allcost = -30;
							break;
							//return;
						}
						else if(re==-4)
						{
							JOptionPane.showMessageDialog(null, "���ݴ�����У��!", "��Ϣ��ʾ", JOptionPane.ERROR_MESSAGE);
							allcost = -30;
							break;
							//return;
						}
						else
						{
							allcost += re;
						}
						
					}
					if(allcost>=0)
						JOptionPane.showMessageDialog(null, "������������ķ���Ϊ:\n"+allcost+"", "���˷���", JOptionPane.DEFAULT_OPTION);
				}
			}
		});
		
        /* 
         * ����JTable������ 
         */  
		String[] columnNames =  
	        { "���", "����(KG)", "����(cm)", "���(cm)", "�߶�(cm)" };  
	        
	        vData = new Vector();
	        vName = new Vector();
	        vName.add("���");
	        vName.add("����(KG)");
	        vName.add("����(cm)");
	        vName.add("���(cm)");
	        vName.add("�߶�(cm)");
	        
	        Vector vRow = new Vector();
	        vRow.add("1");
	        vRow.add(" ");
	        vRow.add(" ");
	        vRow.add(" ");
	        vRow.add(" ");
	        vData.add(vRow.clone());
	        model = new DefaultTableModel(vData, vName);
	        table = new JTable();
	        table.setModel(model);
         
        TableColumn column = null;  
        int colunms = table.getColumnCount();  
        for(int i = 0; i < colunms; i++)  
        {  
            column = table.getColumnModel().getColumn(i);  
            /*��ÿһ�е�Ĭ�Ͽ������Ϊ100*/  
            column.setPreferredWidth(100);  
        }  
        
          
        JScrollPane scroll = new JScrollPane(table);  
        scroll.setSize(300, 200);  
          
        
        add(scroll);  
        this.setVisible(true);  
        this.pack();  
    }  
    
    public boolean Datavalid(int rowindex)
    {
    	DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
    	for(int i=1;i<5;i++)
    	{
    		String str = (String) tableModel.getValueAt(rowindex, i);
    		if(str==null||str.isEmpty()||str==" ")
    			return false;
    	}
    	return true;
    }
    
    public void addLuggage()
    {
    	Vector vRow1 = new Vector();
    	vRow1.add(table.getRowCount()+1);
        vRow1.add(" ");
        vRow1.add(" ");
        vRow1.add(" ");
        vRow1.add(" ");
    	vData.add(vRow1);
    	model = new DefaultTableModel(vData, vName);
    	table.setModel(model);
    	return;
    }
    
    public Vector getData()
    {
    	int rcount=table.getRowCount();
    	Vector data = new Vector();
    	DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
    	for(int i=0;i<rcount;i++)
    	{
    		if(table.getValueAt(i, 1).toString()!=" ")
    		{
    			if(Datavalid(i)==true)
        		{
    				float d[]= {0,0,0,0,0};
    				for(int j=1;j<5;j++)
    				{ 					
    					String value=(String) tableModel.getValueAt(i, j);
    					d[j] = Float.parseFloat(value);
    				}
    				Luggage lu = new Luggage(i+1, d[1], d[2], d[3], d[4]);
    				data.add(lu);
        		}
    			else
    			{
    				//JOptionPane.showMessageDialog(null, "���ݲ���������У��!", "��Ϣ��ʾ", JOptionPane.ERROR_MESSAGE);
    				Luggage lu = new Luggage(-100, -100, -100, -100, -100);
    				data.add(lu);
    			}
    		}
    		
    	}
    	
    	return data;

    }
    
    public static void main(String[] args)  
    {  
        new LuggageTable();  
    }  
}

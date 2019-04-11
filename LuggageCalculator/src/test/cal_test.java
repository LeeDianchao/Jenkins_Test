package test;

import data.*;
import calculation.Calculation;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;


public class cal_test {
	private Person person;
	private Calculation cal;
	/**
	 * 
	 * @param id
	 * @param name
	 * 1������������ţ���ͬΪͬһ���˿͵����
	 * 2������
	 * 3��������λ
	 * 4���ر�˿�
	 * 5�����ò�Ʊ��
	 * 6�������Ƿ��漰����
	 * 7���������
	 * 8������
	 * 9����
	 * 10����
	 * 11����
	 * 12��Ԥ��ֵ
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/test.csv")
	void testCalculation(int id, int flighttype, int seattype, int special, int eairfare, int isusa, int no, float weight, float length, float width, float height, float expectresult) {
		
		
		in_out io=new in_out();
		File fileid = new File("./iddata.txt");//�Ƿ����µ�����
        String sid="";
        File file = new File("./specialdata.txt");//�����Ƿ�ʹ�����������
        String s="";
        try {
        	sid=io.readTxtFile(fileid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(id!=Integer.parseInt(sid))
        {
        	this.person=new Person(flighttype,seattype,special,eairfare,isusa);
    		this.cal=new Calculation(this.person);
    		try {
            	io.writeTxtFile(Integer.toString(id), fileid);
            } catch (Exception e) {
                e.printStackTrace();
            }
    		try {
            	io.writeTxtFile(Integer.toString(0), file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
        	this.person=new Person(flighttype,seattype,special,eairfare,isusa);
    		this.cal=new Calculation(this.person);
        	try {
            	s=io.readTxtFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        	this.cal.setisusespecial(Integer.parseInt(s));
        }
        
		float actualresult=this.cal.caculate(new Luggage(no,weight,length,width,height));
		
		try {
			//System.out.println(this.cal.getisusespecial());
        	io.writeTxtFile(Integer.toString(this.cal.getisusespecial()), file);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		assertEquals(expectresult,actualresult);	
		
	}
	  
}
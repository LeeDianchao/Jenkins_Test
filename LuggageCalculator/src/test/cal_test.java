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
	 * 1：测试用例序号（相同为同一个乘客的行李）
	 * 2：航区
	 * 3：舱室座位
	 * 4：特别乘客
	 * 5：经济舱票价
	 * 6：航线是否涉及美国
	 * 7：行李序号
	 * 8：重量
	 * 9：长
	 * 10：宽
	 * 11：高
	 * 12：预测值
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/test.csv")
	void testCalculation(int id, int flighttype, int seattype, int special, int eairfare, int isusa, int no, float weight, float length, float width, float height, float expectresult) {
		
		
		in_out io=new in_out();
		File fileid = new File("./iddata.txt");//是否是新的用例
        String sid="";
        File file = new File("./specialdata.txt");//保存是否使用特殊行李额
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
package testNG_test;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import calculation.Calculation;
import data.Luggage;
import data.Person;

public class SimpleTestTest {
	@BeforeClass
    public void setUp() {
		System.out.println("*****TestNG*****");
		System.out.println("TestNG test case:");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("*****Test over*****");
    }
    
//  1：测试用例序号
//	2：航区
//	3：舱室座位
//	4：特别乘客
//	5：经济舱票价
//	6：航线是否涉及美国
//	7：行李序号
//	8：重量
//	9：长
//	10：宽
//	11：高
//	12：预测值
    @DataProvider(name="usecase")
    public Object[][] UseCase(){
        return new Object[][]{
                {1,0,0,0,1000,0,1,40,40,40,40,0},
                {2,0,0,0,1000,0,1,50,40,40,40,150},
                {3,0,0,0,1000,0,1,40,40,100,40,-1},
                {4,0,1,0,1000,0,1,40,40,40,40,150},
                {5,1,0,0,1000,1,1,45,40,40,40,3000},
                {6,1,0,0,1000,0,1,45,40,40,40,-1},
                {7,1,0,0,1000,1,1,32,40,40,100,1000},
                {8,1,0,0,1000,1,1,32,40,100,200,-1},
                {9,1,4,0,1000,1,1,10,30,35,40,0},
                {10,1,4,0,1000,1,1,10,40,40,40,-2},
                {11,1,4,0,1000,1,1,11,30,35,40,-2}
                
        };
    }
    
    @Test(dataProvider="usecase")
    public void verifyUser(int id, int flighttype, int seattype, int special, int eairfare, int isusa, int no, float weight, float length, float width, float height, float expectresult){
        System.out.println("case" + id);
        Person person = new Person(flighttype,seattype,special,eairfare,isusa);
        Calculation cal = new Calculation(person);
        float actualresult = cal.caculate(new Luggage(no,weight,length,width,height));	
        Assert.assertEquals(actualresult, expectresult, "Not equals: ");
    }
}

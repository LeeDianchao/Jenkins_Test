package data;

import java.util.Vector;

public class Person {
	public int flighttype;		//航班类型、区域
	public int seattype;		//舱室、座位类型
	public int special;        	//特别旅客(南航明珠金卡会员、天合联盟超级精英,南航明珠银卡会员、天合联盟精英,留学生、劳务、海员)
	public float eairfare;     	//经济舱票价
	public int isUSA;          	//航线是否涉及美国
	
	public Person()
	{
		this.flighttype=-1;
		this.seattype=-1;
		this.special=-1;
		this.eairfare=-1;
		this.isUSA=-1;
	}
	
	public Person(int f, int se, int sp, float ea, int is)
	{
		this.flighttype=f;
		this.seattype=se;
		this.special=sp;
		this.eairfare=ea;
		this.isUSA=is;
	}
}

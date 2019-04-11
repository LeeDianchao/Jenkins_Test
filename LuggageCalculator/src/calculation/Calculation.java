package calculation;
import java.math.BigDecimal;

import data.*;

public class Calculation {
	
	private int flighttype;			//航班类型、区域
	private int seattype;			//舱室、座位类型
	private int special;			//特别乘客
	private float eairfare;         //经济舱票价
	private int isUSA;              //航线是否涉及美国
	private int isUseSpecial;		//是否使用特殊行李额
	
	
	private float weight;			//行李重量
	private float w_size;			//行李宽
	private float h_size;			//行李高
	private float l_size;			//行李长
	private int no;					//行李序号
	
	public Calculation(Person p) {
		
		this.flighttype=p.flighttype;
		this.seattype=p.seattype;
		this.special=p.special;
		this.eairfare=p.eairfare;
		this.isUSA=p.isUSA;
		this.isUseSpecial=0;
		this.weight=-1;
		this.w_size=-1;
		this.h_size=-1;
		this.l_size=-1;
		this.no=-1;
		
	}
	
	public void setisusespecial(int s)
	{
		this.isUseSpecial=s;
	}
	public int getisusespecial()
	{
		return this.isUseSpecial;
	}
	
	public float rounding(float f)//四舍五入，保留一位小数
	{
		BigDecimal b = new BigDecimal(f);  
		float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
		return f1;
	}
	
	public float ForSpecial(int LuggageLimit, int OverFirst, int OverMore)
	{
		int scost = 0;
		if(this.special==0)
		{
			if(this.no==LuggageLimit+1) //超件 第一件
				scost+=OverFirst;
			else if(this.no>=LuggageLimit+2)//超件 第二件及更多 
				scost+=OverMore;
		}
		
		/**
		 * 1：南航明珠金卡会员、天合联盟超级精英:  国内、国际或地区航班免费行李额在一般标准上额外增加20公斤（计重制1件（计件制）
		 * 2：南航明珠银卡会员、天合联盟精英:  国内、国际或地区航班免费行李额在一般标准上额外增加10公斤（计重制1件（计件制） 
		 * 3：留学生、劳务、海员:  国际或地区航班免费行李额在一般标准上额外增加1件
		 */
		else       //特殊乘客（南航明珠金卡会员、天合联盟超级精英,南航明珠银卡会员、天合联盟精英,留学生、劳务、海员）
		{
			if(this.special==1)//南航明珠金卡会员、天合联盟超级精英
			{
				if(this.no==LuggageLimit+1) //超件 第一件
				{
					if(this.weight<=20)
					{
						scost+=0;
						this.isUseSpecial=10;//使用特殊行李额
					}
					else
						scost+=OverFirst;
				}
				else if(this.no>=LuggageLimit+2) //超件 第二件及更多 
				{
					if(this.isUseSpecial==0)//不使用特殊行李额
						if(this.weight<=20)
						{
							scost+=0;
							this.isUseSpecial=10;
						}
						else
							scost+=OverMore;
					//使用特殊行李额
					else					
					{
						if(this.no==LuggageLimit+2)
							scost+=OverFirst;
						else
							scost+=OverMore;
					}
						
				}
			}
			if(this.special==2)//南航明珠银卡会员、天合联盟精英
			{
				if(this.no==LuggageLimit+1) //超件 第一件
				{
					if(this.weight<=10)
					{
						scost+=0;
						this.isUseSpecial=10;//使用特殊行李额
					}
					else
						scost+=OverFirst;
				}
				else if(this.no>=LuggageLimit+2) //超件 第二件及更多 
				{
					if(this.isUseSpecial==0)//不使用特殊行李额
						if(this.weight<=10)
						{
							scost+=0;
							this.isUseSpecial=10;
						}
						else
							scost+=OverMore;
					//使用特殊行李额
					else					
					{
						if(this.no==LuggageLimit+2)
							scost+=OverFirst;
						else
							scost+=OverMore;
					}
				}
			}
			if(this.special==3)//留学生、劳务、海员
			{
				if(this.no==LuggageLimit+2) //超件 第一件
					scost+=OverFirst;
				else if(this.no>=LuggageLimit+3) //超件 第二件及更多 
					scost+=OverMore;
			}
		}
		return scost;
	
	}
	
	public float caculate(Luggage lu){
		
		if(lu.no<0||lu.weight<0||lu.length<0||lu.width<0||lu.height<0)//错误数据
		{
			return -4;
		}
		
		this.weight=lu.weight;
		this.w_size=lu.width;
		this.h_size=lu.height;
		this.l_size=lu.length;
		this.no=lu.no;
		//this.isUseSpecial = 0;
		
		float cost=0;
		int maxweight = 0;
		if(this.isUSA==0)//不涉及美国航线的，每件托运行李的最大重量不得超过32公斤（70磅）
		{
			maxweight = 32;
		}
		else  //涉及美国航线的，每件托运行李的最大重量不得超过45公斤（90磅）
		{
			maxweight = 45;
		}
		
		switch(this.flighttype){
		case 0:		//国内航班
			//一般标准（不合格） //超过一般标准
			if(this.weight>50||this.h_size>100||this.w_size>40||this.l_size>60) {
				return -1;      
			}			
			if(this.no==1)
			{
				//头等舱（不合格）
				if(this.seattype==0) {
					if(this.weight>40)
						cost += rounding((float)((this.weight-40)*this.eairfare*0.015));
				}
				//公务舱（不合格）
				else if(this.seattype==1) {
					if(this.weight>30)
						cost += rounding((float)((this.weight-30)*this.eairfare*0.015));
				}
				//明珠经济舱、经济舱（不合格）
				else if(this.seattype==2||this.seattype==3) {
					if(this.weight>20)
						cost += rounding((float)((this.weight-20)*this.eairfare*0.015));
				}
				//不占座婴儿（不合格）
				else if(this.seattype==4) {
					if(this.weight>10)
						cost += rounding((float)((this.weight-10)*this.eairfare*0.015));
				}
			}
			else   //超件
			{
				if(this.special==1)//南航明珠金卡会员、天合联盟超级精英
				{
					if(this.no==2&&this.weight<=20)
						cost+=0;
					else
						cost += rounding((float)(this.weight*this.eairfare*0.015));
				}
				else if(this.special==2)//南航明珠银卡会员、天合联盟精英
				{
					if(this.no==2&&this.weight<=10)
						cost+=0;
					else
						cost += rounding((float)(this.weight*this.eairfare*0.015));
				}
				else {
					cost += rounding((float)(this.weight*this.eairfare*0.015));
				}
			}
			break;
			
			
		case 1:		//涉及日本、美洲、澳新、俄罗斯（注1）、迪拜（注2）的航程，以及新加坡始发（注3）与中国大陆间的航程
			if(this.seattype==4)//不占座婴儿 1x10
			{
				if(this.weight<=10&&this.w_size+this.h_size+this.l_size<=115&&this.no==1)
				{
					cost+=0;//合格
				}
				else if(this.weight<=10&&this.w_size+this.h_size+this.l_size<=115&&this.no>1)
				{
					return -3;//超件（需要划分到同行乘客的行李额中）
				}
				else
				{
					return -2;//不合格
				}
			}
			else
			{
				//超过一般标准
				if(this.weight>maxweight||this.w_size+this.h_size+this.l_size>300)
					return -1;        
				
				//头等舱  3x32
				if(this.seattype==0) {
					cost+=ForSpecial(3,1000,2000);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>32)
						cost+=3000;
				}
				//公务舱   2x32
				else if(this.seattype==1) {
					cost+=ForSpecial(2,1000,2000);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>32)
						cost+=3000;
				}
				//明珠经济舱、经济舱 2x23
				else if(this.seattype==2||this.seattype==3) {
					cost+=ForSpecial(2,1000,2000);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>23&&this.weight<=32)
						cost+=1000;
					else if(this.weight>32)
						cost+=3000;
					
				}
			}
						
			break;
		case 2:		//涉及中西亚（注4）的航程
			if(this.seattype==4)//不占座婴儿 1x10
			{
				if(this.weight<=10&&this.w_size+this.h_size+this.l_size<=115&&this.no==1)
				{
					return 0;//合格
				}
				else if(this.weight<=10&&this.w_size+this.h_size+this.l_size<=115&&this.no>1)
				{
					return -3;//超件（需要划分到同行乘客的行李额中）
				}
				else
				{
					return -2;//不合格
				}
			}
			else
			{
				//超过一般标准
				if(this.weight>maxweight||this.w_size+this.h_size+this.l_size>300)
					return -1;
				//头等舱  3x32
				if(this.seattype==0) {
					cost+=ForSpecial(3,450,1300);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>32)
						cost+=3000;
				}
				//公务舱   2x32
				else if(this.seattype==1) {
					cost+=ForSpecial(2,450,1300);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>32)
						cost+=3000;
				}
				//明珠经济舱、经济舱 1x32
				else if(this.seattype==2||this.seattype==3) {
					cost+=ForSpecial(1,450,1300);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>32)
						cost+=3000;	
				}
			}
			break;
		case 3:		//涉及内罗毕的航程（注5） 
			if(this.seattype==4)//不占座婴儿 1x10
			{
				if(this.weight<=10&&this.w_size+this.h_size+this.l_size<=115&&this.no==1)
				{
					return 0;//合格
				}
				else if(this.weight<=10&&this.w_size+this.h_size+this.l_size<=115&&this.no>1)
				{
					return -3;//超件（需要划分到同行乘客的行李额中）
				}
				else
				{
					return -2;//不合格
				}
			}
			else
			{
				//超过一般标准
				if(this.weight>maxweight||this.w_size+this.h_size+this.l_size>300)
					return -1;
				//头等舱  3x32
				if(this.seattype==0) {
					cost+=ForSpecial(3,1000,2000);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>32)
						cost+=3000;
				}
				//公务舱   2x32
				else if(this.seattype==1) {
					cost+=ForSpecial(2,1000,2000);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>32)
						cost+=3000;
				}
				//明珠经济舱、经济舱 2x23
				else if(this.seattype==2||this.seattype==3) {
					cost+=ForSpecial(2,1000,2000);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>23&&this.weight<=32)
						cost+=2000;
					else if(this.weight>32)
						cost+=3000;
					
				}
			}
			break;
		
		case 4:		//除日本、美洲、澳新、俄罗斯（注1）、迪拜（注2）、内罗毕、中西亚（注4）以外的国际/地区航程
			if(this.seattype==4)//不占座婴�? 1x10
			{
				if(this.weight<=10&&this.w_size+this.h_size+this.l_size<=115&&this.no==1)
				{
					return 0;//合格
				}
				else if(this.weight<=10&&this.w_size+this.h_size+this.l_size<=115&&this.no>1)
				{
					return -3;//超件（需要划分到同行乘客的行李额中）
				}
				else
				{
					return -2;//不合格
				}
			}
			else
			{
				//超过一般标准
				if(this.weight>maxweight||this.w_size+this.h_size+this.l_size>300)
					return -1;
				//头等舱  3x32
				if(this.seattype==0) {
					cost+=ForSpecial(3,450,1300);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>32)
						cost+=3000;
				}
				//公务舱   3x23
				else if(this.seattype==1) {
					cost+=ForSpecial(3,450,1300);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>23&&this.weight<=32)
						cost+=1000;
					else if(this.weight>32)
						cost+=3000;
				}
				//明珠经济舱 2x23
				else if(this.seattype==2) {
					cost+=ForSpecial(2,450,1300);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>23&&this.weight<=32)
						cost+=1000;
					else if(this.weight>32)
						cost+=3000;
					
				}
				//经济舱 1x23
				else if(this.seattype==3) {
					cost+=ForSpecial(1,450,1300);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>23&&this.weight<=32)
						cost+=1000;
					else if(this.weight>32)
						cost+=3000;
					
				}
			}
			break;
			
		case 5:		//涉及韩国始发与中国间的航程
			if(this.seattype==4)//不占座婴儿 1x10
			{
				if(this.weight<=10&&this.w_size+this.h_size+this.l_size<=115&&this.no==1)
				{
					return 0;//合格
				}
				else if(this.weight<=10&&this.w_size+this.h_size+this.l_size<=115&&this.no>1)
				{
					return -3;//超件（需要划分到同行乘客的行李额中）
				}
				else
				{
					return -2;//不合格
				}
			}
			else
			{
				//超过一般标准
				if(this.weight>maxweight||this.w_size+this.h_size+this.l_size>300)
					return -1;
				//头等舱  3x32
				if(this.seattype==0) {
					cost+=ForSpecial(3,450,1300);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>32)
						cost+=3000;
				}
				//公务舱   2x32
				else if(this.seattype==1) {
					cost+=ForSpecial(2,450,1300);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					else if(this.weight>32)
						cost+=3000;
				}
				//明珠经济舱、经济舱 1x23
				else if(this.seattype==2||this.seattype==3) {
					cost+=ForSpecial(1,450,1300);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>23&&this.weight<=32)
						cost+=1000;
					else if(this.weight>32)
						cost+=3000;
					
				}
			}
			break;
		case 6:		//涉及兰州/乌鲁木齐与迪拜之间
			if(this.seattype==4)//不占座婴儿 1x10
			{
				if(this.weight<=10&&this.w_size+this.h_size+this.l_size<=115&&this.no==1)
				{
					return 0;//合格
				}
				else if(this.weight<=10&&this.w_size+this.h_size+this.l_size<=115&&this.no>1)
				{
					return -3;//超件（需要划分到同行乘客的行李额中）
				}
				else
				{
					return -2;//不合格
				}
			}
			else
			{
				//超过一般标准
				if(this.weight>maxweight||this.w_size+this.h_size+this.l_size>300)
					return -1;
				//头等舱  3x32
				if(this.seattype==0) {
					cost+=ForSpecial(3,1000,2000);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>32)
						cost+=3000;
				}
				//公务舱   2x32
				else if(this.seattype==1) {
					cost+=ForSpecial(2,1000,2000);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>32)
						cost+=3000;
				}
				//明珠经济舱 1x32
				else if(this.seattype==2) {
					cost+=ForSpecial(1,1000,2000);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>32)
						cost+=3000;
					
				}
				//经济舱 1x23
				else if(this.seattype==3) {
					cost+=ForSpecial(1,1000,2000);
					if(this.w_size+this.h_size+this.l_size>158)
						cost+=1000;
					if(this.weight>23&&this.weight<=32)
						cost+=1000;
					else if(this.weight>32)
						cost+=3000;
					
				}
			}
			break;
		}
		return cost;
	}
}

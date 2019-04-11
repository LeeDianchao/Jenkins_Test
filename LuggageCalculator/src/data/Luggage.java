package data;

public class Luggage {
	public int no;			//行李序号
	public float weight;	//重量
	public float length;	//长
	public float width;		//宽
	public float height;	//高
	
	
	public Luggage()
	{
		this.no=-1;
		this.weight=-1;
		this.length=-1;
		this.width=-1;
		this.height=-1;
		
	}
	
	public Luggage(int n, float we, float le, float wi, float he)
	{
		this.no=n;
		this.weight=we;
		this.length=le;
		this.width=wi;
		this.height=he;
	}
}

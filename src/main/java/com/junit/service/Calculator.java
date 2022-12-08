package com.junit.service;

import org.springframework.stereotype.Service;

@Service
public class Calculator 
{
	Multiply mul;
	
	public Calculator() {	}
	
	public Calculator(Multiply mul) {
		this.mul = mul;
	}
	public int sum(int a,int b)
	{
		int sum = a + b;
		System.out.println("method sum : " + sum);
		return sum;
	}
	public int sub(int a,int b)
	{
		int sub = a - b;
		System.out.println("method sub : " + sub);
		return sub;
	}
	public int mul(int a,int b)
	{
		System.out.println("method mul : " + mul);
		int m = mul.mul(a, b);
		return m;
	}
	public int div(int a,int b)
	{
		int div = a / b;
		System.out.println("method div : " + div);
		return div;
	}
	public boolean check(boolean flag)
	{
		return flag;
	}
}
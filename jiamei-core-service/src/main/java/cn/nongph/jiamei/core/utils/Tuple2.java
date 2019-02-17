package cn.nongph.jiamei.core.utils;

public class Tuple2<F,T> {
	private F _1;
	private T _2;
	
	public Tuple2(F e1, T e2){
		this._1 = e1;
		this._2 = e2;
	}
	
	public F e1() {
		return _1;
	}

	public T e2() {
		return _2;
	}

}

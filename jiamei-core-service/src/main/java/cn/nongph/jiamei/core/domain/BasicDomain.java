package cn.nongph.jiamei.core.domain;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.BeanUtils;

import cn.nongph.jiamei.core.annotation.RequiredField;

public abstract class BasicDomain {
	public abstract Long getId();
	
	protected void loadIfNecessary(){
		if( !isLoaded() )
			load();
	}
	
	public void load() {
		try {
			BeanUtils.copyProperties(this, doLoad());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected abstract BasicDomain doLoad();
	
	private boolean isLoaded(){
		Class<?> c = this.getClass();
		do{
			for( Field f: c.getDeclaredFields() ){
				if( f.isAnnotationPresent( RequiredField.class ) ) {
					f.setAccessible( true );
					try {
						if( f.get(this) !=null )
							return true;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} while( (c=c.getSuperclass())!=BasicDomain.class  );
		return false;
	}
	
	public boolean equals(Object obj) {
		if(obj.getClass() == this.getClass()){
			return getId() == ((BasicDomain)obj).getId();
		} else {
			return false;
		}
	}
	
	public int hashCode() {
		return getId().hashCode();
	}
}

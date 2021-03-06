package cn.netbuffer.jfinal_bootstrap_table.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUser<M extends BaseUser<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Long id) {
		set("id", id);
	}

	public java.lang.Long getId() {
		return get("id");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setSex(java.lang.String sex) {
		set("sex", sex);
	}

	public java.lang.String getSex() {
		return get("sex");
	}

	public void setAge(java.lang.Integer age) {
		set("age", age);
	}

	public java.lang.Integer getAge() {
		return get("age");
	}

	public void setPhone(java.lang.String phone) {
		set("phone", phone);
	}

	public java.lang.String getPhone() {
		return get("phone");
	}

	public void setDeliveryaddress(java.lang.String deliveryaddress) {
		set("deliveryaddress", deliveryaddress);
	}

	public java.lang.String getDeliveryaddress() {
		return get("deliveryaddress");
	}

	public void setAdddate(java.lang.Integer adddate) {
		set("adddate", adddate);
	}

	public java.lang.Integer getAdddate() {
		return get("adddate");
	}

}

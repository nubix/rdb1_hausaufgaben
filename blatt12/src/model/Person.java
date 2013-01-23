package model;

public class Person {
	private int id;
	private String name;
	private String gender;
	private String birthday;
	
	public Person(int id, String name, String gender, String birthday) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getBirthday() {
		return birthday;
	}
	
	public String toString() {
		return "Person(" + id + ", " + name + ", " + gender + ", " + birthday + ")";
	}
}

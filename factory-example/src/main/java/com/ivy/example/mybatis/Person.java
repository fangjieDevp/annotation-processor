package com.ivy.example.mybatis;

import com.ivy.annotation.Id;
import com.ivy.annotation.Persistent;
import com.ivy.annotation.Property;

/**
 * 类说明:使用注解完成映射的实体类
 */
@Persistent(table = "person_inf")
public class Person {
    @Id(column = "person_id", type = "integer", generator = "identity")
    private int id;
    @Property(column = "person_name", type = "string")
    private String name;
    @Property(column = "person_age", type = "integer")
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

package common.test;

import common.annotation.Column;
import common.annotation.Id;
import common.annotation.Table;
import jdk.nashorn.internal.objects.annotations.Setter;

@Table(name = "Person")
public class Person {
    @Id
    private Integer id;
    @Column(name = "name")
    private String name;
    private String email;

    public Integer getId() {
        return id;
    }

    public Person setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Person setEmail(String email) {
        this.email = email;
        return this;
    }
}

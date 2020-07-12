package spring1.spring.bean.annotation;

import org.springframework.stereotype.Repository;

@Repository(value = "userRepository2")
public class UserRepositoryImpl2 implements UserRepository{
    public void save(){
        System.out.println("UserRepository Save...");
    }
}

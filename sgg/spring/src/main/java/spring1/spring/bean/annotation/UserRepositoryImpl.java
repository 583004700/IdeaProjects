package spring1.spring.bean.annotation;

import org.springframework.stereotype.Repository;

@Repository(value = "userRepository")
public class UserRepositoryImpl implements UserRepository{
    public void save(){
        System.out.println("UserRepository Save...");
    }
}

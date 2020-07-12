package spring1.spring.bean.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    //注入时指定名称
    @Qualifier("userRepository")
    private UserRepository userRepository;

    public void add(){
        System.out.println("UserService add...");
        userRepository.save();
    }

    @Autowired
    public void m(UserRepository userRepository2){
        System.out.println("Autowired放在方法上"+userRepository2);
    }
}

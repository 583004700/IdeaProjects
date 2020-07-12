package spring1.spring.bean.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * 实例工厂方法
 */
public class InstanceCarFactory {
    private Map<String,Car> cars = null;

    public InstanceCarFactory(){
        cars = new HashMap<String,Car>();
        cars.put("audi",new Car("audi","30000",100));
        cars.put("ford",new Car("ford","40000",200));
    }

    public Car getCar(String name){
        return cars.get(name);
    }
}

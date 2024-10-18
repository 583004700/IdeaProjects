package com.demo.mydemo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
class City{
    private String id;
    private String pid;
    private String level;
    private String name;
    private List<City> children = new ArrayList<>();
}

public class Main {

    public static List<City> levelTree(List<City> list){
        List<City> result = new ArrayList<>();
        Map<String,City> cityMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            City c = list.get(i);
            cityMap.put(c.getId(),c);
        }
        for (int i = 0; i < list.size(); i++) {
            City c = list.get(i);
            String pid = c.getPid();
            City pCity = cityMap.get(pid);
            if(pCity != null){
                pCity.getChildren().add(c);
            }else{
                result.add(c);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<City> cities = new ArrayList<>();
        City hz = new City();
        hz.setName("杭州");
        hz.setId("hz");
        hz.setPid("zj");

        City zj = new City();
        zj.setName("浙江");
        zj.setId("zj");
        cities.add(hz);
        cities.add(zj);

        City xh = new City();
        xh.setName("西湖");
        xh.setId("xh");
        xh.setPid("hz");
        cities.add(xh);

        List<City> result = levelTree(cities);
        System.out.println(result);
    }
}

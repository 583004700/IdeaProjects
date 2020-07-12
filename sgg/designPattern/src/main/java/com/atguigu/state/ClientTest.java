package com.atguigu.state;

/**
 * 状态模式测试类
 * @author Administrator
 *  状态模式定义了当前状态怎么处理事情，并且处理完成后切换到下一个状态
 */
public class ClientTest {

	public static void main(String[] args) {
		// 创建活动对象，奖品有1个奖品
        RaffleActivity activity = new RaffleActivity(1);

        // 我们连续抽300次奖
        for (int i = 0; i < 30; i++) {
            System.out.println("--------第" + (i + 1) + "次抽奖----------");
            // 参加抽奖，第一步点击扣除积分
            activity.debuctMoney();

            // 第二步抽奖
            activity.raffle();
        }
	}

}

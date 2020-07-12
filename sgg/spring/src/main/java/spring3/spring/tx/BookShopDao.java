package spring3.spring.tx;

public interface BookShopDao {
    //根据书号获取书的单价
    int findBookPriceByIsbn(String isbn);

    //更新书的库存，使书号对应库存 -1
    void updateBookStock(String isbn);

    //更新用户的账户余额
    void updateUserAccount(String username,int price);
}

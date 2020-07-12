package spring3.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("bookShopService")
public class BookShopServiceImpl implements BookShopService{
    @Autowired
    private BookShopDao bookShopDao;

    //1.propagation 指定事务的传播行为，即当前的事务方法被另外一个事务方法调用时，
    //如何使用事务，默认取值为REQUIRED，使用调用方法的事务
    //REQUIRES_NEW 使用自己的事务
    //2.使用isolation 指定事务的隔离级别，最常用的取值为 READ_COMMITTED
    //3.默认情况下Spring的声明式事务对所有的运行时异常进行回滚，也可以通过对应的属性进行设置
    //noRollbackFor指定不回滚的异常
    //4.使用readOnly指定事务是否是读
    //5.使用timeout指定强制回滚之前事务可以占用的时间
    @Transactional(propagation = Propagation.REQUIRES_NEW,
    isolation = Isolation.READ_COMMITTED,noRollbackFor = {UserAccountException.class},
    readOnly = false,timeout = 3)
    @Override
    public void purchase(String username, String isbn) {
        int price = bookShopDao.findBookPriceByIsbn(isbn);
        bookShopDao.updateBookStock(isbn);
        bookShopDao.updateUserAccount(username,price);

    }
}

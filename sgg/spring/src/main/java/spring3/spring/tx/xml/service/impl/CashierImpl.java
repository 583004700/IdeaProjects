package spring3.spring.tx.xml.service.impl;


import spring3.spring.tx.xml.service.BookShopService;
import spring3.spring.tx.xml.service.Cashier;

import java.util.List;

public class CashierImpl implements Cashier {

    private BookShopService bookShopService;

    public void setBookShopService(BookShopService bookShopService) {
        this.bookShopService = bookShopService;
    }

    @Override
    public void checkout(String username, List<String> isbns) {
        for(String isbn : isbns){
            bookShopService.purchase(username,isbn);
        }
    }
}

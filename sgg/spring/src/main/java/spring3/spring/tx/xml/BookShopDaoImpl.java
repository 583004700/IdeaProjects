package spring3.spring.tx.xml;

import org.springframework.jdbc.core.JdbcTemplate;

public class BookShopDaoImpl implements BookShopDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findBookPriceByIsbn(String isbn) {
        String sql = "select price from book where isbn = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,isbn);
    }

    @Override
    public void updateBookStock(String isbn) {
        String sql = "update book_stock set stock = stock - 1 where isbn = ?";
        jdbcTemplate.update(sql,isbn);
        String sql2 = "select stock from book_stock where isbn = ?";
        int stock = jdbcTemplate.queryForObject(sql2,Integer.class,isbn);
        if(stock < 0){
            throw new BookStockException("库存不足");
        }
    }

    @Override
    public void updateUserAccount(String username, int price) {
        String sql = "update account set balance = balance - ? where username = ?";
        jdbcTemplate.update(sql,price,username);
        String sql2 = "select balance from account where username = ?";
        int balance = jdbcTemplate.queryForObject(sql2,Integer.class,username);
        if(balance < 0){
            throw new UserAccountException("余额不足!");
        }
    }
}

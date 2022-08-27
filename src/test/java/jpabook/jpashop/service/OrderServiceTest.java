package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("상품주문")
    public void 상품_주문() {
        //given
        Member member = createMember();

        int stockQuantity = 10;
        Book book = createBook(stockQuantity, "시골 JPA", 10000);

        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
        assertEquals(stockQuantity - orderCount, book.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    @DisplayName("상품주문_재고수량초과")
    public void 상품주문_재고수량초과() {
        //given
        Member member = createMember();
        Item book = createBook(10, "시골 JPA", 10000);

        int orderCount = 11;
        //when
        orderService.order(member.getId(), book.getId(), orderCount);

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");

    }

    @Test
    @DisplayName("주문취소")
    public void 주문_취소() {
        //given
        Member member = createMember();
        int stockQuantity = 10;
        Book item = createBook(stockQuantity, "시골 jpa", 10000);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);
        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals(stockQuantity, item.getStockQuantity());


    }

    private Book createBook(int stockQuantity, String bookName, int price) {
        Book book = new Book();
        book.setStockQuantity(stockQuantity);
        book.setName(bookName);
        book.setPrice(price);
        entityManager.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        entityManager.persist(member);
        return member;
    }

}
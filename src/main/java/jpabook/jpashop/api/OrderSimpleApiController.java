package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        for (Order order : all) {
            // Lazy 강제 초기화 시키는법!!
            order.getMember().getName();
            order.getDelivery().getAddress();
        }

        return all;
    }
    // 무한루프에 빠진다.
    // 양방향 매핑 해놓은 곳에 JsonIgnore 해준다.
    // hibernate5 모듈 사용.
    // Entity 를 반환하므로 이 방법은 좋지 않다.

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        return orderRepository.findAllByString(new OrderSearch()).stream().map(SimpleOrderDto::new).collect(Collectors.toList());
    }
    // order 2개 조회.
    // map 돌면서 getMember, getDelivery 할때 각각 쿼리 실행.
    // SimpleOrderDto 한개당 쿼리 3개 발생.
    // N+1 문제 발생.

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        return orderRepository.findAllWithMemberDelivery().stream().map(SimpleOrderDto::new).collect(Collectors.toList());
        // "select o FROM  Order o join fetch o.member m join fetch o.delivery d" sql 문제 join fetch 로 member, delivery 값을 한번에 가져온다.
        // 쿼리 한번으로 해결!
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}

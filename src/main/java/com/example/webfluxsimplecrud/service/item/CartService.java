//package com.example.webfluxsimplecrud.service;
//
//import com.example.webfluxsimplecrud.domain.item.Cart;
//import com.example.webfluxsimplecrud.domain.item.CartItem;
//import com.example.webfluxsimplecrud.domain.item.Item;
//import com.example.webfluxsimplecrud.dto.item.CartRequestDto;
//import com.example.webfluxsimplecrud.dto.item.ItemRequestDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@Service
//@RequiredArgsConstructor
//public class CartService {
//
//    private final CartRepository cartRepository;
//    private final ItemRepository itemRepository;
//    private final CartItemRepository cartItemRepository;
//    private final MartItemRepository martItemRepository;
//
//    public Flux<Item> getAllItems() {
//        return itemRepository.findAll();
//    }
//
//    public Mono<Item> getOneItems(String itemName) {
//        return itemRepository.findByName(itemName)
//                .switchIfEmpty(Mono.error(new RuntimeException(itemName + "은/는 존재하지 않는 아이템입니다.")));
//    }
//
//    public Mono<Cart> getMyCart(String cartId) {
//        return cartRepository.findByName(cartId)
//                .switchIfEmpty(Mono.error(new RuntimeException(cartId + "라는 장바구니는 존재하지 않는 장바구니입니다.")));
//    }
//
//    public Mono<Item> createItem(ItemRequestDto itemRequestDto) {
//        return itemRepository.existsByName(itemRequestDto.getName()).flatMap(exists -> {
//            if (!exists) return itemRepository.save(new Item(itemRequestDto));
//            else return Mono.error(new RuntimeException(itemRequestDto.getName() + "은/는 이미 존재하는 아이템입니다."));
//        });
//    }
//
//    public Mono<Cart> createCart(CartRequestDto cartRequestDto) {
//        return cartRepository.existsByName(cartRequestDto.getName()).flatMap(exists -> {
//            if (!exists) return cartRepository.save(new Cart(cartRequestDto));
//            else return Mono.error(new RuntimeException(cartRequestDto.getName() + "은/는 이미 존재하는 장바구니입니다."));
//        });
//    }
//
//    public Mono<Cart> addItemToCart(String cartName, String itemName, int quantity) {
//        return itemRepository.findByName(itemName)
//                .switchIfEmpty(Mono.error(new RuntimeException(itemName + "은/는 존재하지 않는 아이템입니다.")))
//                .flatMap(item -> {
//                    return martItemRepository
//                            .findById(item.getId())
//                            .switchIfEmpty(Mono.error(new RuntimeException(itemName + "은/는 마트에 존재하지 않습니다.")))
//                            .filter(i -> i.getQuantity() >= quantity)
//                            .switchIfEmpty(Mono.error(new RuntimeException(itemName + "의 재고가 부족합니다.")));
//                }) // 이제 살 수 있음 ......
//                .flatMap(item -> cartRepository.findByName(cartName)
//                        .switchIfEmpty(Mono.error(new RuntimeException(cartName + "은/는 존재하지 않는 장바구니입니다.")))
//                        .flatMap(cart -> {
//                            item.modifyItemQuantity(item.getQuantity() + quantity);
//                            CartItem cartItem = new CartItem(item.getId(), cart.getId(), quantity);
//                            martItemRepository.save(item);
//                            return cartItemRepository.save(cartItem)
//                                    .flatMap(savedCartItem -> {
//                                        cart.addToCart(savedCartItem);
//                                        return cartRepository.save(cart);
//                                    });
//                        }));
//    }
//
////    public Mono<Cart> removeItemFromCart(String cartId, String itemName, int quantity) {
////        return itemRepository.findByName(itemName)
////                .switchIfEmpty(Mono.error(new RuntimeException(itemName + "은/는 존재하지 않는 아이템입니다.")))
////                .flatMap(item -> cartRepository.findByName(cartId)
////                        .switchIfEmpty(Mono.error(new RuntimeException(cartId + "은/는 존재하지 않는 장바구니입니다.")))
////                        .filter(cart -> cart.getCartItems().containsKey(item))
////                        .switchIfEmpty(Mono.error(new RuntimeException(cartId + "에는 " + itemName + "이/가 존재하지 않습니다.")))
////                        .filter(cart -> cart.getCartItems().get(item) >= quantity)
////                        .switchIfEmpty(Mono.error(new RuntimeException("삭제할 재고를 다시 입력해주세요.")))
////                        .flatMap(cart -> {
////                            item.modifyItemQuantity(quantity, true);
////                            return itemRepository.save(item)
////                                    .flatMap(saveditem -> {
////                                        cart.removeItem(saveditem, quantity);
////                                        return cartRepository.save(cart);
////                                    });
////                        }));
////    }
////
////    public Mono<Void> deleteAllItems() {
////        return itemRepository.deleteAll();
////    }
////
////    public Mono<Cart> deleteAllItemsFromCart(String cartId) {
////        return cartRepository.findByName(cartId)
////                .switchIfEmpty(Mono.error(new RuntimeException(cartId + "은/는 존재하지 않는 장바구니입니다.")))
////                .flatMap(cart -> {
////                    cart.getCartItems().clear();
////                    return cartRepository.save(cart);
////                });
////    }
//}
